package org.example.transaction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class TransactionService {

    /**
     * This method creates hashmap of all accounts referenced in transaction list,
     * for every transaction it updates account balance and debit/credit counts.
     * Finally, returns list of accounts from hashmap and sorts it by account id.
     *
     * Time complexity O(n*log(n))
     *
     * @param transactions {@link List<Transaction>}
     * @return accounts sorted by their ids {@link List<Account>}
     */
    public List<Account> calculate(List<Transaction> transactions) {
        final Map<String, Account> accountsById = new HashMap<>();

        for (Transaction transaction : transactions) {
            Account debitAccount = accountsById.computeIfAbsent(transaction.debitAccount,
                    acc -> new Account(transaction.debitAccount, 0, 0, BigDecimal.ZERO));

            Account creditAccount = accountsById.computeIfAbsent(transaction.creditAccount,
                            acc -> new Account(transaction.creditAccount, 0, 0, BigDecimal.ZERO));


            debitAccount.balance = debitAccount.balance.subtract(transaction.amount);
            creditAccount.balance = creditAccount.balance.add(transaction.amount);

            debitAccount.debitCount++;
            creditAccount.creditCount++;
        }

        return accountsById
                .values()
                .stream()
                .sorted((acc1, acc2)
                        -> String.CASE_INSENSITIVE_ORDER.compare(acc1.account, acc2.account))
                .toList();
    }
}