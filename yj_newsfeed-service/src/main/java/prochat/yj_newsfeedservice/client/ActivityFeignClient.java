package prochat.yj_newsfeedservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import prochat.yj_newsfeedservice.controller.response.Response;
import prochat.yj_newsfeedservice.request.PostRequest;
import prochat.yj_newsfeedservice.request.UserRequest;
import prochat.yj_newsfeedservice.response.PostResponse;
import prochat.yj_newsfeedservice.response.UserResponse;

@FeignClient(name = "activity-service")
public interface ActivityFeignClient {
    @PostMapping(path = "/api/v1/posts/{postId}")
    Response<PostResponse> getPostDetail(@PathVariable(name = "postId") Long postId);



}
