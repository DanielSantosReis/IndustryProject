package org.projedata.industry.controller;

import java.util.List;

import javax.validation.Valid;

import org.projedata.industry.model.Feedstock;
import org.projedata.industry.repository.FeedstockRepository;
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
@RequestMapping("/feedstock")
@CrossOrigin("*")
public class FeedstockController {
	
	@Autowired
	private FeedstockRepository repository;
	
	public ResponseEntity<List<Feedstock>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Feedstock> getById (@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Feedstock>> getByName(@PathVariable String name) {
		List<Feedstock> list = repository.findAllByNameContainingIgnoreCase(name);
		if (list.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(list);
		}
	}
	
	@PostMapping
	public ResponseEntity<Feedstock> post (@Valid @RequestBody Feedstock feedstock) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(feedstock));
	}
	
	@PutMapping
	public ResponseEntity<Feedstock> put (@Valid @RequestBody Feedstock feedstock) {
		return repository.findById(feedstock.getId()).map(resp -> ResponseEntity.status(HttpStatus.OK)
				.body(repository.save(feedstock))).orElseGet(()->{
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
