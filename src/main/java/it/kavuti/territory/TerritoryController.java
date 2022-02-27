package it.kavuti.territory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/territory")
public class TerritoryController {

    @Autowired
    public TerritoryService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<TerritoryDTO> search(@RequestParam(name = "query", required = false) String query, Pageable pageable) {
        return service.search(query, pageable);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TerritoryDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TerritoryDTO> create(@Valid @RequestBody TerritoryDTO TerritoryDTO) {
        return ResponseEntity.ok(service.create(TerritoryDTO));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TerritoryDTO> update(@PathVariable("id") Long id, @Valid @RequestBody TerritoryDTO TerritoryDTO) {
        return ResponseEntity.ok(service.update(id, TerritoryDTO));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok(true);
    }
}
