package hu.vattila.insight.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Notification {
    private Integer id;
    @JsonAlias({"continue_message"})
    private String continueMessage;
    @JsonAlias({"consider_message"})
    private String considerMessage;
    private Date date;
    private String sender;
    private String receiver;
}
