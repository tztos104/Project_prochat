package prochat.yj_activityservice.controller.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_activityservice.model.alarm.Alarm;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class AlarmResponse {
    private Long id;
    private String text;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removeDate;

    public static AlarmResponse fromAlarm(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getAlarmText(),
                alarm.getRegDate(),
                alarm.getUpdateDate(),
                alarm.getRemoveDate()
        );
    }
}
