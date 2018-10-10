package io.supercharge.homework.account;

import java.time.LocalDate;
import java.util.List;

import io.supercharge.homework.transaction.Transaction;
import io.supercharge.homework.transaction.TransactionType;

public interface BankAccountHistoryService {

	void storeTransactionalData(BankAccount currentAccount, Transaction transaction);

	List<BankAccountHistory> findHistoryByBankAccountId(long bankAccountId);

	List<BankAccountHistory> findHistoryByDate(long bankAccountId, LocalDate date);

	List<BankAccountHistory> findHistoryByType(long bankAccountId, TransactionType type);

}
