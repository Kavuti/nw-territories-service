package it.kavuti.region;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegionDTO {
    private Long id;
    @NotBlank
    private String description;
}
