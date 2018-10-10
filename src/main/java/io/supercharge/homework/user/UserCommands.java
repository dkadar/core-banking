package io.supercharge.homework.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import io.supercharge.homework.exception.EmptyResultException;

@ShellComponent
public class UserCommands {
	private final UserService userService;

	public UserCommands(UserService userService) {
		this.userService = userService;
	}

	@ShellMethod("Create and store the details of a user (first name, last name and birth date)")
	public String createUser(String firstName, String lastName, LocalDate dateOfBirth) {
		User user = setUserDetails(firstName, lastName, dateOfBirth);
		Optional<User> result = userService.create(user);
		return result.isPresent() ? result.get().toString() : "Failed to create User.";
	}

	@ShellMethod("Get all users")
	public List<User> getUsers() {
		List<User> users = userService.getUsers();
		if(users.isEmpty()) {
			throw new EmptyResultException("There is no user yet.");
		}
		return users;

	}

	private User setUserDetails(String firstName, String lastName, LocalDate dateOfBirth) {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setDateOfBirth(dateOfBirth);
		return user;
	}

}
