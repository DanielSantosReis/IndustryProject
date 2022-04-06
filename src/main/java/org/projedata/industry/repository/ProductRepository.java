package org.projedata.industry.repository;

import java.util.List;

import org.projedata.industry.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	public List<Product> findAllByNameContainingIgnoreCase (String name);

}
