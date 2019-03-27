package hu.vattila.insight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
    private Integer senderId;

    @Column
    @NotNull
    private Integer receiverId;

    @Column
    @NotNull
    private String senderImage;

    @Column
    @NotNull
    private String receiverImage;

    @Column
    @NotNull
    private String content;

    @Column
    @CreatedDate
    private Date date;
}
