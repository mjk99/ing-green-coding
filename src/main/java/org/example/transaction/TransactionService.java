package org.example.transaction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionService {
    public List<Account> calculate(List<Transaction> transactions) {
        final Map<String, AccountData> accountDataById = new HashMap<>();

        for (Transaction transaction : transactions) {
            AccountData debitAccount = accountDataById.computeIfAbsent(transaction.debitAccount,
                    acc -> new AccountData());

            AccountData creditAccount = accountDataById.computeIfAbsent(transaction.creditAccount,
                    acc -> new AccountData());


            debitAccount.balance = debitAccount.balance.subtract(transaction.amount);
            creditAccount.balance = creditAccount.balance.add(transaction.amount);

            debitAccount.debitCount++;
            creditAccount.creditCount++;
        }

        return accountDataById
                .entrySet()
                .stream()
                .map(entry -> new Account(
                                entry.getKey(),
                                entry.getValue().debitCount,
                                entry.getValue().creditCount,
                                entry.getValue().balance
                        )
                ).sorted((acc1, acc2)
                        -> String.CASE_INSENSITIVE_ORDER.compare(acc1.account, acc2.account))
                .toList();
    }

    private class AccountData {
        int debitCount = 0;
        int creditCount = 0;
        BigDecimal balance = BigDecimal.ZERO;
    }
}