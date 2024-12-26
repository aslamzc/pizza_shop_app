package com.aslam.app.Services;

import com.aslam.app.Entity.User;
import com.aslam.app.Interfaces.IService;
import com.aslam.app.Utils.SystemScanner;

public class ProfileService implements IService {

    final SystemScanner scanner;
    private User user;

    public ProfileService(SystemScanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        scanner.title("Profile Update");
        updateUserInformation();
        updateAccountPassword();
    }

    private void updateUserInformation() {
        if (scanner.questionBoolean("Would you like to update your profile information?")) {
            String name = scanner
                    .question("Enter your name (" + user.getName() + ")? leave blank to keep current name");
            String phone = scanner
                    .question("Enter your phone number (" + user.getPhone()
                            + ")? leave blank to keep current phone number");
            user.setName(name.isEmpty() ? user.getName() : name)
                    .setPhone(phone.isEmpty() ? user.getPhone() : phone);
            scanner.success("Your profile has been updated.");
        }
    }

    private void updateAccountPassword() {
        if (scanner.questionBoolean("Would you like to update your password?")) {
            boolean inCorrectPassword = true;
            do {
                String currentPassword = scanner.question("Enter your current password: ", true);
                if (!user.isPasswordMatch(currentPassword)) {
                    scanner.error("Invalid password. Please try again.");
                    if (!scanner.questionBoolean("Would you like to try again?")) {
                        break;
                    }
                    continue;
                }

                inCorrectPassword = false;
                String password = scanner.question("Enter your new password: ", true);
                String confirmPassword = scanner.question("Confirm your new password: ", true);

                if (!password.equals(confirmPassword)) {
                    scanner.error("Passwords do not match. Please try again.");
                    inCorrectPassword = true;
                    continue;
                }

                user.updatePassword(password);
                scanner.success("Your password has been updated.");

            } while (inCorrectPassword);
        }
    }

    public IService setUser(User user) {
        this.user = user;
        return this;
    }
}
