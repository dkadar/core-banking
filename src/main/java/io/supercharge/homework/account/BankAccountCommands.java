package io.supercharge.homework.account;

import java.util.Objects;
import java.util.Optional;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
public class BankAccountCommands {
	private final BankAccountService bankAccountService;

	public BankAccountCommands(BankAccountService userService) {
		this.bankAccountService = userService;
	}

	@ShellMethod("Create bank account for user")
	public String createBankAccount(long userId) {
		Optional<BankAccount> result = bankAccountService.create(userId);
		return result.isPresent() ? result.get().toString() : "Failed to create bank account.";
	}
	
	@ShellMethod("Get banka ccount details")
	public String getBankAccount(long bankAccountId) {
		BankAccount result = bankAccountService.getBankAccountById(bankAccountId);
		return Objects.toString(result);
	}

}
