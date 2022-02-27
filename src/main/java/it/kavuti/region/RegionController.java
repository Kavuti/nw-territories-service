package it.kavuti.region;

import it.kavuti.territory.TerritoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/region")
public class RegionController {

    @Autowired
    public RegionService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<RegionDTO> search(@RequestParam(name = "query", required = false) String query, Pageable pageable) {
        return service.search(query, pageable);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionDTO> create(@Valid @RequestBody RegionDTO regionDTO) {
        return ResponseEntity.ok(service.create(regionDTO));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionDTO> update(@PathVariable("id") Long id, @Valid @RequestBody RegionDTO regionDTO) {
        return ResponseEntity.ok(service.update(id, regionDTO));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping(path = "/{id}/territories")
    public ResponseEntity<List<TerritoryDTO>> territories(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getTerritories(id));
    }
}
