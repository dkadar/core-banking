package io.supercharge.homework.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.supercharge.homework.transaction.Transaction;
import lombok.Data;

@Data
@Entity
public class BankAccountHistory {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	@JoinColumn(name = "BANK_ACCOUNT_ID")
	private BankAccount account;
	@ManyToOne
	@JoinColumn(name = "TRANSACTION_ID")
	private Transaction transaction;
	private long currentBalance;

}
