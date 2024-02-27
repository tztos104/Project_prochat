package prochat.yj_newsfeedservice.model;



import lombok.*;
import prochat.yj_newsfeedservice.model.entity.NewsFeedEntity;


@Getter
@AllArgsConstructor
public class NewsFeed {


    private Long userId;
    private String userName;  // 활동 내용

    private Long activityUserId;
    private String activityUserName;
    private Long relatedUserId;
    private String relatedUserName;
    private Long relatedPosterId;  // 해당 활동이 포함된 글
    private String relatedPosterName;

    public static NewsFeed fromEntity(NewsFeedEntity entity) {
        return new NewsFeed(
                entity.getUserId(),
                entity.getUserName(),

                entity.getActivityUserId(),
                entity.getActivityUserName(),
                entity.getRelatedUserId(),
                entity.getRelatedUserName(),
                entity.getRelatedPosterId(),
                entity.getRelatedPosterName()


        );
    }
}
