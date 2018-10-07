package io.supercharge.homework.transaction;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.supercharge.homework.account.BankAccount;
import lombok.Data;

@Data
@Entity
public class Transaction {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	@JoinColumn(name = "SOURCE_BANK_ACCOUNT_ID")
	private BankAccount source;
	@ManyToOne
	@JoinColumn(name = "TARGET_BANK_ACCOUNT_ID")
	private BankAccount target;
	private LocalDate date;
	private long amount;
	@Enumerated(EnumType.STRING)
	private TransactionType type;
}
