package org.projedata.industry.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.projedata.industry.model.User;
import org.projedata.industry.model.UserLogin;
import org.projedata.industry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public User UserRegister(User email) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String passwordEncoder = encoder.encode(email.getPassword());
		email.setPassword(passwordEncoder);
		
		return repository.save(email);
	}
	
	public Optional<UserLogin> Enter(Optional<UserLogin> email){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<User> user = repository.findByEmail(email.get().getEmail());
		
		if(user.isPresent()) {
			if(encoder.matches(email.get().getPassword(), user.get().getPassword())) {
				
				String auth = email.get().getEmail()+ ":" + email.get().getPassword();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String (encodeAuth);
				
				email.get().setToken(authHeader);
				email.get().setId(user.get().getId()); 
				email.get().setName(user.get().getName());
				email.get().setPhoto(user.get().getPhoto());
				email.get().setPassword(user.get().getPassword());
				
				return email;
				
			}
				
		}
		return null;
	}
}
