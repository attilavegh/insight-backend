package hu.vattila.insight.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Insight implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String content;

    @Column
    @CreatedDate
    private Date date;

    @ManyToOne
    @JoinColumn(name="sender", referencedColumnName = "googleId", nullable=false)
    private Account sender;

    @ManyToOne
    @JoinColumn(name="receiver", referencedColumnName = "googleId", nullable=false)
    private Account receiver;
}
