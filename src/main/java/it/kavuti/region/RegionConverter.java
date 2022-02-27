package it.kavuti.region;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class RegionConverter {

    public RegionDTO convert(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setDescription(region.getDescription());
        return dto;
    }

    public Region convert(RegionDTO dto) {
        Region region = new Region();
        region.setId(dto.getId());
        region.setDescription(dto.getDescription());
        return region;
    }

    public Page<RegionDTO> convert(Page<Region> page) {
        return page.map(this::convert);
    }
}
