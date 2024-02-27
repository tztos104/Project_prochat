package prochat.yj_newsfeedservice.model.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "\"newsfeed\"")
@SQLDelete(sql = "UPDATE newsfeed SET remove_date = NOW() WHERE id=?")
@Where(clause = "remove_date is NULL")
@NoArgsConstructor
public class NewsFeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String userName;  // 활동 내용
    @Enumerated(EnumType.STRING)
    private NewsFeedType newsFeedType;  // 활동 타입
    private Long activityUserId;
    private String activityUserName;
    private Long relatedUserId;
    private String relatedUserName;
    private Long relatedPosterId;  // 해당 활동이 포함된 글
    private String relatedPosterName;  // 해당 활동이 포함된 글

    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removeDate;

    @PrePersist
    void regDate(){
        this.regDate= Timestamp.from(Instant.now());
    }
    @PreUpdate
    void updateDate(){
        this.updateDate= Timestamp.from(Instant.now());
    }

    public static NewsFeedEntity of(Long userId, NewsFeedType newsFeedType,Long activityUserId,Long relatedUserId,Long relatedPosterId) {
        NewsFeedEntity entity = new NewsFeedEntity();
        entity.setUserId(userId);
        entity.setNewsFeedType(newsFeedType);
        entity.setActivityUserId(activityUserId);
        entity.setRelatedUserId(relatedUserId);
        entity.setRelatedPosterId(relatedPosterId);
        return entity;
    }
}
