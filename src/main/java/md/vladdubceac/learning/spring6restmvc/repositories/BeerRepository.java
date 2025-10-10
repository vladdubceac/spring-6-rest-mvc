package md.vladdubceac.learning.spring6restmvc.repositories;

import md.vladdubceac.learning.spring6restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
