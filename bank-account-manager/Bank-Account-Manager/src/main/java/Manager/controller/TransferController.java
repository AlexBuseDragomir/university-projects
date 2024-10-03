package manager.controller;

import manager.animations.Shaker;
import manager.database.DatabaseHandler;
import manager.model.Transaction;
import manager.model.User;
import manager.utility.CurrencyUtility;
import manager.utility.ValidationUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferController extends ShowScreenController {

    @FXML
    private Label accountsButton;

    @FXML
    private TextField destinationAccount;

    @FXML
    private TextField amountToTransfer;

    @FXML
    private ComboBox<String> currentAccountComboBox;

    @FXML
    private Label transferButton;

    @FXML
    private Pane payPopUpPane;

    @FXML
    private Label logoutButton;

    private DatabaseHandler databaseHandler;

    private User user;

    @FXML
    public void logoutUser() {

        showScreen(logoutButton, "/views/startup.fxml");

    }

    @FXML
    public void gotoAccountsScreen() {

        showScreen(accountsButton, "/views/accounts.fxml");

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
    public void gotoProfileScreen() {

        showScreen(accountsButton, "/views/profile.fxml");

    }

    @FXML
    void transferAmountToAccount() throws SQLException, ClassNotFoundException {

        String sourceAccountName = currentAccountComboBox.getSelectionModel().getSelectedItem();
        String destinationAccountName = destinationAccount.getText();

        if (ValidationUtility.checkIfStringIsMoneyFormat(amountToTransfer.getText()) &&
                ValidationUtility.doesDatabaseContainAccount(destinationAccountName) &&
                ! ValidationUtility.doesAccountBelongToUser(user, destinationAccountName) &&
                currentAccountComboBox.getSelectionModel().getSelectedIndex() != -1 &&
                checkEnoughBalanceInAccount(sourceAccountName, Double.parseDouble(amountToTransfer.getText()))){

            ResultSet accountCurrencyResult = databaseHandler.getCurrencyFromAccount(sourceAccountName);
            String accountCurrency = "";
            if (accountCurrencyResult.next()) {
                accountCurrency = accountCurrencyResult.getString("accountCurrency");
            }

            Transaction transaction = new Transaction(sourceAccountName, destinationAccountName,
                    Double.parseDouble(amountToTransfer.getText()), accountCurrency);

            databaseHandler.createTransaction(transaction);

            databaseHandler.updateAccountBalance(sourceAccountName,
                    -1.0 * Double.parseDouble(amountToTransfer.getText()));
            databaseHandler.updateAccountBalance(destinationAccountName,
                    CurrencyUtility.getBalanceInDestinationCurrency(sourceAccountName, destinationAccountName,
                            Double.parseDouble(amountToTransfer.getText())));

            payPopUpPane.setVisible(true);
            transferButton.setVisible(false);

        } else if ( ! ValidationUtility.checkIfStringIsMoneyFormat(amountToTransfer.getText()) ||
                    ! checkEnoughBalanceInAccount(sourceAccountName, Double.parseDouble(amountToTransfer.getText()))) {

            Shaker shaker = new Shaker(amountToTransfer);
            shaker.shake();

        } else if ( ! ValidationUtility.doesDatabaseContainAccount(destinationAccountName) ||
                    ValidationUtility.doesAccountBelongToUser(user, destinationAccountName)) {

            Shaker shaker = new Shaker(destinationAccount);
            shaker.shake();

        } else if (currentAccountComboBox.getSelectionModel().getSelectedIndex() == -1) {

            Shaker shaker = new Shaker(currentAccountComboBox);
            shaker.shake();

        }

    }

    @FXML
    void okPressed(MouseEvent event) {
        payPopUpPane.setVisible(false);
        transferButton.setVisible(true);
        amountToTransfer.setText("");
        destinationAccount.setText("");

    }

    @FXML
    public void initialize() throws SQLException {

        user = new User();
        databaseHandler = new DatabaseHandler();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/login.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginController loginController = loader.getController();
        ResultSet currentUser = databaseHandler.getUser(loginController.getUser());

        if(currentUser.next()) {
            this.user.setUserId(Integer.parseInt(currentUser.getString("userid")));
            this.user.setUserName(currentUser.getString("userName"));
            this.user.setFirstName(currentUser.getString("firstName"));
            this.user.setLastName(currentUser.getString("lastName"));
            this.user.setAddress(currentUser.getString("address"));
            this.user.setEmail(currentUser.getString("email"));
        }

        populateCurrentAccountComboBox(user);

    }

    private void populateCurrentAccountComboBox(User user) throws SQLException {
        currentAccountComboBox.setStyle("-fx-font-family: Arial");
        currentAccountComboBox.setStyle("-fx-font-size: 17");
        databaseHandler = new DatabaseHandler();
        ResultSet userAccounts = databaseHandler.getAccountsByUser(user);
        currentAccountComboBox.getItems().clear();
        while(userAccounts.next()) {
            currentAccountComboBox.getItems().add(userAccounts.getString("accountName"));
        }
    }

    private boolean checkEnoughBalanceInAccount(String accountName, Double amountToPay) throws SQLException {

        databaseHandler = new DatabaseHandler();
        ResultSet currentAccount = databaseHandler.getAccountsByName(accountName);
        double accountBalance = 0;
        if (currentAccount.next()) {
            accountBalance = Double.parseDouble(currentAccount.getString("accountBalance"));
        }
        return accountBalance >= amountToPay;

    }
}
