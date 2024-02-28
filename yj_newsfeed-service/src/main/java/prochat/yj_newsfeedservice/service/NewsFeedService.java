package prochat.yj_newsfeedservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import prochat.yj_newsfeedservice.client.ActivityFeignClient;
import prochat.yj_newsfeedservice.client.UserFeignClient;
import prochat.yj_newsfeedservice.controller.request.PostCreateRequest;
import prochat.yj_newsfeedservice.controller.response.Response;
import prochat.yj_newsfeedservice.model.entity.NewsFeedEntity;
import prochat.yj_newsfeedservice.model.entity.NewsFeedType;
import prochat.yj_newsfeedservice.repository.NewsFeedRepository;
import prochat.yj_newsfeedservice.response.PostResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;
    private final UserFeignClient userFeignClient;
    private final ActivityFeignClient activityFeignClient;

    public List<NewsFeedEntity> getNewsFeedsByUserId(String email) {
        userFeignClient.getUserById(email);
        return newsFeedRepository.findAllByUserEmail(email);
    }

    public List<NewsFeedEntity> getNewsFeedsByUserIdOrderByDateDesc(String email) {
        return newsFeedRepository.findByUserEmailOrderByRegDateDesc(email);
    }

    public List<NewsFeedEntity> getAllNewsFeedsOrderByDateDesc() {
        return newsFeedRepository.findAll(Sort.by(Sort.Direction.DESC, "regDate"));
    }

    public NewsFeedEntity createNewsFeed(String email, NewsFeedType newsFeedType, String activityUserId,
                                         String relatedUserId, Long relatedPosterId) {
        NewsFeedEntity newsFeedEntity = NewsFeedEntity.of(email, newsFeedType, activityUserId, relatedUserId, relatedPosterId);
        return newsFeedRepository.save(newsFeedEntity);
    }


    public void createPost(PostCreateRequest request) {
        activityFeignClient.createPost(request);

    }

    public Response<PostResponse> getPostDetail(Long postId) {
        return activityFeignClient.getPostDetail(postId);

    }

    public Response<PostResponse> modifyPost(Long postId, PostCreateRequest request) {
        return activityFeignClient.modifyPost(postId, request);

    }

    public void deletePost(Long postId) {
        activityFeignClient.deletePost(postId);

    }
}
