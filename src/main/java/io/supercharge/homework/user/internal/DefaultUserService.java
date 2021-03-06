package io.supercharge.homework.user.internal;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import io.supercharge.homework.user.User;
import io.supercharge.homework.user.UserRepository;
import io.supercharge.homework.user.UserService;

@Service
@Transactional
public class DefaultUserService implements UserService {
	private final UserRepository repository;
	private final UserValidator validator;
	

	public DefaultUserService(UserRepository repository, UserValidator validator) {
		this.repository = repository;
		this.validator = validator;
	}

	@Override
	public Optional<User> create(User user) {
		Optional<User> result = Optional.empty();
		if (validator.isValid(user)) {
			result = Optional.ofNullable(repository.save(user));
		}
		
		return result;
	}

	@Override
	public List<User> getUsers() {
		return repository.findAll();
	}

	@Override
	public Optional<User> getUserById(long userId) {
		return repository.findById(userId);
	}

}
