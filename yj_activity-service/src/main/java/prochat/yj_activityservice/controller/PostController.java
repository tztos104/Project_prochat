package prochat.yj_activityservice.controller;



import com.example.yj_userservice.config.ClassUtils;
import com.example.yj_userservice.model.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "PostController", description = "포스트 관련 Api")
@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private  final PostService postService;
    @Operation(summary = "포스트 작성 API")
    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication){

        postService.create(request.getTitle(), request.getContent(), authentication.getName(), request.getStockCode());
        return Response.success();
    }
    @Operation(summary = "포스트 상세보기 API")
    @GetMapping("/{postId}")
    public Response<PostResponse> detail(@PathVariable(name = "postId") Long postId, @RequestBody Authentication authentication){

        Users members = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        return Response.success(PostResponse.fromPost(
                postService.detail( postId)));
    }


    @Operation(summary = "포스트 수정하기 API")
    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable(name = "postId") Long postId, @RequestBody PostCreateRequest request, Authentication authentication){

        Users members = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);

        return Response.success(PostResponse.fromPost(
                postService.modify(members.getEmail(), postId, request.getTitle(), request.getContent())));
    }
    @Operation(summary = "포스트 지우기 API")
    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        Users members = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), Users.class);
        postService.delete(members.getEmail(), postId);
        return Response.success();
    }
    @Operation(summary = "포스트에 코멘트 작성하기 API")
    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> getComments(Pageable pageable, @PathVariable(name = "postId") Long postId) {
        return Response.success(postService.getComments(postId, pageable).map(CommentResponse::fromComment));
    }
    @Operation(summary = "포스트 코멘트보기 API")
    @PostMapping("/{postId}/comments")
    public Response<Void> comment(@PathVariable(name = "postId") Long postId, @RequestBody PostCommentRequest request, Authentication authentication) {
        postService.comment(postId, authentication.getName(), request.getComment());
        return Response.success();
    }
    @Operation(summary = "포스트 좋아요 갯수 확인 API")
    @GetMapping("/{postId}/likes")
    public Response<Integer> getLikes(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        return Response.success(postService.getLikeCount(postId));
    }
    @Operation(summary = "포스트 좋아요 하기 API")
    @PostMapping("/{postId}/likes")
    public Response<Void> like(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        postService.postLike(postId, authentication.getName());
        return Response.success();
    }
    @Operation(summary = "코멘트 좋아요 하기 API")
    @PostMapping("/comments/likes")
    public Response<Void> commentLike(@RequestParam(name = "commentId") Long commentId,Authentication authentication) {
        postService.commentLike(commentId, authentication.getName());
        return Response.success();
    }


}
