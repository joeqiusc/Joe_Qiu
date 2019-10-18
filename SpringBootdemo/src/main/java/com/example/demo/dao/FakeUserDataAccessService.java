package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository("fakeDao")
public class FakeUserDataAccessService implements UserDao{
	
	private static List<User> DB = new ArrayList<>();

	@Override
	public int insertUser(User user) {
		UUID id = UUID.randomUUID();
		DB.add(new User(id, user.getName(), 0));
		return 1;
	}

	@Override
	public List<User> selectAllUser() {
		
		return DB;
	}

	@Override
	public Optional<User> selectUserById(UUID id) {
		
		return DB.stream()
				.filter(user -> user.getId().equals(id))
				.findFirst();
	}

	@Override
	public int deletUserById(UUID id) {
		Optional<User> userMaybe = selectUserById(id);
		if (!userMaybe.isPresent()) {
			return 0;
		}
		DB.remove(userMaybe.get());
		return 1;
	}

	@Override
	public int updateUserById(UUID id, User update) {
		
		return selectUserById(id)
				.map(user -> {
					int indexOfUserToUpdate = DB.indexOf(user);
					if (indexOfUserToUpdate >= 0) {
						DB.set(indexOfUserToUpdate, new User(id, update.getName(), indexOfUserToUpdate));
						return 1;
					}
					return indexOfUserToUpdate;
				})
				.orElse(null);
	}

	@Override
	public int nameLen(int id) {
		return DB.stream()
				.filter(user -> user.getId().equals(id))
				.findFirst().getNameLen();
	}
	
	

	
}
