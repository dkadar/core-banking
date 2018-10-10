package io.supercharge.homework.account.internal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.supercharge.homework.account.BankAccount;
import io.supercharge.homework.account.BankAccountHistory;
import io.supercharge.homework.account.BankAccountHistoryRepository;
import io.supercharge.homework.account.BankAccountHistoryService;
import io.supercharge.homework.transaction.Transaction;
import io.supercharge.homework.transaction.TransactionType;

@Service
@Transactional
public class DefaultBankAccountHistoryService implements BankAccountHistoryService {
	private final BankAccountHistoryRepository repository;

	public DefaultBankAccountHistoryService(BankAccountHistoryRepository repository) {
		this.repository = repository;
	}

	@Override
	public void storeTransactionalData(BankAccount currentAccount, Transaction transaction) {
		BankAccountHistory history = new BankAccountHistory();
		history.setCurrentBalance(currentAccount.getBalance());
		history.setAccount(currentAccount);
		history.setTransaction(transaction);
		repository.save(history);
	}

	@Override
	public List<BankAccountHistory> findHistoryByBankAccountId(long bankAccountId) {
		return repository.findByAccountIdOrderByTransactionDate(bankAccountId);
	}

	@Override
	public List<BankAccountHistory> findHistoryByDate(long bankAccountId, LocalDate date) {
		return repository.findByAccountIdAndTransactionDate(bankAccountId, date);
	}

	@Override
	public List<BankAccountHistory> findHistoryByType(long bankAccountId, TransactionType type) {
		return repository.findByAccountIdAndTransactionType(bankAccountId, type);
	}

}
