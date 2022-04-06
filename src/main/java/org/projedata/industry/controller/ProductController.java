package org.projedata.industry.controller;

import java.util.List;

import javax.validation.Valid;

import org.projedata.industry.model.Product;
import org.projedata.industry.repository.ProductRepository;
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
@RequestMapping("/product")
@CrossOrigin("*") 	// This class will accept requests from any source || Esta classe irá aceitar requisição de qualquer origem
public class ProductController {
	
	@Autowired 		// Spring Dependency Injection || Injeção de dependência do Spring 
	private ProductRepository repository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getById (@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Product>> getByName (@PathVariable String name) {
		return ResponseEntity.ok(repository.findAllByNameContainingIgnoreCase(name));
	}
	
	@PostMapping
	public ResponseEntity<Product> post (@Valid @RequestBody Product product) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
	}
	
	@PutMapping
	public ResponseEntity<Product> put (@Valid @RequestBody Product product) {
		return repository.findById(product.getId()).map(resp -> ResponseEntity.status(HttpStatus.OK)
				.body(repository.save(product))).orElseGet(()->{
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id não encontrado!");
				});
	
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable long id) {
		return repository.findById(id).map(resp -> {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();})
				.orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
	}
	
}
