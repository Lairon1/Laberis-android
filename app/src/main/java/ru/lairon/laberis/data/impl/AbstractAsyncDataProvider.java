package ru.lairon.laberis.data.impl;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.function.Consumer;

import ru.lairon.laberis.data.DataProvider;
import ru.lairon.laberis.moder.Product;
import ru.lairon.laberis.moder.User;

public abstract class AbstractAsyncDataProvider implements DataProvider {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void loginAsync(String login, String password, Consumer<User> response, Consumer<Exception> error) {
        new CustomAsyncTask(response, error, () -> login(login, password)).execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void registrationAsync(String login, String password, String firstname, String lastname, Consumer<User> response, Consumer<Exception> error) {
        new CustomAsyncTask(response, error, () -> registration(login, password, firstname, lastname)).execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void loadAllProductsAsync(Consumer<List<Product>> response, Consumer<Exception> error) {
        new CustomAsyncTask(response, error, this::loadAllProducts).execute();
    }

    private class CustomAsyncTask extends AsyncTask<Void, Void, Void> {

        private Consumer response;
        private Consumer error;
        private Exec execute;

        private Exception exception;
        private Object responseObject;

        public CustomAsyncTask(Consumer response, Consumer error, Exec execute) {
            this.response = response;
            this.error = error;
            this.execute = execute;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                responseObject = execute.execute();
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Void unused) {
            if (exception != null) {
                if (error != null)
                    error.accept(exception);
            } else {
                response.accept(responseObject);
            }
        }


    }

    private interface Exec<T> {
        T execute() throws Exception;
    }

}
