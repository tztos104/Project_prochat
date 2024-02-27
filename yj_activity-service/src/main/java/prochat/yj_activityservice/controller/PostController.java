package prochat.yj_activityservice.controller;



import com.example.yj_userservice.config.ClassUtils;
import com.example.yj_userservice.dto.Users;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import prochat.yj_activityservice.service.PostService;
import prochat.yj_activityservice.controller.response.Response;
import prochat.yj_activityservice.request.PostCommentRequest;
import prochat.yj_activityservice.request.PostCreateRequest;
import prochat.yj_activityservice.response.CommentResponse;
import prochat.yj_activityservice.response.PostResponse;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private  final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication){

        postService.create(request.getTitle(), request.getContent(), authentication.getName(), request.getStockCode());
        return Response.success();
    }
    @GetMapping("/{postId}")
    public Response<PostResponse> detail(@PathVariable(name = "postId") Long postId, @RequestBody Authentication authentication){

        Users members = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        return Response.success(PostResponse.fromPost(
                postService.detail( postId)));
    }



    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable(name = "postId") Long postId, @RequestBody PostCreateRequest request, Authentication authentication){

        Users members = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        return Response.success(PostResponse.fromPost(
                postService.modify(members.getEmail(), postId, request.getTitle(), request.getContent())));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        Users members = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);
        postService.delete(members.getEmail(), postId);
        return Response.success();
    }

    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> getComments(Pageable pageable, @PathVariable(name = "postId") Long postId) {
        return Response.success(postService.getComments(postId, pageable).map(CommentResponse::fromComment));
    }
    @PostMapping("/{postId}/comments")
    public Response<Void> comment(@PathVariable(name = "postId") Long postId, @RequestBody PostCommentRequest request, Authentication authentication) {
        postService.comment(postId, authentication.getName(), request.getComment());
        return Response.success();
    }

    @GetMapping("/{postId}/likes")
    public Response<Integer> getLikes(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        return Response.success(postService.getLikeCount(postId));
    }

    @PostMapping("/{postId}/likes")
    public Response<Void> like(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        postService.postLike(postId, authentication.getName());
        return Response.success();
    }

    @PostMapping("/comments/likes")
    public Response<Void> commentLike(@RequestParam(name = "commentId") Long commentId,Authentication authentication) {
        postService.commentLike(commentId, authentication.getName());
        return Response.success();
    }


}
