package io.supercharge.homework.user;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

}
