package prochat.yj_newsfeedservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import prochat.yj_newsfeedservice.controller.request.PostCreateRequest;
import prochat.yj_newsfeedservice.controller.response.Response;
import prochat.yj_newsfeedservice.request.PostRequest;
import prochat.yj_newsfeedservice.request.UserRequest;
import prochat.yj_newsfeedservice.response.PostResponse;
import prochat.yj_newsfeedservice.response.UserResponse;

@FeignClient(name = "activity-service")
public interface ActivityFeignClient {
    @PostMapping(path = "/api/v1/posts/{postId}")
    Response<PostResponse> getPostDetail(@PathVariable(name = "postId") Long postId);

    @PostMapping("/api/v1/posts")
    Response<Void> createPost(@RequestBody PostCreateRequest request);

     @PutMapping("/api/v1/posts/{postId}")
    Response<PostResponse> modifyPost(@PathVariable(name = "postId") Long postId, @RequestBody PostCreateRequest request);

    @DeleteMapping("/api/v1/posts/{postId}")
    Response<Void> deletePost(@PathVariable(name = "postId") Long postId);


    @GetMapping("/api/v1/posts/{postId}/likes")
    Response<Integer> getLikesCount(@PathVariable(name = "postId") Long postId);

    @PostMapping("/api/v1/posts/{postId}/likes")
    Response<Void> likePost(@PathVariable(name = "postId") Long postId);

    @PostMapping("/api/v1/posts/comments/likes")
    Response<Void> likeComment(@RequestParam(name = "commentId") Long commentId);

}
