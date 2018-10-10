package io.supercharge.homework.account;

import java.util.Optional;

import io.supercharge.homework.transaction.Transaction;

public interface BankAccountService {

	boolean isExistingAccount(long accountId);

	BankAccount getBankAccountById(long accountId);

	boolean hasEnoughMoney(long accountId, long amount);

	Optional<BankAccount> create(long userId);

	void deposite(Transaction transaction);

	void withdrawal(Transaction transaction);

	void transfer(Transaction withdrawalTransaction, Transaction depositeTransaction);

}
