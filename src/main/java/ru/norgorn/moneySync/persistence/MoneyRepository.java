package ru.norgorn.moneySync.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.norgorn.moneySync.persistence.entity.Money;

@Repository
public interface MoneyRepository extends JpaRepository<Money, String>{

	@Query("SELECT m FROM Money m WHERE m.country=:#{#country} ORDER BY m.money DESC")
	List<Money> getTopPlayersByCountry(@Param("country") String country, Pageable pageable);
	
	@Query(value = "SELECT msub.uid, msub.country, msub.money " + 
			" FROM (" + 
			"  SELECT" + 
			"    ROW_NUMBER() OVER (PARTITION BY country ORDER BY money DESC) AS r," + 
			"    m.*" + 
			"  FROM" + 
			"    money m) msub" + 
			" WHERE" + 
			"  msub.r <= ?1 " + 
			";",nativeQuery=true)
	List<Money> getTopPlayersAllCountries(@Param("top") int top);
}
