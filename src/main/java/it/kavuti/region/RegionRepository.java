package it.kavuti.region;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Page<Region> findByDescriptionContainingIgnoreCase(String query, Pageable pageable);
}
