package prochat.yj_newsfeedservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_newsfeedservice.model.entity.NewsFeedType;

@Getter
@AllArgsConstructor
public class CreateNewsFeedRequest {
    private String userId;
    private NewsFeedType newsFeedType;
    private String activityUserId;
    private String relatedUserId;
    private Long relatedPosterId;

    public CreateNewsFeedRequest() {
    }
}
