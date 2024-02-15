package prochat.yj_newsfeedservice.model.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {
    // user who occur alarm
    private String fromUserId;
    private Long targetId;
}
