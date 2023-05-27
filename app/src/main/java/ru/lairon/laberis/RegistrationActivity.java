package ru.lairon.laberis;

import android.os.Bundle;
import android.widget.EditText;

import ru.lairon.laberis.data.exceptions.LoginAlreadyExistsException;
import ru.lairon.laberis.data.exceptions.SimplePasswordException;

public class RegistrationActivity extends LaberisActivity {

    private EditText loginET, passwordET, firstnameET, lastnameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        loginET = findViewById(R.id.loginET);
        passwordET = findViewById(R.id.passwordET);
        firstnameET = findViewById(R.id.firstnameET);
        lastnameET = findViewById(R.id.lastnameET);

        findViewById(R.id.button_login).setOnClickListener(v -> startActivity(LoginActivity.class));
        findViewById(R.id.button_registration).setOnClickListener(v -> {
            String login = loginET.getText().toString().trim(),
                    password = passwordET.getText().toString().trim(),
                    firstname = firstnameET.getText().toString().trim(),
                    lastname = lastnameET.getText().toString().trim();
            if(login.length() == 0){
                toast("Логин не может быть пустым");
                return;
            }
            if(password.length() == 0){
                toast("Пароль не может быть пустым");
                return;
            }
            if(firstname.length() == 0){
                toast("Имя не может быть пустым");
                return;
            }
            if(lastname.length() == 0){
                toast("Фамилия не может быть пустым");
                return;
            }

            getDataProvider().registrationAsync(login, password, firstname, lastname, user -> {
                startActivity(ProductsActivity.class);
            }, exception -> {
                if(exception instanceof SimplePasswordException){
                    toast("Пароль слишком простой");
                }else if(exception instanceof LoginAlreadyExistsException){
                    toast("Логин " + login + " уже используется");
                }else{
                    toast("неизвестная ошибка сервера. Попробуйте позже.");
                }
            });

        });


    }
}