package org.example.transaction;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    private final TransactionService service = new TransactionService();

    @Test
    public void providedExample() {
        //given
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("32309111922661937852684864",
                "06105023389842834748547303", new BigDecimal(10.90)));
        transactions.add(new Transaction("31074318698137062235845814",
                "66105036543749403346524547", new BigDecimal(200.90)));
        transactions.add(new Transaction("66105036543749403346524547",
                "32309111922661937852684864", new BigDecimal(50.10)));

        List<Account> accounts = new ArrayList<>();

        accounts.add(new Account("06105023389842834748547303", 0, 1, new BigDecimal(10.90).setScale(2, RoundingMode.HALF_UP)));
        accounts.add(new Account("31074318698137062235845814", 1, 0, new BigDecimal(-200.90).setScale(2, RoundingMode.HALF_UP)));
        accounts.add(new Account("32309111922661937852684864", 1, 1, new BigDecimal(39.20).setScale(2, RoundingMode.HALF_UP)));
        accounts.add(new Account("66105036543749403346524547", 1, 1, new BigDecimal(150.80).setScale(2, RoundingMode.HALF_UP)));

        //when
        List<Account> output = service.calculate(transactions);

        //then
        for (int i = 0; i < accounts.size(); i++) {
            assertEquals(accounts.get(i).account, output.get(i).account);
            assertEquals(accounts.get(i).debitCount, output.get(i).debitCount);
            assertEquals(accounts.get(i).creditCount, output.get(i).creditCount);
            assertEquals(accounts.get(i).balance, output.get(i).balance);
        }

    }
}