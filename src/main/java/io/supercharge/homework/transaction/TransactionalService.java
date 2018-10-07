package io.supercharge.homework.transaction;

public interface TransactionalService {
	boolean deposite(long targetAccountId, long amount);

	boolean withdrawal(long sourceAccountId, long amount);

	boolean transfer(long sourceAccountId, long targetAccountId, long amount);
}
