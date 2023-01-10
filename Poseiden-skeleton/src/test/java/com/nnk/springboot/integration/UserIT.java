package com.nnk.springboot.integration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserIT {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void userTests() {
		User user = new User(15,"user", "~fK9:Lt.", "IT test user", "USER");
		User admin = new User(16,"admin", "~fK8:Lt.", "IT test admin", "ADMIN");

		// Save
		user = userRepository.save(user);
		assertNotNull(user.getId());
		assertEquals("user", user.getUsername());


		admin = userRepository.save(admin);
		assertNotNull(admin.getId());
		assertEquals("admin", admin.getUsername());

		// Update
		String newPassword = "~Fk8:lT.";
		user.setPassword(newPassword);
		user = userRepository.save(user);
		assertEquals(newPassword, user.getPassword());

		// Find
		List<User> listResult = userRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = user.getId();
		userRepository.delete(user);
		Optional<User> userList = userRepository.findById(id);
		assertFalse(userList.isPresent());
	}
}
