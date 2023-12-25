package me.niv.inventoryservice.repository;

import me.niv.inventoryservice.entity.BoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends JpaRepository<BoxEntity, Long> {

    boolean existsByName(String name);
}
