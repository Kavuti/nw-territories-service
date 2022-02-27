package it.kavuti.territory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class TerritoryService {

    @Autowired
    private TerritoryRepository repository;

    @Autowired
    private TerritoryConverter converter;

    public Page<TerritoryDTO> search(String query, Pageable pageable) {
        log.debug("Searching Territorys for query {}", query);
        Page<Territory> persistentTerritorys = repository.findByDescriptionContainingIgnoreCase(query, pageable);
        log.debug("Found {} Territorys for query {} and pageable {}", persistentTerritorys.getTotalElements(), query, pageable);
        return persistentTerritorys.map(converter::convert);
    }

    public TerritoryDTO get(Long id) {
        log.debug("Finding Territory with id {}", id);
        Optional<Territory> optionalTerritory = repository.findById(id);
        if (!optionalTerritory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Territory found for the given id");
        }
        return converter.convert(optionalTerritory.get());
    }

    public TerritoryDTO create(TerritoryDTO dto) {
        log.debug("Creating Territory with DTO {}", dto);
        if (dto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating the Territory");
        }

        Territory created = repository.save(converter.convert(dto));
        log.debug("Territory {} created with id {}", dto, created.getId());
        return converter.convert(created);
    }

    public TerritoryDTO update(Long id, TerritoryDTO dto) {
        checkExistence(id);
        log.debug("Updating Territory with id {} with DTO {}", id, dto);
        Territory savedTerritory = repository.save(converter.convert(dto));
        log.debug("Territory with id {} successfully updated", id);
        return converter.convert(savedTerritory);
    }

    private void checkExistence(Long id) {
        log.debug("Checking Territory existence for id {}", id);
        Optional<Territory> optionalTerritory = repository.findById(id);
        if (!optionalTerritory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Territory found for the given id");
        }
    }

    public void delete(Long id) {
        checkExistence(id);
        log.debug("Deleting Territory with id {}", id);
        repository.deleteById(id);
        log.debug("Territory with id {} successfully deleted");
    }

}
