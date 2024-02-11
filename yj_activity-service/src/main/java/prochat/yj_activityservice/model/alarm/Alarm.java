package prochat.yj_activityservice.model.alarm;


import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_activityservice.model.entity.AlarmEntity;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Alarm {
    private Long id = null;

    private AlarmType alarmType;

    private AlarmArgs args;

    private Timestamp regDate;

    private Timestamp updateDate;

    private Timestamp removeDate;

    public String getAlarmText() {
        return alarmType.getAlarmText();
    }

    public static Alarm fromEntity(AlarmEntity entity) {
        return new Alarm(
                entity.getId(),
                entity.getAlarmType(),
                entity.getArgs(),
                entity.getRegDate(),
                entity.getUpdateDate(),
                entity.getRemoveDate()
        );
    }
}
