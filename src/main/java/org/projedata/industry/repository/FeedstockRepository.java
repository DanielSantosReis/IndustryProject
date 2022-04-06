package org.projedata.industry.repository;

import java.util.List;

import org.projedata.industry.model.Feedstock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedstockRepository extends JpaRepository<Feedstock, Long> {
	
	public List<Feedstock> findAllByNameContainingIgnoreCase (String name);

}
