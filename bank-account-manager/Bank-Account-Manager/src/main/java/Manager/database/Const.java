package manager.database;

public class Const {

    static final String USERS_TABLE = "users";
    static final String ACCOUNTS_TABLE = "accounts";
    static final String TRANSACTIONS_TABLE = "transactions";

    // USERS Table Column Names
    static final String USERS_ID = "userid";
    static final String USERS_FIRSTNAME = "firstName";
    static final String USERS_LASTNAME = "lastName";
    static final String USERS_USERNAME = "userName";
    static final String USERS_PASSWORD = "password";
    static final String USERS_EMAIL = "email";
    static final String USERS_ADDRESS = "address";

    // ACCOUNTS Table Column Names
    static final String ACCOUNTS_ID = "accountid";
    static final String ACCOUNTS_NAME = "accountName";
    static final String ACCOUNTS_BALANCE = "accountBalance";
    static final String ACCOUNTS_CURRENCY = "accountCurrency";
    static final String ACCOUNTS_USERID = "accountUserid";

    // TRANSACTIONS Table Column Names
    static final String TRANSACTIONS_ID = "transactionid";
    static final String TRANSACTION_SOURCE_ACCOUNT = "sourceAccount";
    static final String TRANSACTION_DESTINATION_ACCOUNT = "destinationAccount";
    static final String TRANSACTION_AMOUNT = "amount";
    static final String TRANSACTION_CURRENCY = "currency";
    static final String TRANSACTION_DATE = "transactionDate";




}
