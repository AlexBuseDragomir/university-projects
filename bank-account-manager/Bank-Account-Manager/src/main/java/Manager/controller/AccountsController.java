package manager.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import manager.triplet.SimpleTriplet;
import manager.animations.Shaker;
import manager.database.DatabaseHandler;
import manager.enums.Currency;
import manager.model.Account;
import manager.model.User;
import manager.utility.ValidationUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class AccountsController extends ShowScreenController {

    @FXML
    private Label homeButton;

    @FXML
    private Label logoutButton;

    @FXML
    private Label createAccountButton;

    @FXML
    private Pane accountPane1;

    @FXML
    private Label accountBalance1;

    @FXML
    private Label accountName1;

    @FXML
    private Label accountCurrency1;

    @FXML
    private Pane accountPane2;

    @FXML
    private Label accountBalance2;

    @FXML
    private Label accountName2;

    @FXML
    private Label accountCurrency2;

    @FXML
    private Pane accountPane3;

    @FXML
    private Label accountBalance3;

    @FXML
    private Label accountName3;

    @FXML
    private Label accountCurrency3;

    @FXML
    private Pane accountPane4;

    @FXML
    private Label accountBalance4;

    @FXML
    private Label accountName4;

    @FXML
    private Label accountCurrency4;

    @FXML
    private Pane accountPane5;

    @FXML
    private Label accountBalance5;

    @FXML
    private Label accountName5;

    @FXML
    private Label accountCurrency5;

    @FXML
    private Pane createAccountPopUp;

    @FXML
    private TextField accountBalanceAdd;

    @FXML
    private ComboBox<String> accountCurrencyAdd;

    @FXML
    private Label lblMaxAccountsReached;

    @FXML
    private Pane addBalancePane;

    @FXML
    private Label addBalanceAccountName;

    @FXML
    private Label addBalanceAccountCurrency;

    @FXML
    private TextField addBalanceAmount;


    private DatabaseHandler databaseHandler;
    private User user;

    @FXML
    public void deleteAccount1() throws SQLException, ClassNotFoundException {
        databaseHandler.deleteAccount(accountName1.getText());
        initialize();
    }

    @FXML
    public void deleteAccount2() throws SQLException, ClassNotFoundException {
        databaseHandler.deleteAccount(accountName2.getText());
        initialize();
    }

    @FXML
    public void deleteAccount3() throws SQLException, ClassNotFoundException {
        databaseHandler.deleteAccount(accountName3.getText());
        initialize();
    }

    @FXML
    public void deleteAccount4() throws SQLException, ClassNotFoundException {
        databaseHandler.deleteAccount(accountName4.getText());
        initialize();
    }

    @FXML
    public void deleteAccount5() throws SQLException, ClassNotFoundException {
        databaseHandler.deleteAccount(accountName5.getText());
        initialize();
    }

    @FXML
    public void logoutUser() {

        showScreen(logoutButton, "/views/startup.fxml");

    }

    @FXML
    public void promptCreateAccount() throws SQLException {

        int numberOfAccounts = 0;
        ResultSet accountsResult = databaseHandler.getAccountsByUser(user);
        while (accountsResult.next()) {
            numberOfAccounts++;
        }

        if (numberOfAccounts == 5) {

            Shaker shaker = new Shaker(lblMaxAccountsReached);
            shaker.shake();

        } else {

            createAccountButton.setVisible(false);
            createAccountPopUp.setVisible(true);
            populateCurrencyComboBox();

        }

    }

    @FXML
    public void cancelCreateAccount() {

        createAccountButton.setVisible(true);
        createAccountPopUp.setVisible(false);

    }

    @FXML
    public void confirmCreateAccount() throws SQLException {

        if ( ! ValidationUtility.checkIfStringIsMoneyFormat(accountBalanceAdd.getText())) {

            Shaker shaker = new Shaker(accountBalanceAdd);
            shaker.shake();

        } else if (accountCurrencyAdd.getSelectionModel().getSelectedIndex() == -1) {

            Shaker shaker = new Shaker(accountCurrencyAdd);
            shaker.shake();

        } else {

            String currency = accountCurrencyAdd.getSelectionModel().getSelectedItem();
            Account account = new Account(user, Double.parseDouble(accountBalanceAdd.getText()), Currency.valueOf(currency));
            databaseHandler.createAccount(user, account);

            createAccountButton.setVisible(true);
            createAccountPopUp.setVisible(false);

            initialize();

        }

    }

    @FXML
    public void gotoHomePage() {

        showScreen(homeButton, "/views/main.fxml");

    }

    @FXML
    public void gotoPayPage() {

        showScreen(homeButton, "/views/pay.fxml");

    }

    @FXML
    public void gotoProfilePage() {

        showScreen(homeButton, "/views/profile.fxml");

    }

    @FXML
    public void gotoTransferPage() {

        showScreen(homeButton, "/views/transfer.fxml");

    }

    @FXML
    void addBalanceToAccount() throws SQLException, ClassNotFoundException {

        if (ValidationUtility.checkIfStringIsMoneyFormat(addBalanceAmount.getText())) {

            databaseHandler.updateAccountBalance(addBalanceAccountName.getText(),
                    Double.parseDouble(addBalanceAmount.getText()));
            addBalancePane.setVisible(false);
            createAccountButton.setVisible(true);
            initialize();

        } else {

            Shaker shaker = new Shaker(addBalanceAmount);
            shaker.shake();

        }

    }

    @FXML
    void cancelAddBalance() {
        addBalancePane.setVisible(false);
        createAccountButton.setVisible(true);
    }

    @FXML
    void promptAddBalance1() {
        addBalanceAccountName.setText(accountName1.getText());
        addBalanceAccountCurrency.setText(accountCurrency1.getText());
        addBalancePane.setVisible(true);
        createAccountButton.setVisible(false);
        addBalanceAmount.setText("");
    }

    @FXML
    void promptAddBalance2() {
        addBalanceAccountName.setText(accountName2.getText());
        addBalanceAccountCurrency.setText(accountCurrency2.getText());
        addBalancePane.setVisible(true);
        createAccountButton.setVisible(false);
        addBalanceAmount.setText("");
    }

    @FXML
    void promptAddBalance3() {
        addBalanceAccountName.setText(accountName3.getText());
        addBalanceAccountCurrency.setText(accountCurrency3.getText());
        addBalancePane.setVisible(true);
        createAccountButton.setVisible(false);
        addBalanceAmount.setText("");
    }

    @FXML
    void promptAddBalance4() {
        addBalanceAccountName.setText(accountName4.getText());
        addBalanceAccountCurrency.setText(accountCurrency4.getText());
        addBalancePane.setVisible(true);
        createAccountButton.setVisible(false);
        addBalanceAmount.setText("");
    }

    @FXML
    void promptAddBalance5() {
        addBalanceAccountName.setText(accountName5.getText());
        addBalanceAccountCurrency.setText(accountCurrency5.getText());
        addBalancePane.setVisible(true);
        createAccountButton.setVisible(false);
        addBalanceAmount.setText("");
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
        ResultSet userResult = databaseHandler.getUser(loginController.getUser());

        if (userResult.next()) {
            user.setUserId(Integer.parseInt(userResult.getString("userid")));
            user.setUserName(userResult.getString("userName"));
            user.setFirstName(userResult.getString("firstName"));
            user.setLastName(userResult.getString("lastName"));
            user.setEmail(userResult.getString("email"));
            user.setAddress(userResult.getString("address"));
        }

        accountBalanceAdd.setText("");

        int numberOfAccounts = 0;
        SimpleTriplet<String, String, String> triplet = new SimpleTriplet<>();
        SimpleTriplet[] arrayOfTriplets = new SimpleTriplet[6];
        ResultSet accountsResult = databaseHandler.getAccountsByUser(user);
        while (accountsResult.next()) {

            triplet.setFirst(accountsResult.getString("accountName"));
            double formattedBalance = Double.parseDouble(accountsResult.getString("accountBalance"));
            DecimalFormat dec = new DecimalFormat("#0.00");
            triplet.setSecond(dec.format(formattedBalance));
            triplet.setThird(accountsResult.getString("accountCurrency"));
            arrayOfTriplets[numberOfAccounts] = new SimpleTriplet<>(triplet);
            numberOfAccounts++;

        }

        resetAccountLabels();
        if (numberOfAccounts == 5) {

            lblMaxAccountsReached.setVisible(true);

        } else {

            lblMaxAccountsReached.setVisible(false);

        }

        for (int i = 0; i < numberOfAccounts; i++) {
            switch (i) {

                case 0:
                    initAccountLabel(accountPane1, accountName1, accountBalance1, accountCurrency1, arrayOfTriplets[i]);
                    break;
                case 1:
                    initAccountLabel(accountPane2, accountName2, accountBalance2, accountCurrency2, arrayOfTriplets[i]);
                    break;
                case 2:
                    initAccountLabel(accountPane3, accountName3, accountBalance3, accountCurrency3, arrayOfTriplets[i]);
                    break;
                case 3:
                    initAccountLabel(accountPane4, accountName4, accountBalance4, accountCurrency4, arrayOfTriplets[i]);
                    break;
                case 4:
                    initAccountLabel(accountPane5, accountName5, accountBalance5, accountCurrency5, arrayOfTriplets[i]);
                    break;

            }

        }

    }

    private void initAccountLabel(Pane pane, Label nameLabel, Label balanceLabel,
                                  Label currencyLabel, SimpleTriplet data) {

        pane.setVisible(true);
        nameLabel.setText(data.getFirst().toString());
        balanceLabel.setText(data.getSecond().toString());
        currencyLabel.setText(data.getThird().toString());

    }

    private void resetAccountLabels() {

        accountPane1.setVisible(false);
        accountPane2.setVisible(false);
        accountPane3.setVisible(false);
        accountPane4.setVisible(false);
        accountPane5.setVisible(false);

    }

    private void populateCurrencyComboBox() {

        accountCurrencyAdd.setStyle("-fx-font-family: Arial");
        accountCurrencyAdd.setStyle("-fx-font-size: 17");
        accountCurrencyAdd.getItems().clear();
        accountCurrencyAdd.getItems().addAll(
                Currency.values()[0].toString(),
                Currency.values()[1].toString(),
                Currency.values()[2].toString(),
                Currency.values()[3].toString()
        );

    }
}
