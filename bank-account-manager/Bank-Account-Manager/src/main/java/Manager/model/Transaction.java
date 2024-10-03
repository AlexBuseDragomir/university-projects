package manager.model;

import java.time.LocalDate;

public class Transaction {

    private int transactionId;
    private String sourceAccount;
    private String destinationAccount;
    private double amount;
    private String currency;
    private String transactionDate;

    public Transaction(String sourceAccount, String destinationAccount, double amount, String currency) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.currency = currency;

        LocalDate localDate = LocalDate.now();
        this.transactionDate = localDate.getYear() + "/" + localDate.getMonthValue() +
                "/" + localDate.getDayOfMonth();
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
