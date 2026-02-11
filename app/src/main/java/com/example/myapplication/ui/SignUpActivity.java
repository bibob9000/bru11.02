package com.example.myapplication.ui;
import static com.example.myapplication.utils.Utils.APIKEY;
import static com.example.myapplication.utils.Utils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.myapplication.R;
import com.example.myapplication.controller.API;
import com.example.myapplication.data.ResponseUser;
import com.example.myapplication.data.User;
import com.example.myapplication.utils.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private AppCompatCheckBox checkBox;
    private MaterialButton button;
    private TextView signInText;
    private TextInputLayout email, pswrd1, pswrd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        checkBox = findViewById(R.id.signupCheckBox);
        button = findViewById(R.id.button_signup);
        signInText = findViewById(R.id.textView17);
        email = findViewById(R.id.emailAddress);
        pswrd1 = findViewById(R.id.passwordTextInputLayout);
        pswrd2 = findViewById(R.id.confirmPasswordTextInputLayout);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e = String.valueOf(email.getEditText().getText());
                String p1 = String.valueOf(pswrd1.getEditText().getText());
                String p2 = String.valueOf(pswrd2.getEditText().getText());

                if (checkBox.isChecked()) {
                    if (isEmailValid(e) && !e.isEmpty() && !p1.isEmpty() && !p2.isEmpty() && p1.equals(p2)) {
                        Utils.user.setEmail(e);
                        Utils.user.setPassword(p1);
                        User user = new User(e, p1);
                        Call<ResponseUser> call = api.signUpByEmailAndPswrd(APIKEY, user);
                        call.enqueue(new Callback<ResponseUser>() {
                            @Override
                            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Новый пользователь успешно добавлен", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Ошибка: " + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseUser> call, Throwable t) {
                                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        if (!isEmailValid(e)) {
                            Toast.makeText(SignUpActivity.this, "Некорректный email", Toast.LENGTH_SHORT).show();
                        } else if (!p1.equals(p2)) {
                            Toast.makeText(SignUpActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Примите условия соглашения", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(view);
            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void signIn(View view) {
        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
    }
}