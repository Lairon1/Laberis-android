package ru.lairon.laberis;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import ru.lairon.laberis.data.exceptions.UserNotFoundException;
import ru.lairon.laberis.data.exceptions.WrongPasswordException;
import ru.lairon.laberis.data.impl.RestAPIDataProvider;

public class LoginActivity extends LaberisActivity {

    private EditText loginET, passwordET;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Application.setDataProvider(new RestAPIDataProvider("http://10.0.2.2:8080"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginET = findViewById(R.id.loginET);
        passwordET = findViewById(R.id.passwordET);

        findViewById(R.id.button_register).setOnClickListener(v -> startActivity(RegistrationActivity.class));

        findViewById(R.id.button_login).setOnClickListener(v -> {

            String login = loginET.getText().toString().trim(),
                    password = passwordET.getText().toString().trim();
            if (login.length() == 0) {
                toast("Пароль не может быть пустым");
                return;
            }
            if (password.length() == 0) {
                toast("Пароль не может быть пустым");
                return;
            }
            getDataProvider().loginAsync(login, password, user -> {
                startActivity(ProductsActivity.class);
            }, exception -> {
                if (exception instanceof UserNotFoundException) {
                    toast("Пользователя с логином " + login + " не существует.");
                } else if (exception instanceof WrongPasswordException) {
                    toast("Неверный пароль");
                } else {
                    toast("неизвестная ошибка сервера. Попробуйте позже.");
                }
            });
        });
    }


}