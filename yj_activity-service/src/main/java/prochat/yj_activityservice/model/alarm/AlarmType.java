package prochat.yj_activityservice.model.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    NEW_COMMENT_ON_POST("new comment!"),
    NEW_LIKE_ON_POST("new like!"),
    NEW_LIKE_ON_COMMENT("new like!"),
    NEW_FOLLOW("new follow!"),
    ;

    private final String alarmText;
}
