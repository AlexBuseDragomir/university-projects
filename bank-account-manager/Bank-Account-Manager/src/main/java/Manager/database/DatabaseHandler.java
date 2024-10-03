package manager.database;

import manager.model.Account;
import manager.model.Transaction;
import manager.model.User;

import java.sql.*;

public class DatabaseHandler extends Configs {

    private Connection dbConnection;



    public ResultSet validateUser(User user) {

        ResultSet resultSet = null;

        String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "
                + Const.USERS_USERNAME + "=?";

        try {

            PreparedStatement preparedStatement;
            preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUserName());

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;

    }

    public ResultSet getUser(User user) {

        ResultSet resultSet = null;

        if (!user.getUserName().equals("") || !user.getPassword().equals("")) {

            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "
                    + Const.USERS_USERNAME + "=?" + " AND " + Const.USERS_PASSWORD
                    + "=?";

            try {

                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultSet;
    }

    public void registerUser(User user) {

        String insert = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME
                + ", " + Const.USERS_LASTNAME
                + ", " + Const.USERS_USERNAME
                + ", " + Const.USERS_EMAIL
                + ", " + Const.USERS_PASSWORD
                + ", " + Const.USERS_ADDRESS + ")" + " VALUES(?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getAddress());


            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet getUserByUsername(User user) {

        ResultSet resultSet = null;

        if (! user.getUserName().equals("") || ! user.getPassword().equals("")) {

            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "
                    + Const.USERS_USERNAME + "=?";

            try {

                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUserName());

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return resultSet;
    }

    public void editUser(User user) throws SQLException, ClassNotFoundException {

        String query = "UPDATE " + Const.USERS_TABLE + " SET "
                + Const.USERS_FIRSTNAME + " = ?"
                + ", " + Const.USERS_LASTNAME + " = ?"
                + ", " + Const.USERS_ADDRESS + " = ?"
                + " WHERE (" + Const.USERS_USERNAME + " = ?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getAddress());
        preparedStatement.setString(4, user.getUserName());

        preparedStatement.executeUpdate();

    }

    public void editUserPassword(String userName, String password) throws SQLException, ClassNotFoundException {

        String query = "UPDATE " + Const.USERS_TABLE + " SET "
                + Const.USERS_PASSWORD + " = ?"
                + " WHERE (" + Const.USERS_USERNAME + " = ?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1, password);
        preparedStatement.setString(2, userName);

        preparedStatement.executeUpdate();

    }

    public ResultSet getAccountsByUser(User user) {

        ResultSet resultSet = null;

        if (!user.getUserName().equals("") || !user.getPassword().equals("")) {

            String query = "SELECT * FROM " + Const.ACCOUNTS_TABLE + " WHERE "
                    + Const.ACCOUNTS_USERID + "=?";

            try {

                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, Integer.toString(user.getUserId()));

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return resultSet;

    }

    public void createAccount(User user, Account account) {

        String insert = "INSERT INTO " + Const.ACCOUNTS_TABLE
                + "(" + Const.ACCOUNTS_NAME
                + ", " + Const.ACCOUNTS_BALANCE
                + ", " + Const.ACCOUNTS_CURRENCY
                + ", " + Const.ACCOUNTS_USERID
                + ")" + " VALUES(?, ?, ?, ?)";

        try {

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, Double.toString(account.getBalance()));
            preparedStatement.setString(3, account.getCurrency().toString());
            preparedStatement.setString(4, Integer.toString(user.getUserId()));

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteAccount(String accountName) throws SQLException, ClassNotFoundException {

        String query = "DELETE FROM " + Const.ACCOUNTS_TABLE
                + " WHERE (" + Const.ACCOUNTS_NAME + " = ?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1, accountName);

        preparedStatement.executeUpdate();

    }

    public void updateAccountBalance(String accountName, Double balance) throws SQLException, ClassNotFoundException {

        double oldBalance = 0;
        ResultSet account = getAccountsByName(accountName);
        if (account.next()) {
            oldBalance = Double.parseDouble(account.getString("accountBalance"));
        }
        double finalBalance = oldBalance + balance;

        String query = "UPDATE " + Const.ACCOUNTS_TABLE + " SET "
                + Const.ACCOUNTS_BALANCE + " = ?"
                + " WHERE (" + Const.ACCOUNTS_NAME + " = ?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1, Double.toString(finalBalance));
        preparedStatement.setString(2, accountName);

        preparedStatement.executeUpdate();

    }

    public ResultSet getAccountsByName(String name) {

        ResultSet resultSet = null;

        String query = "SELECT * FROM " + Const.ACCOUNTS_TABLE + " WHERE "
                + Const.ACCOUNTS_NAME + "=?";

        try {

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;

    }

    public void createTransaction(Transaction transaction) {

        String insert = "INSERT INTO " + Const.TRANSACTIONS_TABLE
                + "(" + Const.TRANSACTION_SOURCE_ACCOUNT
                + ", " + Const.TRANSACTION_DESTINATION_ACCOUNT
                + ", " + Const.TRANSACTION_AMOUNT
                + ", " + Const.TRANSACTION_CURRENCY
                + ", " + Const.TRANSACTION_DATE
                + ")" + " VALUES(?, ?, ?, ?, ?)";

        try {

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, transaction.getSourceAccount());
            preparedStatement.setString(2, transaction.getDestinationAccount());
            preparedStatement.setString(3, Double.toString(transaction.getAmount()));
            preparedStatement.setString(4, transaction.getCurrency());
            preparedStatement.setString(5, transaction.getTransactionDate());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet getCurrencyFromAccount(String accountName) {

        ResultSet resultSet = null;

        String query = "SELECT " + Const.ACCOUNTS_CURRENCY+ " FROM " + Const.ACCOUNTS_TABLE + " WHERE "
                + Const.ACCOUNTS_NAME + "=?";

        try {

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, accountName);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;

    }

    public ResultSet getTransactionsDescending() {

        ResultSet resultSet = null;

        String query = "SELECT * FROM " + Const.TRANSACTIONS_TABLE + " ORDER BY "
                + Const.TRANSACTIONS_ID + " DESC";

        try {

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;

    }

    private Connection getDbConnection() throws SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
                + "?autoReconnect=true&useSSL=false";
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

}
