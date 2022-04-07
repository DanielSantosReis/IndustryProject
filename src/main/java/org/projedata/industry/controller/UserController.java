package org.projedata.industry.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.projedata.industry.model.User;
import org.projedata.industry.model.UserLogin;
import org.projedata.industry.repository.UserRepository;
import org.projedata.industry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository repository;
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById (@PathVariable long id){
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<User>> getByNome(@PathVariable String name){
		return ResponseEntity.ok(repository.findAllByNameContainingIgnoreCase(name));
	}
	
	@PostMapping("/enter")
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user){
		return userService.Enter(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> Post(@RequestBody User user){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(userService.UserRegister(user));
	}
	
	@PutMapping
	public ResponseEntity<User> put (@Valid @RequestBody User user) {
		return repository.findById(user.getId()).map(resp -> ResponseEntity.status(HttpStatus.OK)
				.body(repository.save(user))).orElseGet(()->{
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado!!!");
				});
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable long id) {
		return repository.findById(id).map(response -> {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();})
				.orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
	}
}