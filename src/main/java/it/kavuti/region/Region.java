package it.kavuti.region;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "region")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_region")
    @SequenceGenerator(name = "seq_region", sequenceName = "seq_region")
    private Long id;
    private String description;
}
