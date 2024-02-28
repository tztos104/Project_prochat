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
    private String userEmail;
    private String userName;  // 활동 내용
    @Enumerated(EnumType.STRING)
    private NewsFeedType newsFeedType;  // 활동 타입
    private String activityUserEmail;
    private String activityUserName;
    private String relatedUserEmail;
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

    public static NewsFeedEntity of(String userEmail, NewsFeedType newsFeedType,String activityUserId,String relatedUserId,Long relatedPosterId) {
        NewsFeedEntity entity = new NewsFeedEntity();
        entity.setUserEmail(userEmail);
        entity.setNewsFeedType(newsFeedType);
        entity.setActivityUserEmail(activityUserId);
        entity.setRelatedUserEmail(relatedUserId);
        entity.setRelatedPosterId(relatedPosterId);
        return entity;
    }
}
