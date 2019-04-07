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
    @JsonAlias({ "sender_id" })
    private Integer senderId;

    @Column
    @NotNull
    @JsonAlias({ "receiver_id" })
    private Integer receiverId;

    @Column
    @NotNull
    @JsonAlias({ "sender_image" })
    private String senderImage;

    @Column
    @NotNull
    @JsonAlias({ "receiver_image" })
    private String receiverImage;

    @Column
    @NotNull
    private String content;

    @Column
    @CreatedDate
    private Date date;
}
