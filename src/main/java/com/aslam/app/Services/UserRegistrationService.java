package com.aslam.app.Services;

import java.util.List;
import com.aslam.app.Entity.User;
import com.aslam.app.Interfaces.IService;
import com.aslam.app.Statuses.NewUserState;
import com.aslam.app.Utils.SystemScanner;
import com.aslam.app.Utils.Validator;

public class UserRegistrationService implements IService {

    private final SystemScanner scanner;
    private final List<User> users;
    private final Validator validation;

    public UserRegistrationService(SystemScanner scanner, List<User> users) {
        this.scanner = scanner;
        this.users = users;
        this.validation = Validator.getInstance();
    }

    @Override
    public void handle() {

        scanner.title(" User Signup ");
        String name = scanner.question("Enter your name: ", true);
        String email = getMail();
        if (email == null)
            return;

        String password = scanner.question("Enter your password: ", true);
        String phone = scanner.question("Enter your phone number: ", true);

        User user = new User(name, email, password, phone);
        user.setState(new NewUserState());
        user.getState().display();

        users.add(user);
        scanner.success("Please signin to place your order.");

    }

    private String getMail() {
        String email;
        boolean validEmail;
        do {
            email = scanner.question("Enter your email: ", true);
            validEmail = validation.isValidEmail(email);

            if (!validEmail || validation.isEmailExist(email, users)) {
                String question = "The email you entered is invalid or already exists. Do you want to try again?";
                if (!scanner.questionBoolean(question))
                    return null;
            }

        } while (!validEmail);

        return email;
    }

}
