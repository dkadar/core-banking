package io.supercharge.homework.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
	Optional<User> create(User user);

	List<User> getUsers();

	Optional<User> getUserById(long userId);
}
