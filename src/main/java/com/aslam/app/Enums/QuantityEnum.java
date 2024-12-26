package com.aslam.app.Enums;

public enum QuantityEnum {

    SMALL(0),
    MEDIUM(1),
    LARGE(2);

    private final int code;

    QuantityEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static QuantityEnum fromCode(int code) {
        for (QuantityEnum size : QuantityEnum.values()) {
            if (size.getCode() == code) {
                return size;
            }
        }
        return null;
    }
}