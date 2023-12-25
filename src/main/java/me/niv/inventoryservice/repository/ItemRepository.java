package me.niv.inventoryservice.repository;

import me.niv.inventoryservice.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    boolean existsByNameAndProperties (String name, String properties);
}
