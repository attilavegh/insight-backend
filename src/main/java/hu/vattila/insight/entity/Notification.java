package hu.vattila.insight.entity;

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
    private String content;
    private Date date;
    private String sender;
    private String receiver;
}
