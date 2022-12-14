package ru.andrianov.eshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andrianov.eshop.models.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
