package io.supercharge.homework.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import io.supercharge.homework.user.User;
import lombok.Data;

@Data
@Entity
public class BankAccount {
	@Id
	@GeneratedValue
	private long id;
	@OneToOne
	@JoinColumn(name = "OWNER_ID")
	private User owner;
	private long balance;
}
