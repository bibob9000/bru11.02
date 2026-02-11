package com.example.myapplication.ui;

import static com.example.myapplication.utils.Utils.APIKEY;
import static com.example.myapplication.utils.Utils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.controller.API;
import com.example.myapplication.data.Email;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputLayout emailField;
    private MaterialButton button;
    private Retrofit retrofit;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailField = findViewById(R.id.forgotEmailAddress);
        button = findViewById(R.id.button_forgot);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email email = new Email(String.valueOf(emailField.getEditText().getText()));

                Call<Void> call = api.sendCode(APIKEY, email);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(ForgotPasswordActivity.this, "Send OK", Toast.LENGTH_SHORT).show();
                        if (isEmailValid(String.valueOf(emailField.getEditText().getText()))){
                            String emailText = emailField.getEditText().getText().toString();
                            Intent intent = new Intent(ForgotPasswordActivity.this, OTPVerificationActivity.class);
                            intent.putExtra("email", emailText);
                            intent.putExtra("emailRepeat", String.valueOf(emailField.getEditText().getText()));
                            startActivityForResult(intent, REQUEST_CODE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ForgotPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void signIn2(View view) {
        startActivity(new Intent(ForgotPasswordActivity.this, LogInActivity.class));
    }

    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}