package hu.vattila.insight.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    private String continueMessage;

    @Column
    private String considerMessage;

    @Column
    @CreationTimestamp
    private Date date;

    @ManyToOne
    @JoinColumn(name="sender", referencedColumnName = "googleId", nullable=false)
    private Account sender;

    @ManyToOne
    @JoinColumn(name="receiver", referencedColumnName = "googleId", nullable=false)
    private Account receiver;
}
