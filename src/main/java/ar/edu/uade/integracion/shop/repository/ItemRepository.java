package ar.edu.uade.integracion.shop.repository;

import ar.edu.uade.integracion.shop.entity.Category;
import ar.edu.uade.integracion.shop.entity.Item;
import ar.edu.uade.integracion.shop.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {
    List<Item> findBySeller(User seller, Pageable pageable);

    List<Item> findByNameIsLikeOrDescriptionIsLike(String name, String description, Pageable pageable);

    List<Item> findByCategory(Category category, Pageable pageable);
}
