package hu.vattila.insight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InsightDto {
    private String sender;
    private String receiver;
    private String continueMessage;
    private String considerMessage;
}
