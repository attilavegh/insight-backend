package hu.vattila.insight.entity;

import hu.vattila.insight.dto.splitter.AssignmentDetailDto;
import hu.vattila.insight.dto.splitter.BrowserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Experiment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private BrowserType BrowserType;

    @OneToMany(mappedBy = "experiment", cascade = CascadeType.ALL)
    private List<Bucket> buckets;
}
