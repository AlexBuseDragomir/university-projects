package manager.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import manager.animations.Shaker;
import manager.database.DatabaseHandler;
import manager.model.User;
import manager.utility.ValidationUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ProfileController extends ShowScreenController {

    @FXML
    private Label accountsButton;

    @FXML
    private Label editProfileButton;

    @FXML
    private Label transactionHistoryButton;

    @FXML
    private Label changePasswordButton;

    @FXML
    private Label profileUsername;

    @FXML
    private Label profileFirstName;

    @FXML
    private Label profileLastName;

    @FXML
    private Label profileAddress;

    @FXML
    private Label profileEmail;

    @FXML
    private TextField profileLastNameEdit;

    @FXML
    private TextField profileFirstNameEdit;

    @FXML
    private TextField profileAddressEdit;

    @FXML
    private Label saveEditProfileButton;

    @FXML
    private Label cancelEditProfileButton;

    @FXML
    private Pane popUpPane;

    @FXML
    private PasswordField txtOldPassword;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Label logoutButton;

    private DatabaseHandler databaseHandler;

    @FXML
    void gotoTransactionHistoryScreen() {
        showScreen(transactionHistoryButton, "/views/transaction_history.fxml");
    }

    @FXML
    public void logoutUser() {

        showScreen(logoutButton, "/views/startup.fxml");

    }

    @FXML
    public void gotoHomeScreen() {

        showScreen(accountsButton, "/views/main.fxml");

    }

    @FXML
    public void gotoPayScreen() {

        showScreen(accountsButton, "/views/pay.fxml");

    }

    @FXML
    public void gotoTransferScreen() {

        showScreen(accountsButton, "/views/transfer.fxml");

    }

    @FXML
    public void gotoAccountsScreen() {

        showScreen(accountsButton, "/views/accounts.fxml");

    }

    @FXML
    public void cancelPasswordChange() {

        popUpPane.setVisible(false);
        changePasswordButton.setVisible(true);
        transactionHistoryButton.setVisible(true);
        editProfileButton.setVisible(true);
        resetPasswordFields();

    }

    @FXML
    public void savePasswordChange() throws SQLException, ClassNotFoundException {

        String userPassword = "";
        ResultSet userResult = databaseHandler.getUserByUsername(new User(profileUsername.getText()));

        while (userResult.next()) {
            userPassword = userResult.getString("password");
        }

        if (!txtOldPassword.getText().equals("") &&
                ! txtNewPassword.getText().equals("") &&
                ! txtConfirmPassword.getText().equals("")) {

            if (userPassword.equals(txtOldPassword.getText())
                    && txtNewPassword.getText().equals(txtConfirmPassword.getText())
                    && !txtNewPassword.getText().equals(userPassword)) {

                if (ValidationUtility.meetsPasswordFormat(txtNewPassword.getText())) {

                    databaseHandler.editUserPassword(profileUsername.getText(), txtConfirmPassword.getText());

                    popUpPane.setVisible(false);
                    editProfileButton.setVisible(true);
                    transactionHistoryButton.setVisible(true);
                    changePasswordButton.setVisible(true);

                    showScreen(txtOldPassword, "/views/login.fxml");

                } else {

                    Shaker newPasswordShaker = new Shaker(txtNewPassword);
                    Shaker confirmPasswordShaker = new Shaker(txtConfirmPassword);
                    newPasswordShaker.shake();
                    confirmPasswordShaker.shake();

                }



            } else if (!userPassword.equals(txtOldPassword.getText())) {

                Shaker shaker = new Shaker(txtOldPassword);
                shaker.shake();

            } else if (!txtNewPassword.getText().equals(txtConfirmPassword.getText())) {

                Shaker newPasswordShaker = new Shaker(txtNewPassword);
                Shaker confirmPasswordShaker = new Shaker(txtConfirmPassword);
                newPasswordShaker.shake();
                confirmPasswordShaker.shake();

            } else if (txtNewPassword.getText().equals(txtConfirmPassword.getText())
                    && txtNewPassword.getText().equals(userPassword)) {

                Shaker oldPasswordShaker = new Shaker(txtOldPassword);
                Shaker newPasswordShaker = new Shaker(txtNewPassword);
                Shaker confirmPasswordShaker = new Shaker(txtConfirmPassword);
                oldPasswordShaker.shake();
                newPasswordShaker.shake();
                confirmPasswordShaker.shake();
            }

        } else {

            Shaker oldPasswordShaker = new Shaker(txtOldPassword);
            Shaker newPasswordShaker = new Shaker(txtNewPassword);
            Shaker confirmPasswordShaker = new Shaker(txtConfirmPassword);
            oldPasswordShaker.shake();
            newPasswordShaker.shake();
            confirmPasswordShaker.shake();
        }

    }

    @FXML
    public void promptChangePassword() {

        popUpPane.setVisible(true);
        changePasswordButton.setVisible(false);
        transactionHistoryButton.setVisible(false);
        editProfileButton.setVisible(false);
        resetPasswordFields();

    }

    @FXML
    public void cancelEditProfile() {

        profileLabelsVisible(true);
        profileEditTextFieldsVisible(false);

        editProfileButton.setVisible(true);
        transactionHistoryButton.setVisible(true);
        changePasswordButton.setVisible(true);

        cancelEditProfileButton.setVisible(false);
        saveEditProfileButton.setVisible(false);

    }

    @FXML
    public void saveEditProfile() throws SQLException, ClassNotFoundException {

        User user = new User();
        ResultSet userResult = databaseHandler.getUserByUsername(new User(profileUsername.getText()));

        while (userResult.next()) {
            user.setFirstName(userResult.getString("firstName"));
            user.setLastName(userResult.getString("lastName"));
            user.setAddress(userResult.getString("address"));
            user.setUserName(userResult.getString("userName"));
        }

        user.setFirstName(profileFirstNameEdit.getText());
        user.setLastName(profileLastNameEdit.getText());
        user.setAddress(profileAddressEdit.getText());

        if (ValidationUtility.meetsFirstNameFormat(profileFirstNameEdit.getText()) &&
                ValidationUtility.meetsLastNameFormat(profileLastNameEdit.getText()) &&
                ValidationUtility.meetsAddressFormat(profileAddressEdit.getText())) {

            databaseHandler.editUser(user);

            profileLabelsVisible(true);
            profileEditTextFieldsVisible(false);

            editProfileButton.setVisible(true);
            transactionHistoryButton.setVisible(true);
            changePasswordButton.setVisible(true);

            cancelEditProfileButton.setVisible(false);
            saveEditProfileButton.setVisible(false);

            initialize();

        } else if ( ! ValidationUtility.meetsFirstNameFormat(profileFirstNameEdit.getText())) {

            Shaker shaker = new Shaker(profileFirstNameEdit);
            shaker.shake();

        } else if ( ! ValidationUtility.meetsLastNameFormat(profileLastNameEdit.getText())) {

            Shaker shaker = new Shaker(profileLastNameEdit);
            shaker.shake();

        } else if ( ! ValidationUtility.meetsAddressFormat(profileAddressEdit.getText())) {

            Shaker shaker = new Shaker(profileAddressEdit);
            shaker.shake();

        }

    }

    @FXML
    public void promptEditProfile() {

        profileLabelsVisible(false);
        profileEditTextFieldsVisible(true);
        initEditTextFields();

        editProfileButton.setVisible(false);
        transactionHistoryButton.setVisible(false);
        changePasswordButton.setVisible(false);

        cancelEditProfileButton.setVisible(true);
        saveEditProfileButton.setVisible(true);

    }

    @FXML
    public void initialize() throws SQLException {

        databaseHandler = new DatabaseHandler();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/login.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginController loginController = loader.getController();
        ResultSet user = databaseHandler.getUser(loginController.getUser());

        while(user.next()) {
            populateLabel(profileUsername, user.getString("username"));
            populateLabel(profileFirstName, user.getString("firstName"));
            populateLabel(profileLastName, user.getString("lastName"));
            populateLabel(profileAddress, user.getString("address"));
            populateLabel(profileEmail, user.getString("email"));
        }

    }

    private void profileLabelsVisible(boolean state) {

        profileFirstName.setVisible(state);
        profileLastName.setVisible(state);
        profileAddress.setVisible(state);

    }

    private void profileEditTextFieldsVisible(boolean state) {

        profileFirstNameEdit.setVisible(state);
        profileLastNameEdit.setVisible(state);
        profileAddressEdit.setVisible(state);

    }

    private void initEditTextFields() {

        profileFirstNameEdit.setText(profileFirstName.getText());
        profileLastNameEdit.setText(profileLastName.getText());
        profileAddressEdit.setText(profileAddress.getText());

    }

    private void populateLabel(Label label, String string) {

        label.setText(string);

    }

    private void resetPasswordFields() {

        txtOldPassword.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");

    }
}
