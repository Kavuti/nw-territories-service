package it.kavuti.territory;

import it.kavuti.region.Region;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "territory")
@Getter
@Setter
public class Territory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_territory")
    @SequenceGenerator(name = "seq_territory", sequenceName = "seq_territory")
    private Long id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
}
