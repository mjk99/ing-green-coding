package org.example.transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Transaction {
    public final String debitAccount;
    public final String creditAccount;
    public final BigDecimal amount;

    public Transaction(String debitAccount, String creditAccount, BigDecimal amount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }
}