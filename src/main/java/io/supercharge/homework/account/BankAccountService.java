package io.supercharge.homework.account;

import java.util.Optional;

public interface BankAccountService {

	boolean isExistingAccount(long accountId);

	BankAccount getBankAccountById(long accountId);

	boolean hasEnoughMoney(long accountId, long amount);

	void deposite(long accountId, long amount);

	void withdrawal(long accountId, long amount);

	void transfer(long sourceAccountId, long targetAccountId, long amount);

	Optional<BankAccount> create(long userId);

}
