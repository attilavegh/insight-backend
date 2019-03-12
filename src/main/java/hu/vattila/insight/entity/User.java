package hu.vattila.insight.entity;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {

    @Id
    @NotNull
    private Integer id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private String imageUrl;

    @Column
    @CreatedDate
    private Date registrationDate;
}
