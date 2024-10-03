package manager.database;

import manager.enums.Currency;
import manager.model.Account;
import manager.model.Transaction;
import manager.model.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.lang.Math.abs;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class DatabaseHandlerTest {

    private DatabaseHandler databaseHandler;

    @Before
    public void setUp() {
        this.databaseHandler = new DatabaseHandler();
    }

    @Test
    public void testValidateUser() {
        User user = new User();
        user.setUserName("johndoe");
        ResultSet resultSet = this.databaseHandler.validateUser(user);
        try {
            assertThat(resultSet.next(), is(true));
        } catch (SQLException e) {
            fail();
        }

        try {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");

            assertThat(firstName, is("John"));
            assertThat(lastName, is("Doe"));
            assertThat(email, is("johndoe@example.com"));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testValidateUserNotFound() {
        User user = new User();
        user.setUserName("ion");
        ResultSet resultSet = this.databaseHandler.validateUser(user);
        try {
            assertThat(resultSet.next(), is(false));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setUserName("michaeljohnson");
        user.setPassword("password789");
        ResultSet resultSet = this.databaseHandler.getUser(user);

        try {
            assertThat(resultSet.next(), is(true));
        } catch (SQLException e) {
            fail();
        }

        try {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");

            assertThat(firstName, is("Michael"));
            assertThat(lastName, is("Johnson"));
            assertThat(email, is("michaeljohnson@example.com"));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testGetUserWrongPassword() {
        User user = new User();
        user.setUserName("michaeljohnson");
        user.setPassword("password789!");
        ResultSet resultSet = this.databaseHandler.getUser(user);
        try {
            assertThat(resultSet.next(), is(false));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testRegisterUser() {
        User user = new User("Mike", "Stevens", "mikestevens",
                "password123!@#", "mike.stevens@example.com", "Dummy address details");
        this.databaseHandler.registerUser(user);
        ResultSet resultSet = this.databaseHandler.getUser(user);

        try {
            assertThat(resultSet.next(), is(true));

            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");

            assertThat(firstName, is("Mike"));
            assertThat(lastName, is("Stevens"));
            assertThat(email, is("mike.stevens@example.com"));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testGetUserByUsername() {
        User user = new User();
        user.setUserName("janesmith");
        ResultSet resultSet = this.databaseHandler.validateUser(user);
        try {
            assertThat(resultSet.next(), is(true));
        } catch (SQLException e) {
            fail();
        }

        try {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");

            assertThat(firstName, is("Jane"));
            assertThat(lastName, is("Smith"));
            assertThat(email, is("janesmith@example.com"));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testGetUserByUsernameNotFound() {
        User user = new User();
        user.setUserName("peter");
        ResultSet resultSet = this.databaseHandler.validateUser(user);
        try {
            assertThat(resultSet.next(), is(false));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testEditUser() {
        User user = new User("James", "Taylor", "jamestaylor", "password123!",
            "jamestaylor@example.com", "789 Elm Street");
        try {
            this.databaseHandler.registerUser(user);

            user.setFirstName("Chris");
            user.setLastName("Johnson");
            user.setAddress("101 Maple Avenue, Apt. 202");

            this.databaseHandler.editUser(user);

            ResultSet resultSet = this.databaseHandler.getUser(user);
            assertThat(resultSet.next(), is(true));

            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String address = resultSet.getString("address");

            assertThat(firstName, is("Chris"));
            assertThat(lastName, is("Johnson"));
            assertThat(address, is("101 Maple Avenue, Apt. 202"));

        } catch (SQLException | ClassNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testEditNotFoundUser()
            throws  SQLException, ClassNotFoundException {
        User user = new User();
        user.setUserName("stevens");
        user.setFirstName("Laurentiu");
        user.setLastName("Codrea");
        user.setAddress("Bulevardul Dezrobirii, nr. 15");

        this.databaseHandler.editUser(user);

        ResultSet resultSet = this.databaseHandler.getUser(user);
        assertThat(resultSet.next(), is(false));
    }

    @Test
    public void testEditUserPassword() {
        // Using dummy data instead of real names, emails, and addresses
        User user = new User("DummyFirstName", "DummyLastName", "dummyUsername", "dummyPassword1!",
            "dummy.email@example.com", "Dummy Street, No. 1");

        try {
            // Register the user with dummy data
            this.databaseHandler.registerUser(user);

            // Edit the password with a dummy new password
            this.databaseHandler.editUserPassword("dummyUsername", "dummyPassword2!");

            // Retrieve the updated user
            ResultSet resultSet = this.databaseHandler.getUserByUsername(user);
            assertThat(resultSet.next(), is(true));

            // Check if the password has been updated correctly
            String password = resultSet.getString("password");
            assertThat(password, is("dummyPassword2!"));
        } catch (SQLException | ClassNotFoundException e) {
            fail();
        }
    }

    @Test(expected = SQLException.class)
    public void testEditUserPasswordFail()
            throws  SQLException, ClassNotFoundException {
        User user = new User();
        user.setUserName("lorens");

        this.databaseHandler.editUserPassword("lorens", "lorens@!@!");

        ResultSet resultSet = this.databaseHandler.getUserByUsername(user);
        assertThat(resultSet.next(), is(false));

        String password = resultSet.getString("password");
        assert( ! password.equals("lorens@!@!"));
    }

    @Test
    public void testGetAccountsByUser() {
        User user = new User();
        user.setUserName("johndoe");
        ResultSet resultSet = this.databaseHandler.getUserByUsername(user);

        try {
            assertThat(resultSet.next(), is(true));

            int userId = resultSet.getInt("userid");
            user.setUserId(userId);

            resultSet = this.databaseHandler.getAccountsByUser(user);

            assertThat(resultSet.next(), is(true));

            String accountName1 = resultSet.getString("accountName");
            Double accountBalance1 = resultSet.getDouble("accountBalance");
            String accountCurrency1 = resultSet.getString("accountCurrency");

            assertThat(resultSet.next(), is(true));

            String accountName2 = resultSet.getString("accountName");
            Double accountBalance2 = resultSet.getDouble("accountBalance");
            String accountCurrency2 = resultSet.getString("accountCurrency");

            assertThat(accountName1, is("BAT201952010"));
            assertThat(accountName2, is("BAT201952011"));

            assertThat(accountBalance1, is(3173.58));
            assertThat(accountBalance2, is(1003.25));

            assert(abs(accountBalance1 - 3173.58) <= 0.1);
            assert(abs(accountBalance2 - 1003.25) <= 0.1);

            assertThat(accountCurrency1, is("RON"));
            assertThat(accountCurrency2, is("USD"));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test(expected = SQLException.class)
    public void testGetAccountsByIncorrectUser()
            throws SQLException{
        User user = new User();
        user.setUserName("mary");
        ResultSet resultSet = this.databaseHandler.getUserByUsername(user);

        assertThat(resultSet.next(), is(false));

        int userId = resultSet.getInt("userid");
        user.setUserId(userId);

        resultSet = this.databaseHandler.getAccountsByUser(user);
    }

    @Test
    public void testCreateAccount() {
        User user = new User();
        user.setUserName("johndoe");

        ResultSet resultSet = this.databaseHandler.getUserByUsername(user);

        try {
            resultSet.next();
            user.setUserId(resultSet.getInt("userid"));

            Account account = new Account(user, 1000.50, Currency.USD);

            this.databaseHandler.createAccount(user, account);

            resultSet = this.databaseHandler.getAccountsByUser(user);
            resultSet.next();
            resultSet.next();

            assertThat(resultSet.next(), is(true));

            double accountBalance = resultSet.getDouble("accountBalance");
            String accountCurrency = resultSet.getString("accountCurrency");

            assert(abs(accountBalance - 1000.50) <= 0.1);
            assertThat(accountCurrency, is("USD"));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testDeleteAccount() {
        User user = new User();
        user.setUserName("janesmith");

        ResultSet resultSet = this.databaseHandler.getUserByUsername(user);

        try {
            resultSet.next();
            user.setUserId(resultSet.getInt("userid"));

            Account account = new Account(user, 985.50, Currency.GBP);

            this.databaseHandler.createAccount(user, account);

            resultSet = this.databaseHandler.getAccountsByUser(user);
            resultSet.next();
            resultSet.next();

            assertThat(resultSet.next(), is(true));

            String accountName = resultSet.getString("accountName");
            double accountBalance = resultSet.getDouble("accountBalance");
            String accountCurrency = resultSet.getString("accountCurrency");

            assert(abs(accountBalance - 985.50) <= 0.1);
            assertThat(accountCurrency, is("GBP"));

            this.databaseHandler.deleteAccount(accountName);

            resultSet = this.databaseHandler.getAccountsByUser(user);

            resultSet.next();
            resultSet.next();

            assertThat(resultSet.next(), is(false));
        } catch (SQLException | ClassNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testUpdateAccountBalancePositive() {
        try {
            ResultSet resultSet = this.databaseHandler.getAccountsByName("BAT201952010");
            resultSet.next();
            double oldBalance = resultSet.getDouble("accountBalance");

            this.databaseHandler.updateAccountBalance("BAT201952010", 500.30);

            resultSet = this.databaseHandler.getAccountsByName("BAT201952010");
            resultSet.next();
            double newBalance = resultSet.getDouble("accountBalance");

            assert(abs(newBalance - (oldBalance + 500.30)) < 0.1);
        } catch (SQLException | ClassNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testUpdateAccountBalanceNegative() {
        try {
            ResultSet resultSet = this.databaseHandler.getAccountsByName("BAT201952010");
            resultSet.next();
            double oldBalance = resultSet.getDouble("accountBalance");

            this.databaseHandler.updateAccountBalance("BAT201952010", -500.30);

            resultSet = this.databaseHandler.getAccountsByName("BAT201952010");
            resultSet.next();
            double newBalance = resultSet.getDouble("accountBalance");

            assert(abs(newBalance - (oldBalance - 500.30)) < 0.1);
        } catch (SQLException | ClassNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testGetAccountsByName() {
        ResultSet resultSet = this.databaseHandler.getAccountsByName("BAT201952011");

        try {
            assertThat(resultSet.next(), is(true));

            double accountBalance = resultSet.getDouble("accountBalance");
            String accountCurrency = resultSet.getString("accountCurrency");

            assert(abs(accountBalance - 1003.25) < 0.1);
            assertThat(accountCurrency, is("USD"));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test(expected = SQLException.class)
    public void testGetAccountsByIncorrectName()
            throws SQLException {
        ResultSet resultSet = this.databaseHandler.getAccountsByName("BAT2019520119");

        assertThat(resultSet.next(), is(false));

        double accountBalance = resultSet.getDouble("accountBalance");
        String accountCurrency = resultSet.getString("accountCurrency");

        assert(abs(accountBalance - 1003.25) < 0.1);
        assertThat(accountCurrency, is("USD"));
    }

    @Test
    public void testGetCurrencyFromAccount() {
        ResultSet resultSet = this.databaseHandler.getCurrencyFromAccount("BAT201952021");

        try {
            assertThat(resultSet.next(), is(true));
            String currency = resultSet.getString("accountCurrency");
            assertThat(currency, is("GBP"));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testCreateTransactionBetweenAccounts() {
        String sourceAccount = "BAT201952010";
        String destinationAccount = "BAT201952030";
        String currency = "RON";
        double amount = 500;

        LocalDate localDate = LocalDate.now();
        String transactionDate = localDate.getYear() + "/" + localDate.getMonthValue() +
                "/" + localDate.getDayOfMonth();

        Transaction transaction = new Transaction(sourceAccount,
                destinationAccount, amount, currency);

        this.databaseHandler.createTransaction(transaction);
        ResultSet resultSet = this.databaseHandler.getTransactionsDescending();

        try {
            assertThat(resultSet.next(), is(true));
            String source = resultSet.getString("sourceAccount");
            String destination = resultSet.getString("destinationAccount");
            double sum = resultSet.getDouble("amount");
            String date = resultSet.getString("transactionDate");

            assertThat(source, is(sourceAccount));
            assertThat(destination, is(destinationAccount));
            assertThat(sum, is(amount));
            assertThat(date, is(transactionDate));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testCreateTransactionTowardsService() {
        String sourceAccount = "BAT201952010";
        String destinationAccount = "TWITCH";
        String currency = "RON";
        double amount = 600;

        LocalDate localDate = LocalDate.now();
        String transactionDate = localDate.getYear() + "/" + localDate.getMonthValue() +
                "/" + localDate.getDayOfMonth();

        Transaction transaction = new Transaction(sourceAccount,
                destinationAccount, amount, currency);

        this.databaseHandler.createTransaction(transaction);
        ResultSet resultSet = this.databaseHandler.getTransactionsDescending();

        try {
            assertThat(resultSet.next(), is(true));
            String source = resultSet.getString("sourceAccount");
            String destination = resultSet.getString("destinationAccount");
            double sum = resultSet.getDouble("amount");
            String date = resultSet.getString("transactionDate");

            assertThat(source, is(sourceAccount));
            assertThat(destination, is(destinationAccount));
            assertThat(sum, is(amount));
            assertThat(date, is(transactionDate));
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void testGetTransactionsDescending() {
        ResultSet resultSet = this.databaseHandler.getTransactionsDescending();

        String sourceAccount = "BAT20195710";
        String destinationAccount = "BAT201952020";
        String transactionDate = "2019/5/7";
        double amount = 540;

        String source = null;
        String destination = null;
        String date = null;
        double sum = 0;

        try {
            while(resultSet.next()) {
                source = resultSet.getString("sourceAccount");
                destination = resultSet.getString("destinationAccount");
                sum = resultSet.getDouble("amount");
                date = resultSet.getString("transactionDate");
            }

            assertThat(source, is(sourceAccount));
            assertThat(destination, is(destinationAccount));
            assertThat(sum, is(amount));
            assertThat(date, is(transactionDate));
        } catch (SQLException e) {
            fail();
        }
    }
}
