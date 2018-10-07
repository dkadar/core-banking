package io.supercharge.homework.transaction.internal;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import io.supercharge.homework.account.BankAccountService;

@Component
class TransactionalValidator {
	private final BankAccountService bankAccountService;
	
	public TransactionalValidator(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

	public boolean isValidAmount(long amount) {
		Assert.isTrue(amount > 0, "The amount must be greater than 0.");
		return true;
	}
	
	public boolean isValidAccountNumber(long accountId) {
		Assert.isTrue(accountId > 0, "The account number must be valid.");
		return isExistingAccountId(accountId);
	}

	private boolean isExistingAccountId(long accountId) {
		return bankAccountService.isExistingAccount(accountId);
	}


	public boolean isCovered(long sourceAccountId, long amount) {
		return isValidAmount(amount) && bankAccountService.hasEnoughMoney(sourceAccountId, amount);
	}
}
