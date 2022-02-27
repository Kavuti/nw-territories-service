package it.kavuti.territory;

import it.kavuti.region.Region;
import it.kavuti.region.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TerritoryConverter {

    @Autowired
    private RegionRepository regionRepository;

    public TerritoryDTO convert(Territory territory) {
        TerritoryDTO dto = new TerritoryDTO();
        dto.setId(territory.getId());
        dto.setDescription(territory.getDescription());
        dto.setRegionId(territory.getRegion() != null ? territory.getRegion().getId() : null);
        return dto;
    }

    public Territory convert(TerritoryDTO dto) {
        Territory territory = new Territory();
        territory.setId(dto.getId());
        territory.setDescription(dto.getDescription());

        Optional<Region> regionOptional = regionRepository.findById(dto.getRegionId());
        if (regionOptional.isPresent()) {
            territory.setRegion(regionOptional.get());
        }

        return territory;
    }
}
