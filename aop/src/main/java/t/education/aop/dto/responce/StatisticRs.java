package t.education.aop.dto.responce;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StatisticRs {
    private Long minDuration;
    private Long middleDuration;
    private Long maxDuration;
    private Long sumDuration;
    private Long countMethodExecutions;
}
