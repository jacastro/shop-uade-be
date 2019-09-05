package ar.edu.uade.integracion.shop.repository;

import ar.edu.uade.integracion.shop.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
}




