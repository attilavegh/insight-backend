package hu.vattila.insight.dto.splitter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AssignmentDetailDto {
    String googleId;
    BrowserType browserType;
}
