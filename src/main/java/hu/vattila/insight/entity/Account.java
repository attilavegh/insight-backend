package hu.vattila.insight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String googleId;

    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    private String lastName;

    @Column
    @NotNull
    private String fullName;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private String imageUrl;

    @Column
    @CreationTimestamp
    private Date registrationDate;

    @JsonIgnore
    @OneToMany(mappedBy="sender")
    private Set<Insight> sentInsights;

    @JsonIgnore
    @OneToMany(mappedBy="receiver")
    private Set<Insight> receivedInsights;
}
