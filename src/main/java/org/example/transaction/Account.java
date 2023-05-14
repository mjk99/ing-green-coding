package org.example.transaction;

import java.math.BigDecimal;

public final class Account {
    public final String account;
    public final int debitCount;
    public final int creditCount;
    public final BigDecimal balance;

    public Account(String account, int debitCount, int creditCount, BigDecimal balance) {
        this.account = account;
        this.debitCount = debitCount;
        this.creditCount = creditCount;
        this.balance = balance;
    }
}