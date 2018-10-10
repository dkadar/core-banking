package io.supercharge.homework.transaction.internal;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import io.supercharge.homework.account.BankAccountService;
import io.supercharge.homework.exception.InvalidTransactionException;
import io.supercharge.homework.transaction.Transaction;
import io.supercharge.homework.transaction.TransactionType;
import io.supercharge.homework.transaction.TransactionRepository;
import io.supercharge.homework.transaction.TransactionService;

@Service
@Transactional
public class DefaultTransactionService implements TransactionService {
	private static final String TRANSACTION_FAILED_MESSAGE = "Transaction failed. Please check account number (id) and the amount. Thank you and have a nice day.";
	private final TransactionValidator validator;
	private final TransactionRepository repository;
	private final BankAccountService bankAccountService;

	public DefaultTransactionService(TransactionValidator validator, TransactionRepository repository,
			BankAccountService bankAccountService) {
		this.validator = validator;
		this.repository = repository;
		this.bankAccountService = bankAccountService;
	}

	@Override
	public void deposit(long targetAccountId, long amount) {
		if (isValidDeposit(targetAccountId, amount)) {
			Transaction transaction = repository
					.save(createTransaction(targetAccountId, amount, TransactionType.DEPOSIT));
			bankAccountService.deposite(transaction);
		} else {
			throw new InvalidTransactionException(TRANSACTION_FAILED_MESSAGE);
		}

	}

	private boolean isValidDeposit(long targetAccountId, long amount) {
		return validator.isValidAccountNumber(targetAccountId) && validator.isValidAmount(amount);
	}

	@Override
	public void withdrawal(long sourceAccountId, long amount) {

		if (isValidWithdrawal(sourceAccountId, amount)) {
			Transaction transaction = repository
					.save(createTransaction(sourceAccountId, amount, TransactionType.WITHDRAWAL));
			bankAccountService.withdrawal(transaction);
		} else {
			throw new InvalidTransactionException(TRANSACTION_FAILED_MESSAGE);
		}

	}

	private boolean isValidWithdrawal(long sourceAccountId, long amount) {
		return validator.isValidAccountNumber(sourceAccountId) && validator.isCovered(sourceAccountId, amount);
	}

	private Transaction createTransaction(long sourceAccountId, long amount, TransactionType type) {
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setAccount(bankAccountService.getBankAccountById(sourceAccountId));
		transaction.setDate(LocalDateTime.now());
		transaction.setType(type);
		return transaction;
	}

	@Override
	public void transfer(long sourceAccountId, long targetAccountId, long amount) {
		if (isValidWithdrawal(sourceAccountId, amount) && isValidDeposit(targetAccountId, amount)) {

			Transaction withdrawal = repository
					.save(createTransaction(sourceAccountId, amount, TransactionType.WITHDRAWAL));
			Transaction deposit = repository.save(createTransaction(targetAccountId, amount, TransactionType.DEPOSIT));
			bankAccountService.transfer(withdrawal, deposit);
		} else {
			throw new InvalidTransactionException(TRANSACTION_FAILED_MESSAGE);
		}

	}

}
