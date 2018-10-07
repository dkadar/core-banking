package io.supercharge.homework.user.internal;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import io.supercharge.homework.user.User;


@Component
class UserValidator {
	
	boolean isValid(User user) {
        Assert.notNull(user, "User should not be null!");
        Assert.hasText(user.getFirstName(), "User should has first name.");
        Assert.hasText(user.getLastName(), "User should has last name.");
        Assert.notNull(user.getDateOfBirth(), "User should has date of birth.");
        return isDateOfBirthInThePast(user.getDateOfBirth());
    }

	private boolean isDateOfBirthInThePast(LocalDate dateOfBirth) {
		return !LocalDate.now().isBefore(dateOfBirth);
	}

}
