package manager.model;

import manager.database.DatabaseHandler;
import manager.enums.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private String name;
    private double balance;
    private Currency currency;
    private User user;
    private int accountIndex;

    public Account(User user, double balance, Currency currency) throws SQLException {

        this.user = user;
        this.name = createAccountName();
        this.balance = balance;
        this.currency = currency;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAccountIndex() {
        return accountIndex;
    }

    public void setAccountIndex(int accountIndex) {
        this.accountIndex = accountIndex;
    }

    private String createAccountName() throws SQLException {

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet accounts = databaseHandler.getAccountsByUser(user);
        List<Integer> lastDigit = new ArrayList<>();

        while (accounts.next()) {
            String name = accounts.getString("accountName");
            int accountNumber = Integer.parseInt(name.substring(name.length() - 1));
            lastDigit.add(accountNumber);

        }

        StringBuilder stringBuilder = new StringBuilder("BAT");

        for (int i = 0; i <= 4; i++) {
            if (!lastDigit.contains(i)) {

                LocalDate localDate = LocalDate.now();

                int dayOfWeekNumber = localDate.getDayOfMonth();
                int currentMonth = localDate.getMonthValue();
                int currentYear = localDate.getYear();

                stringBuilder.append(currentYear);
                stringBuilder.append(currentMonth);
                stringBuilder.append(dayOfWeekNumber);
                stringBuilder.append(user.getUserId());
                stringBuilder.append(i);

                break;

            }
        }

        return stringBuilder.toString();

    }

}
