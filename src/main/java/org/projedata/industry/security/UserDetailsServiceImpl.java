package org.projedata.industry.security;

import java.util.Optional;

import org.projedata.industry.model.User;
import org.projedata.industry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> email = userRepository.findByEmail(userName);
		email.orElseThrow(()-> new UsernameNotFoundException(userName + " not found.  "));
		return email.map(UserDetailsImpl::new).get();
	}
}
