package manager.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import manager.animations.Shaker;
import manager.database.DatabaseHandler;
import manager.model.User;
import manager.utility.ValidationUtility;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController extends ShowScreenController {

    @FXML
    private Label registerButton;

    @FXML
    private TextField registerUsername;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private TextField registerFirstName;

    @FXML
    private TextField registerEmail;

    @FXML
    private TextField registerAddress;

    @FXML
    private TextField registerLastName;

    @FXML
    private Label cancelLoginButton;

    private DatabaseHandler databaseHandler;

    @FXML
    public void cancelLogin() {

        showScreen(cancelLoginButton, "/views/startup.fxml");

    }

    @FXML
    public void registerUser() {

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.validateUser(new User(registerUsername.getText()));
        try {
            if (!resultSet.next()) {
                if ( ! ValidationUtility.meetsFirstNameFormat(registerFirstName.getText())) {
                    Shaker shaker = new Shaker(registerFirstName);
                    shaker.shake();
                } else if ( ! ValidationUtility.meetsLastNameFormat(registerLastName.getText())) {
                    Shaker shaker = new Shaker(registerLastName);
                    shaker.shake();
                } else if ( ! ValidationUtility.meetsUsernameFormat(registerUsername.getText())) {
                    Shaker shaker = new Shaker(registerUsername);
                    shaker.shake();
                } else if ( ! ValidationUtility.meetsEmailFormat(registerEmail.getText())) {
                    Shaker shaker = new Shaker(registerEmail);
                    shaker.shake();
                } else if ( ! ValidationUtility.meetsAddressFormat(registerAddress.getText())) {
                    Shaker shaker = new Shaker(registerAddress);
                    shaker.shake();
                } else if ( ! ValidationUtility.meetsPasswordFormat(registerPassword.getText())) {
                    Shaker shaker = new Shaker(registerPassword);
                    shaker.shake();
                } else {
                    createUser();
                    showScreen(registerFirstName, "/views/login.fxml");
                }
            } else {
                Shaker shaker = new Shaker(registerUsername);
                shaker.shake();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createUser() {

        databaseHandler = new DatabaseHandler();

        String firstName = registerFirstName.getText();
        String lastName = registerLastName.getText();
        String userName = registerUsername.getText();
        String password = registerPassword.getText();
        String email = registerEmail.getText();
        String address = registerAddress.getText();

        User user = new User(firstName, lastName, userName, password, email, address);
        databaseHandler.registerUser(user);

    }
}
