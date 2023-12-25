package me.niv.inventoryservice.repository;

import java.util.List;
import java.util.Optional;
import me.niv.inventoryservice.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long> {

    boolean existsByType(String type);

    List<TypeEntity> findAll();

    Optional<TypeEntity> findByType(String type);
}
