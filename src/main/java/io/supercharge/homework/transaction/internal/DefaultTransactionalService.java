package io.supercharge.homework.transaction.internal;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import io.supercharge.homework.account.BankAccountService;
import io.supercharge.homework.transaction.Transaction;
import io.supercharge.homework.transaction.TransactionType;
import io.supercharge.homework.transaction.TransactionalRepository;
import io.supercharge.homework.transaction.TransactionalService;

@Service
@Transactional
public class DefaultTransactionalService implements TransactionalService {
	private final TransactionalValidator validator;
	private final TransactionalRepository repository;
	private final BankAccountService bankAccountService;

	public DefaultTransactionalService(TransactionalValidator validator, TransactionalRepository repository,
			BankAccountService bankAccountService) {
		this.validator = validator;
		this.repository = repository;
		this.bankAccountService = bankAccountService;
	}

	@Override
	public boolean deposite(long targetAccountId, long amount) {
		Optional<Transaction> result = Optional.empty();
		if (validator.isValidAccountNumber(targetAccountId) && validator.isValidAmount(amount)) {
			result = Optional.ofNullable(repository.save(createDeposite(targetAccountId, amount)));
			bankAccountService.deposite(targetAccountId, amount);
		}

		return result.isPresent();
	}

	private Transaction createDeposite(long targetAccountId, long amount) {
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTarget(bankAccountService.getBankAccountById(targetAccountId));
		transaction.setDate(LocalDate.now());
		transaction.setType(TransactionType.DEPOSIT);
		return transaction;
	}

	@Override
	public boolean withdrawal(long sourceAccountId, long amount) {
		Optional<Transaction> result = Optional.empty();
		if (validator.isValidAccountNumber(sourceAccountId) && validator.isValidAmount(amount)) {
			result = Optional.ofNullable(repository.save(createWithdrawal(sourceAccountId, amount)));
			bankAccountService.withdrawal(sourceAccountId, amount);
		}

		return result.isPresent();
	}

	private Transaction createWithdrawal(long sourceAccountId, long amount) {
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setSource(bankAccountService.getBankAccountById(sourceAccountId));
		transaction.setDate(LocalDate.now());
		transaction.setType(TransactionType.WITHDRAWAL);
		return transaction;
	}

	@Override
	public boolean transfer(long sourceAccountId, long targetAccountId, long amount) {
		Optional<Transaction> result = Optional.empty();
		if (validator.isValidAccountNumber(targetAccountId) && validator.isValidAccountNumber(sourceAccountId)
				&& validator.isCovered(sourceAccountId, amount)) {
			result = Optional.ofNullable(repository.save(createTransfer(sourceAccountId, targetAccountId, amount)));
			bankAccountService.transfer(sourceAccountId, targetAccountId, amount);
		}

		return result.isPresent();
	}

	private Transaction createTransfer(long sourceAccountId, long targetAccountId, long amount) {
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTarget(bankAccountService.getBankAccountById(targetAccountId));
		transaction.setSource(bankAccountService.getBankAccountById(sourceAccountId));
		transaction.setDate(LocalDate.now());
		transaction.setType(TransactionType.TRANSACTION);
		return transaction;
	}

}
