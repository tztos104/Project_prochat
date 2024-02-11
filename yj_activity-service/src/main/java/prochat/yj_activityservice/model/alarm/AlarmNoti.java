package prochat.yj_activityservice.model.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmNoti {
    private String type; // 알람의 종류 (댓글, 좋아요, 팔로우 등)
    private String senderName; // 알람을 발생시킨 사용자의 이름
    private Long targetId; //  사용자의 이름
    private String content; // 알람이 발생한 컨텐츠에 대한 설명 또는 제목


}
