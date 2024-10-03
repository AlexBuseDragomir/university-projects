package manager.utility;

import manager.database.DatabaseHandler;
import manager.enums.ExchangeRate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyUtility {

    public static double getBalanceInDestinationCurrency(String sourceAccount,
            String destinationAccount, double amount) throws SQLException {

        DatabaseHandler databaseHandler = new DatabaseHandler();
        String sourceAccountCurrency = "";
        String destinationAccountCurrency = "";
        ResultSet sourceAccountResult = databaseHandler.getAccountsByName(sourceAccount);
        ResultSet destinationAccountResult = databaseHandler.getAccountsByName(destinationAccount);
        if (sourceAccountResult.next()) {
            sourceAccountCurrency = sourceAccountResult.getString("accountCurrency");
        }
        if (destinationAccountResult.next()) {
            destinationAccountCurrency = destinationAccountResult.getString("accountCurrency");
        }

        String exchangeRateString = sourceAccountCurrency + "_" + destinationAccountCurrency;
        ExchangeRate exchangeRate = ExchangeRate.valueOf(exchangeRateString);
        return amount * exchangeRate.getRate();

    }

}
