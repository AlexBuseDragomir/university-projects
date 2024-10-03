package manager.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import manager.animations.Shaker;
import manager.database.DatabaseHandler;
import manager.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends ShowScreenController {

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Label cancelLoginButton;

    private static User user;

    static {
        user = new User();
    }

    @FXML
    public void cancelLogin() {
        showScreen(cancelLoginButton, "/views/startup.fxml");
    }

    @FXML
    public void loginUser() {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        if (loginUsername.getText().equals("")) {
            Shaker usernameShaker = new Shaker(loginUsername);
            usernameShaker.shake();
        } else if (loginPassword.getText().equals("")) {
            Shaker passwordShaker = new Shaker(loginPassword);
            passwordShaker.shake();
        } else {
            String loginText = loginUsername.getText().trim();
            String loginPwd = loginPassword.getText().trim();

            user.setUserName(loginText);
            user.setPassword(loginPwd);

            ResultSet userRow = databaseHandler.getUser(user);
            try {
                if (userRow.next()) {
                    showScreen(loginUsername, "/views/main.fxml");
                } else {
                    Shaker usernameShaker = new Shaker(loginUsername);
                    Shaker passwordShaker = new Shaker(loginPassword);
                    passwordShaker.shake();
                    usernameShaker.shake();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public User getUser() {
        return user;
    }
}
