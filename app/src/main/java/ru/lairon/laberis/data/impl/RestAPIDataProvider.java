package ru.lairon.laberis.data.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ru.lairon.laberis.data.exceptions.LoginAlreadyExistsException;
import ru.lairon.laberis.data.exceptions.ServerErrorException;
import ru.lairon.laberis.data.exceptions.SimplePasswordException;
import ru.lairon.laberis.data.exceptions.UserNotFoundException;
import ru.lairon.laberis.data.exceptions.WrongPasswordException;
import ru.lairon.laberis.moder.Product;
import ru.lairon.laberis.moder.ProductType;
import ru.lairon.laberis.moder.User;

public class RestAPIDataProvider extends AbstractAsyncDataProvider {

    private final String address;

    public RestAPIDataProvider(String address) {
        this.address = address;
    }


    @Override
    public User login(String login, String password)
            throws JSONException,
            UnirestException,
            UserNotFoundException,
            WrongPasswordException,
            ServerErrorException {


        JSONObject requestBody = new JSONObject();
        requestBody.put("login", login);
        requestBody.put("password", password);

        HttpResponse<String> response = Unirest.post(address + "/user/login")
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(requestBody.toString())
                .asString();
        switch (response.getCode()) {
            case 200:
                return parseUser(new JSONObject(response.getBody()));
            case 404:
                throw new UserNotFoundException();
            case 602:
                throw new WrongPasswordException();
            default:
                throw new ServerErrorException();
        }
    }

    private User parseUser(JSONObject object) throws JSONException {

        return new User(
                object.getString("login"),
                null,
                object.getString("firstname"),
                object.getString("lastname"),
                object.getString("token")
        );

    }

    @Override
    public User registration(String login, String password, String firstname, String lastname)
            throws JSONException,
            SimplePasswordException,
            LoginAlreadyExistsException,
            UnirestException, ServerErrorException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("login", login);
        requestBody.put("password", password);
        requestBody.put("firstname", firstname);
        requestBody.put("lastname", lastname);

        HttpResponse<String> response = Unirest.post(address + "/user/register")
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(requestBody.toString())
                .asString();

        switch (response.getCode()) {
            case 200:
                return parseUser(new JSONObject(response.getBody()));
            case 601:
                throw new SimplePasswordException();
            case 603:
                throw new LoginAlreadyExistsException();
            default:
                throw new ServerErrorException();
        }
    }

    @Override
    public List<Product> loadAllProducts() throws UnirestException, ServerErrorException, JSONException {
        HttpResponse<String> response = Unirest.get(address + "/product/all").asString();
        if (response.getCode() != 200)
            throw new ServerErrorException();
        List<Product> products = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response.getBody());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Product product = parseProduct(jsonObject);
            products.add(product);
            product.setImage(loadImage(product));
        }

        return products;
    }

    private Bitmap loadImage(Product product) throws UnirestException {
        HttpResponse<InputStream> response = Unirest.get(address + "/image/get?id=" + product.getId()).asBinary();
        if(response.getCode() != 200) return null;
        return BitmapFactory.decodeStream(response.getBody());
    }

    private Product parseProduct(JSONObject object) throws JSONException {
        JSONObject specificationsArray = object.getJSONObject("specifications");
        Map<String, String> specifications = new HashMap<>();
        Iterator<String> keys = specificationsArray.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            specifications.put(next, specificationsArray.getString(next));
        }

        Product product = new Product(
                object.getLong("id"),
                object.getString("title"),
                object.getString("description"),
                object.getDouble("price"),
                ProductType.valueOf(object.getString("type")),
                specifications
                );
        return product;
    }




}
