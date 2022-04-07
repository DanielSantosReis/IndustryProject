package org.projedata.industry.repository;

import java.util.List;
import java.util.Optional;

import org.projedata.industry.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findAllByNameContainingIgnoreCase (String email);
	public Optional<User> findByEmail (String email);
	
}
