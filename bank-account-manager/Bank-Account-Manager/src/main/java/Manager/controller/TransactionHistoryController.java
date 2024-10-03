package manager.controller;

import manager.database.DatabaseHandler;
import manager.model.User;
import manager.utility.CurrencyUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryController extends ShowScreenController {

    @FXML
    private Pane transactionPane1;

    @FXML
    private Label sourceAccount1;

    @FXML
    private Label destinationAccount1;

    @FXML
    private Label transactionDate1;

    @FXML
    private Label transactionType1;

    @FXML
    private Label amount1;

    @FXML
    private Pane transactionPane2;

    @FXML
    private Label sourceAccount2;

    @FXML
    private Label destinationAccount2;

    @FXML
    private Label transactionDate2;

    @FXML
    private Label transactionType2;

    @FXML
    private Label amount2;

    @FXML
    private Pane transactionPane3;

    @FXML
    private Label sourceAccount3;

    @FXML
    private Label destinationAccount3;

    @FXML
    private Label transactionDate3;

    @FXML
    private Label transactionType3;

    @FXML
    private Label amount3;

    @FXML
    private Pane transactionPane4;

    @FXML
    private Label sourceAccount4;

    @FXML
    private Label destinationAccount4;

    @FXML
    private Label transactionDate4;

    @FXML
    private Label transactionType4;

    @FXML
    private Label amount4;

    @FXML
    private Pane transactionPane5;

    @FXML
    private Label sourceAccount5;

    @FXML
    private Label destinationAccount5;

    @FXML
    private Label transactionDate5;

    @FXML
    private Label transactionType5;

    @FXML
    private Label amount5;

    @FXML
    private Label backButton;

    @FXML
    void gotoAccountsScreen() {
        showScreen(backButton, "/views/accounts.fxml");
    }

    @FXML
    void gotoHomeScreen() {
        showScreen(backButton, "/views/main.fxml");
    }

    @FXML
    void gotoPayScreen() {
        showScreen(backButton, "/views/pay.fxml");
    }

    @FXML
    void gotoTransferScreen() {
        showScreen(backButton, "/views/transfer.fxml");
    }

    @FXML
    void gotoProfileScreen() {
        showScreen(backButton, "/views/profile.fxml");
    }

    @FXML
    void initialize() throws SQLException {

        User user = new User();
        DatabaseHandler databaseHandler = new DatabaseHandler();
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
            user.setUserId(Integer.parseInt(currentUser.getString("userid")));
            user.setUserName(currentUser.getString("userName"));
            user.setFirstName(currentUser.getString("firstName"));
            user.setLastName(currentUser.getString("lastName"));
            user.setAddress(currentUser.getString("address"));
            user.setEmail(currentUser.getString("email"));
        }

        ResultSet userAccount = databaseHandler.getAccountsByUser(user);
        List<String> userAccountNames = new ArrayList<>();
        while (userAccount.next()) {
            String accountName = userAccount.getString("accountName");
            userAccountNames.add(accountName);
        }


        transactionPane1.setVisible(false);
        transactionPane2.setVisible(false);
        transactionPane3.setVisible(false);
        transactionPane4.setVisible(false);
        transactionPane5.setVisible(false);

        ResultSet transactions = databaseHandler.getTransactionsDescending();
        int numberOfTransactions = 0;
        while (transactions.next()) {

            if (userAccountNames.contains(transactions.getString("sourceAccount"))) {
                if (numberOfTransactions == 5) {
                    break;
                }
                numberOfTransactions++;


                switch (numberOfTransactions) {

                    case 1:
                        transactionPane1.setVisible(true);
                        sourceAccount1.setText(transactions.getString("sourceAccount"));
                        destinationAccount1.setText(transactions.getString("destinationAccount"));
                        double formattedAmount = Double.parseDouble(transactions.getString("amount"));
                        DecimalFormat dec = new DecimalFormat("#0.00");
                        amount1.setText(dec.format(formattedAmount) + " " +
                                transactions.getString("currency"));
                        transactionDate1.setText(transactions.getString("transactionDate"));
                        transactionType1.setText("Amount transferred");
                        break;
                    case 2:
                        transactionPane2.setVisible(true);
                        sourceAccount2.setText(transactions.getString("sourceAccount"));
                        destinationAccount2.setText(transactions.getString("destinationAccount"));
                        formattedAmount = Double.parseDouble(transactions.getString("amount"));
                        dec = new DecimalFormat("#0.00");
                        amount2.setText(dec.format(formattedAmount) + " " +
                                transactions.getString("currency"));
                        transactionDate2.setText(transactions.getString("transactionDate"));
                        transactionType2.setText("Amount transferred");
                        break;
                    case 3:
                        transactionPane3.setVisible(true);
                        sourceAccount3.setText(transactions.getString("sourceAccount"));
                        destinationAccount3.setText(transactions.getString("destinationAccount"));
                        formattedAmount = Double.parseDouble(transactions.getString("amount"));
                        dec = new DecimalFormat("#0.00");
                        amount3.setText(dec.format(formattedAmount) + " " +
                                transactions.getString("currency"));
                        transactionDate3.setText(transactions.getString("transactionDate"));
                        transactionType3.setText("Amount transferred");
                        break;
                    case 4:
                        transactionPane4.setVisible(true);
                        sourceAccount4.setText(transactions.getString("sourceAccount"));
                        destinationAccount4.setText(transactions.getString("destinationAccount"));
                        formattedAmount = Double.parseDouble(transactions.getString("amount"));
                        dec = new DecimalFormat("#0.00");
                        amount4.setText(dec.format(formattedAmount) + " " +
                                transactions.getString("currency"));
                        transactionDate4.setText(transactions.getString("transactionDate"));
                        transactionType4.setText("Amount transferred");
                        break;
                    case 5:
                        transactionPane5.setVisible(true);
                        sourceAccount5.setText(transactions.getString("sourceAccount"));
                        destinationAccount5.setText(transactions.getString("destinationAccount"));
                        formattedAmount = Double.parseDouble(transactions.getString("amount"));
                        dec = new DecimalFormat("#0.00");
                        amount5.setText(dec.format(formattedAmount) + " " +
                                transactions.getString("currency"));
                        transactionDate5.setText(transactions.getString("transactionDate"));
                        transactionType5.setText("Amount transferred");
                        break;

                }

            } else if (userAccountNames.contains(transactions.getString("destinationAccount"))) {

                if (numberOfTransactions == 5) {
                    break;
                }
                numberOfTransactions++;


                switch (numberOfTransactions) {

                    case 1:
                        transactionPane1.setVisible(true);
                        sourceAccount1.setText(transactions.getString("sourceAccount"));
                        destinationAccount1.setText(transactions.getString("destinationAccount"));
                        String destinationCurrency = "";
                        ResultSet destinationCurrencyResult =
                                databaseHandler.getCurrencyFromAccount(transactions.getString("destinationAccount"));
                        if (destinationCurrencyResult.next()) {
                            destinationCurrency = destinationCurrencyResult.getString("accountCurrency");
                        }
                        double convertedAmount =
                                CurrencyUtility.getBalanceInDestinationCurrency(transactions.getString("sourceAccount"),
                                        transactions.getString("destinationAccount"),
                                        Double.parseDouble(transactions.getString("amount")));
                        DecimalFormat dec = new DecimalFormat("#0.00");
                        amount1.setText(dec.format(convertedAmount) + " " + destinationCurrency);
                        transactionDate1.setText(transactions.getString("transactionDate"));
                        transactionType1.setText("Amount received");
                        break;
                    case 2:
                        transactionPane2.setVisible(true);
                        sourceAccount2.setText(transactions.getString("sourceAccount"));
                        destinationAccount2.setText(transactions.getString("destinationAccount"));
                        destinationCurrency = "";
                        destinationCurrencyResult =
                                databaseHandler.getCurrencyFromAccount(transactions.getString("destinationAccount"));
                        if (destinationCurrencyResult.next()) {
                            destinationCurrency = destinationCurrencyResult.getString("accountCurrency");
                        }
                        convertedAmount =
                                CurrencyUtility.getBalanceInDestinationCurrency(transactions.getString("sourceAccount"),
                                        transactions.getString("destinationAccount"),
                                        Double.parseDouble(transactions.getString("amount")));
                        dec = new DecimalFormat("#0.00");
                        amount2.setText(dec.format(convertedAmount) + " " + destinationCurrency);
                        transactionDate2.setText(transactions.getString("transactionDate"));
                        transactionType2.setText("Amount received");
                        break;
                    case 3:
                        transactionPane3.setVisible(true);
                        sourceAccount3.setText(transactions.getString("sourceAccount"));
                        destinationAccount3.setText(transactions.getString("destinationAccount"));
                        destinationCurrency = "";
                        destinationCurrencyResult =
                                databaseHandler.getCurrencyFromAccount(transactions.getString("destinationAccount"));
                        if (destinationCurrencyResult.next()) {
                            destinationCurrency = destinationCurrencyResult.getString("accountCurrency");
                        }
                        convertedAmount =
                                CurrencyUtility.getBalanceInDestinationCurrency(transactions.getString("sourceAccount"),
                                        transactions.getString("destinationAccount"),
                                        Double.parseDouble(transactions.getString("amount")));
                        dec = new DecimalFormat("#0.00");
                        amount3.setText(dec.format(convertedAmount) + " " + destinationCurrency);
                        transactionDate3.setText(transactions.getString("transactionDate"));
                        transactionType3.setText("Amount received");
                        break;
                    case 4:
                        transactionPane4.setVisible(true);
                        sourceAccount4.setText(transactions.getString("sourceAccount"));
                        destinationAccount4.setText(transactions.getString("destinationAccount"));
                        destinationCurrency = "";
                        destinationCurrencyResult =
                                databaseHandler.getCurrencyFromAccount(transactions.getString("destinationAccount"));
                        if (destinationCurrencyResult.next()) {
                            destinationCurrency = destinationCurrencyResult.getString("accountCurrency");
                        }
                        convertedAmount =
                                CurrencyUtility.getBalanceInDestinationCurrency(transactions.getString("sourceAccount"),
                                        transactions.getString("destinationAccount"),
                                        Double.parseDouble(transactions.getString("amount")));
                        dec = new DecimalFormat("#0.00");
                        amount4.setText(dec.format(convertedAmount) + " " + destinationCurrency);
                        transactionDate4.setText(transactions.getString("transactionDate"));
                        transactionType4.setText("Amount received");
                        break;
                    case 5:
                        transactionPane5.setVisible(true);
                        sourceAccount5.setText(transactions.getString("sourceAccount"));
                        destinationAccount5.setText(transactions.getString("destinationAccount"));
                        destinationCurrency = "";
                        destinationCurrencyResult =
                                databaseHandler.getCurrencyFromAccount(transactions.getString("destinationAccount"));
                        if (destinationCurrencyResult.next()) {
                            destinationCurrency = destinationCurrencyResult.getString("accountCurrency");
                        }
                        convertedAmount =
                                CurrencyUtility.getBalanceInDestinationCurrency(transactions.getString("sourceAccount"),
                                        transactions.getString("destinationAccount"),
                                        Double.parseDouble(transactions.getString("amount")));
                        dec = new DecimalFormat("#0.00");
                        amount5.setText(dec.format(convertedAmount) + " " + destinationCurrency);
                        transactionDate5.setText(transactions.getString("transactionDate"));
                        transactionType5.setText("Amount received");
                        break;

                }
            }

        }

    }
}
