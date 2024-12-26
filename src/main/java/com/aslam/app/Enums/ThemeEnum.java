package com.aslam.app.Enums;

public enum ThemeEnum {

    WHITE("\033[0m"),
    RED("\033[31m"),
    BLACK_BOLD("\033[1;30m"),
    RED_BOLD("\033[1;31m"),
    LIGHT_GREEN_BOLD("\033[1;92m"),
    GREEN_BOLD("\033[1;32m"),
    YELLOW_BOLD("\033[1;33m"),
    WHITE_UNDERLINED("\033[4;37m"),
    LIGHT_RED_BACKGROUND("\033[48;5;124m"),
    WHITE_BACKGROUND("\033[47m");

    private final String code;

    ThemeEnum(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
