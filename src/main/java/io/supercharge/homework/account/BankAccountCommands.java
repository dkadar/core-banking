package io.supercharge.homework.account;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import io.supercharge.homework.exception.EmptyResultException;
import io.supercharge.homework.transaction.TransactionType;

@ShellComponent
public class BankAccountCommands {
	private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("YYYY.MM.dd HH:mm:ss");
	private final BankAccountService bankAccountService;
	private final BankAccountHistoryService historyService;

	public BankAccountCommands(BankAccountService bankAccountService, BankAccountHistoryService historyService) {
		this.bankAccountService = bankAccountService;
		this.historyService = historyService;
	}

	@ShellMethod("Create bank account for user (you have to give the user id)")
	public String createBankAccount(long userId) {
		Optional<BankAccount> result = bankAccountService.create(userId);
		return result.isPresent() ? result.get().toString() : "Failed to create bank account.";
	}

	@ShellMethod("Get bank account details (you have to give the bank account id)")
	public String getBankAccount(long bankAccountId) {
		BankAccount result = bankAccountService.getBankAccountById(bankAccountId);
		return Objects.toString(result);
	}

	@ShellMethod("Print transaction history (you have to give the bank account id)")
	public String printTransactionHistory(long bankAccountId) {
		List<BankAccountHistory> histories = historyService.findHistoryByBankAccountId(bankAccountId);

		return createHistoryResult(histories);
	}

	@ShellMethod("Filter history by date (you have to give the bank account id and a date)")
	public String filterHistoryByDate(long bankAccountId, LocalDate date) {
		List<BankAccountHistory> histories = historyService.findHistoryByDate(bankAccountId, date);

		return createHistoryResult(histories);
	}

	@ShellMethod("Filter history by transaction type (you have to give the bank account id and a transaction type code,"
			+ " (DEPOSIT or WITHDRAWAL)")
	public String filterHistoryByTransactionType(long bankAccountId, TransactionType type) {
		List<BankAccountHistory> histories = historyService.findHistoryByType(bankAccountId, type);
		return createHistoryResult(histories);
	}

	private String createHistoryResult(List<BankAccountHistory> histories) {
		
		if (histories.isEmpty()) {
			throw new EmptyResultException("There is no transaction history.");
		} 
		return editHistoryString(histories);
	}

	private String editHistoryString(List<BankAccountHistory> histories) {
		return "Date\t\t\t Amount\t Current balance\n" //
				+ histories.stream() //
						.map(m -> m.getTransaction().getDate().format(DATE_TIME_PATTERN) //
								+ "\t " + getNumberSign(m.getTransaction().getType()) + m.getTransaction().getAmount() //
								+ "\t " + m.getCurrentBalance()) //
						.collect(Collectors.joining("\n"));
	}

	private String getNumberSign(TransactionType type) {
		return type == TransactionType.WITHDRAWAL ? "-" : "+";
	}

}
