package it.kavuti.territory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerritoryRepository extends JpaRepository<Territory, Long> {
    Page<Territory> findByDescriptionContainingIgnoreCase(String query, Pageable pageable);
    List<Territory> findByRegionId(Long id);
}
