package md.vladdubceac.learning.spring6restmvc.repositories;

import md.vladdubceac.learning.spring6restmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
