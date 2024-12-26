package com.aslam.app.Services;

import java.util.List;
import com.aslam.app.Entity.User;
import com.aslam.app.Interfaces.IService;
import com.aslam.app.Statuses.SigninState;
import com.aslam.app.Statuses.SignoutState;
import com.aslam.app.Utils.SystemScanner;
import com.aslam.app.Utils.Validator;

public class SigninService implements IService {

    final private SystemScanner scanner;
    final private List<User> users;
    private User loggedInUser;
    private final MyAccountService dashboardService;
    private final Validator validation;

    public SigninService(
            MyAccountService dashboardService,
            SystemScanner scanner,
            List<User> users) {
        this.dashboardService = dashboardService;
        this.scanner = scanner;
        this.users = users;
        this.validation = Validator.getInstance();
    }

    @Override
    public void handle() {

        scanner.title("User Signin");
        if (!signinAction())
            return;

        dashboardService.setUserService(this).handle();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void logout() {
        loggedInUser.setState(new SignoutState());
        loggedInUser.getState().display();
        loggedInUser = null;
    }

    private boolean signinAction() {
        boolean isLoggedIn = true;
        do {
            String email = scanner.question("Enter your email: ", true);
            if (!validation.isValidEmail(email) || !validation.isEmailExist(email, users)) {
                String question = "The email you entered is invalid or not registered. Do you want to try again?";
                isLoggedIn = false;
                if (!scanner.questionBoolean(question))
                    return false;
            } else {
                String password = scanner.question("Enter your password: ", true);
                loggedInUser = signin(email, password);

                if (loggedInUser == null) {
                    isLoggedIn = false;
                    boolean tryAgain = scanner.questionBoolean("Invalid email or password. Do you want to try again?");
                    if (!tryAgain)
                        return false;
                }

                if (loggedInUser != null && loggedInUser.getState() instanceof SigninState) {
                    isLoggedIn = true;
                }
            }
        } while (!isLoggedIn);
        return true;
    }

    public User signin(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.isPasswordMatch(password)) {
                user.setState(new SigninState());
                user.getState().display();
                return user;
            }
        }
        return null;
    }
}
