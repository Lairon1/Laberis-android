package ru.lairon.laberis;

import ru.lairon.laberis.data.DataProvider;

public class Application {

    private static DataProvider dataProvider;

    public static DataProvider getDataProvider() {
        return dataProvider;
    }

    public static void setDataProvider(DataProvider dataProvider) {
        Application.dataProvider = dataProvider;
    }
}
