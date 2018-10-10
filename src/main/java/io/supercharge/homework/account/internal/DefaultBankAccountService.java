package io.supercharge.homework.account.internal;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.supercharge.homework.account.BankAccount;
import io.supercharge.homework.account.BankAccountHistoryService;
import io.supercharge.homework.account.BankAccountRepository;
import io.supercharge.homework.account.BankAccountService;
import io.supercharge.homework.transaction.Transaction;
import io.supercharge.homework.user.User;
import io.supercharge.homework.user.UserService;

@Service
@Transactional
public class DefaultBankAccountService implements BankAccountService {
	private final BankAccountRepository repository;
	private final BankAccountHistoryService historyService;
	private final UserService userService;

	public DefaultBankAccountService(BankAccountRepository repository, BankAccountHistoryService historyService,
			UserService userService) {
		this.repository = repository;
		this.historyService = historyService;
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
			result = Optional.of(repository.save(account));
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
		return currentAccount.isPresent() && currentAccount.get().getBalance() >= amount;
	}

	@Override
	public void deposite(Transaction transaction) {
		Optional<BankAccount> account = repository.findById(transaction.getAccount().getId());
		if (account.isPresent()) {
			BankAccount currentAccount = account.get();
			currentAccount.setBalance(currentAccount.getBalance() + transaction.getAmount());
			repository.save(currentAccount);
			historyService.storeTransactionalData(currentAccount, transaction);
		}

	}

	@Override
	public void withdrawal(Transaction transaction) {
		Optional<BankAccount> account = repository.findById(transaction.getAccount().getId());
		if (account.isPresent()) {
			BankAccount currentAccount = account.get();
			currentAccount.setBalance(currentAccount.getBalance() - transaction.getAmount());
			repository.save(currentAccount);
			historyService.storeTransactionalData(currentAccount, transaction);
		}

	}

	@Override
	public void transfer(Transaction withdrawalTransaction, Transaction depositeTransaction) {
		withdrawal(withdrawalTransaction);
		deposite(depositeTransaction);

	}

}
