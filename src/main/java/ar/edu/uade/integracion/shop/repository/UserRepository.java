package ar.edu.uade.integracion.shop.repository;


import ar.edu.uade.integracion.shop.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository <User, Integer> {
}
