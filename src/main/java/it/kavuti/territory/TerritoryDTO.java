package it.kavuti.territory;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TerritoryDTO {
    private Long id;
    @NotBlank
    private String description;
    @NotNull
    private Long regionId;
}
