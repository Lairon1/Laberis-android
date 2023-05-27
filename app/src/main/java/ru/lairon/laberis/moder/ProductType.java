package ru.lairon.laberis.moder;

public enum ProductType {
    PROCESSOR("Процессор"),
    MOTHER_BOARD("Материнская плата"),
    VIDEO_CARD("Видеокарта"),
    ORM("Оперативная память"),
    FRAME("Корпус"),
    POWER_UNIT("Блок питания"),
    COOLER("Охлаждение"),
    DISK("Жесткий диск"),
    MONITOR("Монитор");

    private String russianName;

    ProductType(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }
}