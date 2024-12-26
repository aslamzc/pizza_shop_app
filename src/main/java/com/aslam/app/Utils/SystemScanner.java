package com.aslam.app.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import com.aslam.app.Enums.ThemeEnum;

public class SystemScanner {

    private static final String INVALID_INPUT = "Invalid input. Please enter a valid number:";
    private static final String ENTER_VALID_NUMBER = "Please enter a valid number:";
    private static final String ENTER_VALID_DATE = "Please enter a valid date:";
    private static final String ENTER_VALID_VALUE = "Please enter a valid value:";
    private final Scanner scanner;

    private static final SystemScanner INSTANCE = new SystemScanner();

    private SystemScanner() {
        this.scanner = new Scanner(System.in);
    }

    public static SystemScanner getInstance() {
        return INSTANCE;
    }

    public void heading(String text) {
        displayText(String.valueOf(ThemeEnum.WHITE_BACKGROUND) + ThemeEnum.BLACK_BOLD + text);
    }

    public void highlight(String text) {
        displayText(String.valueOf(ThemeEnum.LIGHT_RED_BACKGROUND) + ThemeEnum.BLACK_BOLD + text);
    }

    public void text(String text) {
        displayText(text);
    }

    public void title(String text) {
        String stars = "*".repeat(10);
        String separator = "=".repeat(text.length() + 22);
        displayText(separator);
        displayText(ThemeEnum.YELLOW_BOLD + stars + " " + text + " " + stars);
        displayText(separator);
    }

    public String question(String question) {
        return question(question, false);
    }

    public String question(String question, boolean isRequired) {
        prompt(question);
        while (true) {
            String answer = scanner.nextLine().trim();
            if (!isRequired || !answer.isEmpty()) {
                return answer;
            }
            error(ENTER_VALID_VALUE);
        }
    }

    public int questionNumber(String question) {
        return questionNumber(question, false);
    }

    public int questionNumber(String question, boolean isRequired) {
        prompt(question);
        while (true) {
            String value = scanner.nextLine().trim();
            if (!isRequired && value.isEmpty())
                return 0;
            try {
                int answer = Integer.parseInt(value);
                if (answer > 0 || !isRequired)
                    return answer;
                error(ENTER_VALID_NUMBER);
            } catch (NumberFormatException e) {
                error(INVALID_INPUT);
            }
        }
    }

    public LocalDate questionDate(String question) {
        prompt(question);
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                error(ENTER_VALID_DATE);
            }
        }
    }

    public boolean questionBoolean(String question) {
        prompt(question);
        displayText(ThemeEnum.RED + "(1)" + ThemeEnum.WHITE + " Yes      " + ThemeEnum.RED + "(2)" + ThemeEnum.WHITE
                + " No");
        return "1".equals(getValidatedOption("Please select an option:", "1", "2"));
    }

    public int questionOption(String question, String optionHeading, String[] options) {
        prompt(optionHeading);
        for (int i = 0; i < options.length; i++) {
            option(i + 1, options[i]);
        }
        return Integer.parseInt(getValidatedOption(question, options.length));
    }

    public void success(String text) {
        displayText(ThemeEnum.GREEN_BOLD + text);
    }

    public void error(String text) {
        displayText(ThemeEnum.RED_BOLD + text);
    }

    public void prompt(String question) {
        displayText(ThemeEnum.LIGHT_GREEN_BOLD + question);
    }

    public void option(int id, String text) {
        displayText(ThemeEnum.RED + "(" + id + ") " + ThemeEnum.WHITE + text);
    }

    public void blankSpace() {
        System.out.println();
    }

    private void displayText(String text) {
        System.out.println(text + ThemeEnum.WHITE);
    }

    private String getValidatedOption(String prompt, String... validOptions) {
        while (true) {
            prompt(prompt);
            String input = scanner.nextLine().trim();
            for (String option : validOptions) {
                if (input.equals(option))
                    return input;
            }
            error("Invalid option. Please try again.");
        }
    }

    private String getValidatedOption(String prompt, int maxOption) {
        while (true) {
            int answer = questionNumber(prompt, true);
            if (answer > 0 && answer <= maxOption)
                return String.valueOf(answer);
            error("Please enter a valid option.");
        }
    }
}
