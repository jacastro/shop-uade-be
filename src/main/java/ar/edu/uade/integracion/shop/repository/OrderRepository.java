package ar.edu.uade.integracion.shop.repository;


import ar.edu.uade.integracion.shop.entity.Order;
import ar.edu.uade.integracion.shop.entity.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByBuyer(User buyer);
}
