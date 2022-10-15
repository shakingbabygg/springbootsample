package springboot.sample.repositories;

import org.springframework.stereotype.Repository;

import springboot.sample.models.entities.Currency;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String>{

	
}
