package io.supercharge.homework.transaction;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TransactionCommands {
	private final TransactionService transactionalService;

	public TransactionCommands(TransactionService transactionService) {
		this.transactionalService = transactionService;
	}

	@ShellMethod("Deposit (you have to give the bank account id and the amount)")
	public void deposit(long accountId, long amount) {
		transactionalService.deposit(accountId, amount);
	}

	@ShellMethod("Withdrawal (you have to give the bank account id and the amount)")
	public void withdrawal(long accountId, long amount) {
		transactionalService.withdrawal(accountId, amount);
	}

	@ShellMethod("Transfer (you have to give the source and the target bank account id and the amount)")
	public void transfer(long source, long target, long amount) {
		transactionalService.transfer(source, target, amount);
	}

}
