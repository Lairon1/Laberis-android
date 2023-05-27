package ru.lairon.laberis.data;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;

import java.util.List;
import java.util.function.Consumer;

import ru.lairon.laberis.data.exceptions.LoginAlreadyExistsException;
import ru.lairon.laberis.data.exceptions.ServerErrorException;
import ru.lairon.laberis.data.exceptions.SimplePasswordException;
import ru.lairon.laberis.data.exceptions.UserNotFoundException;
import ru.lairon.laberis.data.exceptions.WrongPasswordException;
import ru.lairon.laberis.moder.Product;
import ru.lairon.laberis.moder.User;

public interface DataProvider {

    User login(String login, String password) throws JSONException, UnirestException, UserNotFoundException, WrongPasswordException, ServerErrorException;

    User registration(String login, String password, String firstname, String lastname) throws JSONException, SimplePasswordException, LoginAlreadyExistsException, UnirestException, ServerErrorException;

    List<Product> loadAllProducts() throws UnirestException, ServerErrorException, JSONException;

    void loginAsync(String login, String password, Consumer<User> response, Consumer<Exception> error);

    default void loginAsync(String login, String password, Consumer<User> response) {
        loginAsync(login, password, response, null);
    }

    void registrationAsync(String login, String password, String firstname, String lastname, Consumer<User> response, Consumer<Exception> error);

    default void registrationAsync(String login, String password, String firstname, String lastname, Consumer<User> response) {
        registrationAsync(login, password, firstname, lastname, response, null);
    }

    void loadAllProductsAsync(Consumer<List<Product>> response, Consumer<Exception> error);

    default void loadAllProductsAsync(Consumer<List<Product>> response) {
        loadAllProductsAsync(response, null);
    }

}
