package io.supercharge.homework.transaction;

public interface TransactionService {
	void deposit(long targetAccountId, long amount);

	void withdrawal(long sourceAccountId, long amount);

	void transfer(long sourceAccountId, long targetAccountId, long amount);
}
