package prochat.yj_newsfeedservice.service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prochat.yj_newsfeedservice.client.ActivityFeignClient;
import prochat.yj_newsfeedservice.client.UserFeignClient;
import prochat.yj_newsfeedservice.controller.response.Response;
import prochat.yj_newsfeedservice.model.NewsFeed;
import prochat.yj_newsfeedservice.model.Post;
import prochat.yj_newsfeedservice.model.entity.NewsFeedEntity;
import prochat.yj_newsfeedservice.model.entity.NewsFeedType;
import prochat.yj_newsfeedservice.model.entity.Users;
import prochat.yj_newsfeedservice.request.UserRequest;
import prochat.yj_newsfeedservice.response.PostResponse;
import prochat.yj_newsfeedservice.response.UserResponse;

@Component
@RequiredArgsConstructor
public class NewsFeedMapper {

    private final UserFeignClient userFeignClient;
    private final ActivityFeignClient activityFeignClient;
    public NewsFeed toGetMyNewsFeedDto(NewsFeedEntity entity) {
        Long userId = entity.getUserId();
        NewsFeedType newsFeedType = entity.getNewsFeedType();
        Long activityUserId = entity.getActivityUserId();
        Long relatedUserId = entity.getRelatedUserId();
        Long relatedPosterId = entity.getRelatedPosterId();

        UserResponse userById = userFeignClient.getUserById(new UserRequest(userId));
        //getUserById(UserRequest.builder().userId(userId).build()).getUserDto();
        UserResponse activityById = userFeignClient.getUserById(new UserRequest(activityUserId));
        //Users activityUserDto = userFeignClient.getUserById(GetUserRequestDto.builder().userId(activityUserId).build()).getUserDto();
        UserResponse relatedById = null;
        if(relatedUserId!=null) {
            relatedById = userFeignClient.getUserById(new UserRequest(relatedUserId));
        }
        Post posterDto=null;
        if(relatedPosterId!=null) {
            Response<PostResponse> postDetail = activityFeignClient.getPostDetail(relatedPosterId);
            posterDto = postDetail.getData().getPost();


        }
        return  new NewsFeed(
                userId,
                userById.getName(),
                activityUserId,
                activityById.getName(),
                relatedUserId,
                (relatedById!=null)?relatedById.getName():null,
                relatedPosterId,
                (posterDto!=null)?posterDto.getTitle():null
        );

    }
}
