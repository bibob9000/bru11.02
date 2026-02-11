package com.example.myapplication.ui;

import static com.example.myapplication.utils.Utils.APIKEY;
import static com.example.myapplication.utils.Utils.BASE_URL;
import static com.example.myapplication.utils.Utils.TOKEN;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.chaos.view.PinView;
import com.example.myapplication.R;
import com.example.myapplication.controller.API;
import com.example.myapplication.data.ChangePasswordToken;
import com.example.myapplication.data.Email;
import com.example.myapplication.data.ResponseUser;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPVerificationActivity extends AppCompatActivity {

    TextView resendText;
    boolean runningTimer;
    MaterialButton NewPassButton;
    CountDownTimer resetCountDownTimer;
    PinView pinView;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        NewPassButton = findViewById(R.id.SetNewPass);
        resendText = findViewById(R.id.resendAfter);
        pinView = findViewById(R.id.pinView);
        String emailPass = getIntent().getStringExtra("email");
        String emailRepeat = getIntent().getStringExtra("emailRepeat");
        Email email = new Email(emailRepeat);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        resetCountDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                resendText.setText("resend after 0:" + (l / 1000 + 1));
            }

            @Override
            public void onFinish() {
                resendText.setText("resend");
                resendText.setTextColor(Color.parseColor("#0066CC"));
                runningTimer = false;
                resendText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResetTimer();
                        Call<Void> call = api.sendCode(APIKEY, email);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(OTPVerificationActivity.this, "Code Resended", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(OTPVerificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        };

        NewPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pinView.getText().toString().length() == 6){
                    ChangePasswordToken changePasswordToken =
                            new ChangePasswordToken(
                                    "email",
                                    emailPass,
                                    pinView.getText().toString());
                    Call<ResponseUser> call = api.verifyCode(APIKEY, changePasswordToken);
                    call.enqueue(new Callback<ResponseUser>() {
                        @Override
                        public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                            if (response.isSuccessful()){
                                if (response.body() != null){
                                    TOKEN = response.body().accessToken;
                                    Toast.makeText(OTPVerificationActivity.this, TOKEN + " ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(OTPVerificationActivity.this, NewPasswordActivity.class);
                                    intent.putExtra("passedEmail", emailPass);
                                    intent.putExtra("newToken", TOKEN);
                                    startActivity(intent);
                                }
                            }
                            else {
                                pinView.setLineColor(ResourcesCompat.getColor(getResources(), R.color.red, getTheme()));
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseUser> call, Throwable throwable) {
                            Toast.makeText(OTPVerificationActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(OTPVerificationActivity.this, "Введите код", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ResetTimer();
    }

    private void ResetTimer(){
        if(runningTimer == false){
            runningTimer = true;
            resendText.setTextColor(Color.parseColor("#A7A7A7"));
            resendText.setOnClickListener(null); // Убираем предыдущий обработчик
            resetCountDownTimer.start();
        }
    }
}