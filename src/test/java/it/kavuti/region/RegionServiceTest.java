package it.kavuti.region;

import com.github.javafaker.Faker;
import it.kavuti.territory.TerritoryConverter;
import it.kavuti.territory.TerritoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class RegionServiceTest {

    @Mock
    private RegionRepository repository;

    @Mock
    private TerritoryRepository territoryRepository;

    @Mock
    private TerritoryConverter territoryConverter;

    @Mock
    private RegionConverter converter;

    @InjectMocks
    private RegionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearch_OK() {
        Page<Region> regionsPage = getRegionsPage();
        when(repository.findAll(any(Pageable.class))).thenReturn(regionsPage);
        Mockito.doCallRealMethod().when(converter).convert(any(Region.class));
        Page<RegionDTO> result = service.search(null, Pageable.unpaged());
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(regionsPage.getContent().size());
    }

    @Test
    void testSearchWithQuery_OK() {
        Page<Region> regionsPage = getRegionsPage();
        when(repository.findByDescriptionContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(regionsPage);
        Mockito.doCallRealMethod().when(converter).convert(any(Region.class));
        Page<RegionDTO> result = service.search("test", Pageable.unpaged());
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(regionsPage.getContent().size());
    }

    @Test
    void testGet_OK() {
        Region region = getRegion();
        when(repository.findById(anyLong())).thenReturn(Optional.of(region));
        Mockito.doCallRealMethod().when(converter).convert(any(Region.class));
        assertThatNoException().isThrownBy(() -> service.get(1L));
    }

    @Test
    void testGet_KO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(1L)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void testCreate_OK() {
        Region region = getRegion();
        when(repository.save(any(Region.class))).thenReturn(region);
        Mockito.doCallRealMethod().when(converter).convert(any(Region.class));
        RegionDTO dto = getRegionDTO();
        dto.setId(null);
        assertThatNoException().isThrownBy(() -> service.create(dto));
    }

    @Test
    void testCreate_KO() {
        Region region = getRegion();
        assertThatThrownBy(() -> service.create(getRegionDTO())).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void testUpdate_OK() {
        RegionDTO dto = getRegionDTO();
        Region region = getRegion();
        when(repository.findById(anyLong())).thenReturn(Optional.of(region));
        when(repository.save(any(Region.class))).thenReturn(region);
        Mockito.doCallRealMethod().when(converter).convert(any(RegionDTO.class));
        Mockito.doCallRealMethod().when(converter).convert(any(Region.class));
        assertThatNoException().isThrownBy(() -> service.update(dto.getId(), dto));
    }



    private Region getRegion() {
        Faker faker = new Faker();
        Region region = new Region();
        region.setId(faker.number().randomNumber());
        region.setDescription(faker.leagueOfLegends().champion());
        return region;
    }

    private Page<Region> getRegionsPage() {
        Faker faker = new Faker();
        int max = faker.random().nextInt(10);
        List<Region> regions = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            regions.add(getRegion());
        }
        return new PageImpl<>(regions);
    }

    private RegionDTO getRegionDTO() {
        Faker faker = new Faker();
        RegionDTO region = new RegionDTO();
        region.setId(faker.number().randomNumber());
        region.setDescription(faker.leagueOfLegends().champion());
        return region;
    }
}
