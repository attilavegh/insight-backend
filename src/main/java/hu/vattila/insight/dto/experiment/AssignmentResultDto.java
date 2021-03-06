package hu.vattila.insight.dto.experiment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AssignmentResultDto {
    String experimentName;
    String bucketName;
}
