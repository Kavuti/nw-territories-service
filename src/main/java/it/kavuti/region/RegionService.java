package it.kavuti.region;

import it.kavuti.territory.Territory;
import it.kavuti.territory.TerritoryConverter;
import it.kavuti.territory.TerritoryDTO;
import it.kavuti.territory.TerritoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegionService {

    @Autowired
    private RegionRepository repository;

    @Autowired
    private TerritoryRepository territoryRepository;

    @Autowired
    private TerritoryConverter territoryConverter;

    @Autowired
    private RegionConverter converter;

    public Page<RegionDTO> search(String query, Pageable pageable) {
        log.debug("Searching Regions for query {}", query);
        Page<Region> persistentRegions = repository.findByDescriptionContainingIgnoreCase(query, pageable);
        log.debug("Found {} regions for query {} and pageable {}", persistentRegions.getTotalElements(), query, pageable);
        return persistentRegions.map(converter::convert);
    }

    public RegionDTO get(Long id) {
        Optional<Region> optionalRegion = repository.findById(id);
        if (!optionalRegion.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Region found for the given id");
        }
        return converter.convert(optionalRegion.get());
    }

    public RegionDTO create(RegionDTO dto) {
        log.debug("Creating Region with DTO {}", dto);
        if (dto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating the region");
        }

        Region created = repository.save(new Region(null, dto.getDescription()));
        log.debug("Region {} created with id {}", dto, created.getId());
        return converter.convert(created);
    }

    public RegionDTO update(Long id, RegionDTO dto) {
        checkExistence(id);
        log.debug("Updating region with id {} with DTO {}", id, dto);
        Region savedRegion = repository.save(converter.convert(dto));
        log.debug("Region with id {} successfully updated", id);
        return converter.convert(savedRegion);
    }

    private void checkExistence(Long id) {
        log.debug("Checking Region existence for id {}", id);
        Optional<Region> optionalRegion = repository.findById(id);
        if (!optionalRegion.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Region found for the given id");
        }
    }

    public void delete(Long id) {
        checkExistence(id);
        log.debug("Deleting region with id {}", id);
        repository.deleteById(id);
        log.debug("Region with id {} successfully deleted");
    }

    public List<TerritoryDTO> getTerritories(Long id) {
        checkExistence(id);
        log.debug("Retrieving territories related to region with id {}", id);
        List<Territory> byRegionId = territoryRepository.findByRegionId(id);
        return byRegionId.stream().map(territoryConverter::convert).collect(Collectors.toList());
    }

}
