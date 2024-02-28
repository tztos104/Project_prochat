package prochat.yj_activityservice.service;



import com.example.yj_userservice.model.Users;
import com.example.yj_userservice.exception.ErrorCode;
import com.example.yj_userservice.exception.ProchatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import prochat.yj_activityservice.client.UserFeignClient;
import prochat.yj_activityservice.model.alarm.AlarmArgs;
import prochat.yj_activityservice.model.alarm.AlarmNoti;
import prochat.yj_activityservice.model.alarm.AlarmType;
import prochat.yj_activityservice.model.entity.AlarmEntity;
import prochat.yj_activityservice.repository.AlarmRepository;
import prochat.yj_activityservice.repository.EmitterRepository;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final static String ALARM_NAME = "alarm";

    private final AlarmRepository alarmRepository;
    private final EmitterRepository emitterRepository;

    private final UserFeignClient userFeignClient;
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    //알람을 전성하는 메서드
    public void send(AlarmType type, AlarmArgs args, String receiverId, String content) {
        // 알람을 받을 사용자 정보 조회
        Users memberEntity = userFeignClient.findByEmail(receiverId);
        // 알람 엔터티 생성 및 저장
        AlarmEntity entity = AlarmEntity.of(type, args, memberEntity.getEmail());
        // 알람 데이터 생성
        AlarmNoti alarmNoti = new AlarmNoti(type.getAlarmText(),args.getFromUserId(), args.getTargetId(), content);

        alarmRepository.save(entity);

        // 알람을 받을 사용자의 SseEmitter 조회
        emitterRepository.get(receiverId).ifPresentOrElse(it -> {
                    try {
                        it.send(SseEmitter.event()
                                .id(entity.getId().toString())
                                .name(ALARM_NAME)
                                .data(alarmNoti));
                    } catch (IOException exception) {
                        emitterRepository.delete(receiverId);
                        throw new ProchatException(ErrorCode.NOTIFICATION_CONNECT_ERROR);
                    }
                },
                () -> log.info("No emitter founded")
        );
    }

    //사용자의 알람 구독을 위한 SseEmitter를 생성하고 반환하는 메서드.
    public SseEmitter connectNotification(String userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, emitter);
        // SseEmitter 완료 및 타임아웃 이벤트 처리
        emitter.onCompletion(() -> emitterRepository.delete(userId));
        emitter.onTimeout(() -> emitterRepository.delete(userId));

        try {
            log.info("send");
            // 사용자에게 연결 완료 메시지 전송
            emitter.send(SseEmitter.event()
                    .id("id")
                    .name(ALARM_NAME)
                    .data("connect completed"));
        } catch (IOException exception) {
            // 통신 오류 시 예외 처리
            throw new ProchatException(ErrorCode.NOTIFICATION_CONNECT_ERROR);
        }
        return emitter;
    }

}

