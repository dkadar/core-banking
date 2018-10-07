package io.supercharge.homework.transaction;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TransactionalCommands {
	private final TransactionalService transactionalService;

	public TransactionalCommands(TransactionalService transactionalService) {
		this.transactionalService = transactionalService;
	}
	
	@ShellMethod("Deposite")
	public void deposite(long accountId, long amount) {
		transactionalService.deposite(accountId, amount);
	}
	
	@ShellMethod("Withdrawal")
	public void withdrawal(long accountId, long amount) {
		transactionalService.withdrawal(accountId, amount);
	}
	
	@ShellMethod("Transfer")
	public void transfer(long source, long target, long amount) {
		transactionalService.transfer(source, target, amount);
	}
	
	

}
