package io.supercharge.homework.account.internal;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import io.supercharge.homework.account.BankAccount;
import io.supercharge.homework.account.BankAccountHistory;
import io.supercharge.homework.account.BankAccountHistoryRepository;
import io.supercharge.homework.account.BankAccountRepository;
import io.supercharge.homework.account.BankAccountService;
import io.supercharge.homework.transaction.TransactionType;
import io.supercharge.homework.user.User;
import io.supercharge.homework.user.UserService;

@Service
@Transactional
public class DefaultBankAccountService implements BankAccountService {
	private final BankAccountRepository repository;
	private final BankAccountHistoryRepository historyRepiository;
	private final UserService userService;

	public DefaultBankAccountService(BankAccountRepository repository, BankAccountHistoryRepository historyRepiository,
			UserService userService) {
		this.repository = repository;
		this.historyRepiository = historyRepiository;
		this.userService = userService;
	}

	@Override
	public Optional<BankAccount> create(long userId) {
		Optional<User> owner = userService.getUserById(userId);
		Optional<BankAccount> result = Optional.empty();
		if (owner.isPresent()) {
			BankAccount account = new BankAccount();
			account.setBalance(0);
			account.setOwner(owner.get());
			result = Optional.ofNullable(repository.save(account));
		}
		return result;
	}

	@Override
	public boolean isExistingAccount(long accountId) {
		return repository.findById(accountId).isPresent();
	}

	@Override
	public BankAccount getBankAccountById(long accountId) {
		return repository.findById(accountId).orElse(null);
	}

	@Override
	public boolean hasEnoughMoney(long accountId, long amount) {
		Optional<BankAccount> currentAccount = repository.findById(accountId);
		return currentAccount.isPresent() ? currentAccount.get().getBalance() >= amount : false;
	}

	@Override
	public void deposite(long accountId, long amount) {
		Optional<BankAccount> account = repository.findById(accountId);
		if (account.isPresent()) {
			BankAccount currentAccount = account.get();
			currentAccount.setBalance(currentAccount.getBalance() + amount);
			repository.save(currentAccount);
			logTransfer(currentAccount, TransactionType.DEPOSIT, amount);

		}

	}

	@Override
	public void withdrawal(long accountId, long amount) {
		Optional<BankAccount> account = repository.findById(accountId);
		if (account.isPresent()) {
			BankAccount currentAccount = account.get();
			currentAccount.setBalance(currentAccount.getBalance() - amount);
			repository.save(currentAccount);
			logTransfer(currentAccount, TransactionType.WITHDRAWAL, amount);
		}

	}

	@Override
	public void transfer(long sourceAccountId, long targetAccountId, long amount) {
		withdrawal(sourceAccountId, amount);
		deposite(targetAccountId, amount);

	}

	private void logTransfer(BankAccount currentAccount, TransactionType type, long amount) {
		BankAccountHistory history = new BankAccountHistory();
		history.setAccount(currentAccount);
		history.setCurrentBalance(currentAccount.getBalance());
		history.setTransaction(type);
		history.setAmount(amount);
		historyRepiository.save(history);

	}

}
