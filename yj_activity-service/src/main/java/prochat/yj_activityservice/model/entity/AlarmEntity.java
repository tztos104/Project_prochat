package prochat.yj_activityservice.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import prochat.yj_activityservice.model.alarm.AlarmArgs;
import prochat.yj_activityservice.model.alarm.AlarmType;

import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "\"alarm\"", indexes = {
        @Index(name = "members_id_idx", columnList = "members_id")
})
@SQLDelete(sql = "UPDATE alarm SET remove_date = NOW() WHERE id=?")
@Where(clause = "remove_date is NULL")
@NoArgsConstructor
public class AlarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;


    private String members;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Type(com.vladmihalcea.hibernate.type.json.JsonType.class)
    @Column(columnDefinition = "json")
    private AlarmArgs args;

    @Column(name = "reg_date")
    private Timestamp regDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "remove_date")
    private Timestamp removeDate;


    @PrePersist
    void regDate() {
        this.regDate = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updateDate() {
        this.updateDate = Timestamp.from(Instant.now());
    }

    public static AlarmEntity of(AlarmType alarmType, AlarmArgs args, String member) {
        AlarmEntity entity = new AlarmEntity();
        entity.setAlarmType(alarmType);
        entity.setArgs(args);
        entity.setMembers(member);
        return entity;
    }
}
