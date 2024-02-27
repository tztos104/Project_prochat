package prochat.yj_activityservice.service;


import com.example.yj_userservice.dto.Users;
import com.example.yj_userservice.dto.entity.UsersEntity;
import com.example.yj_userservice.exception.ErrorCode;
import com.example.yj_userservice.exception.ProchatException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prochat.yj_activityservice.client.UserFeignClient;
import prochat.yj_activityservice.model.Comment;
import prochat.yj_activityservice.model.Post;
import prochat.yj_activityservice.model.alarm.AlarmArgs;
import prochat.yj_activityservice.model.alarm.AlarmType;
import prochat.yj_activityservice.model.entity.CommentEntity;
import prochat.yj_activityservice.model.entity.LikeEntity;
import prochat.yj_activityservice.model.entity.PostEntity;
import prochat.yj_activityservice.repository.CommentRepository;
import prochat.yj_activityservice.repository.LikeRepository;
import prochat.yj_activityservice.repository.PostRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final AlarmService alarmService;
    private final UserFeignClient userFeignClient;


    @Transactional
    public void create(String title, String content, String memberId, String stockCode){

        //유저 찾기
        Users memberEntity = userFeignClient.findByEmail(memberId);
        PostEntity postEntity = PostEntity.of(title, content, memberEntity.getEmail(),stockCode);
        //글저장
        postRepository.save(postEntity);


    }

    // 뉴스피드 보여주기
    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Post detail(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        return Post.fromEntity(postEntity);
    }

    public Page<Post> my(String MembersId, Pageable pageable) {
        return postRepository.findAllByMembers(MembersId, pageable).map(Post::fromEntity);
    }


    @Transactional
    public Post modify(String UserId, Long postId, String title, String content) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() ->
                new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));


        if (!Objects.equals(postEntity.getMembers(), UserId)) {
            throw new ProchatException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with post %d", UserId, postId));
        }

        postEntity.setTitle(title);
        postEntity.setContent(content);

        return Post.fromEntity(postRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(String userId, Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        if (!Objects.equals(postEntity.getMembers(), userId)) {
            throw new ProchatException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with post %d", userId, postId));
        }
        likeRepository.deleteAllByPost(postEntity);
        commentRepository.deleteAllByPost(postEntity);
        postRepository.delete(postEntity);
    }

    @Transactional
    public void comment(Long postId, String memberId, String comment) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        Users memberEntity = userFeignClient.findByEmail(memberId);

        commentRepository.save(CommentEntity.of(comment, postEntity, memberEntity.getEmail()));
        String content = String.format("%s 님이 포스트에 댓글을 달았습니다.",memberEntity.getEmail());
        alarmService.send(AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(memberEntity.getEmail(), postId), postEntity.getMembers() ,comment);
    }

    public Page<Comment> getComments(Long postId, Pageable pageable) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        return commentRepository.findAllByPost(postEntity, pageable).map(Comment::fromEntity);
    }

    @Transactional
    public void postLike(Long postId, String MemberId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        Users memberEntity = userFeignClient.findByEmail(MemberId);
        likeRepository.findByMemberAndPost(memberEntity, postEntity).ifPresent(it -> {
            throw new ProchatException(ErrorCode.ALREADY_LIKED_POST, String.format("userName %s already like the post %s", MemberId, postId));
        });

        likeRepository.save(LikeEntity.ofPost(postEntity, memberEntity));
        String content = String.format("%s 님이 포스트에 좋아요를 눌렀습니다.",memberEntity.getEmail());
        alarmService.send(AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(memberEntity.getEmail(), postId), postEntity.getMembers() ,content);

    }
    @Transactional
    public void commentLike(Long commentId, String MemberId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", commentId)));
        Users memberEntity = userFeignClient.findByEmail(MemberId);

        likeRepository.findByMemberAndComment(memberEntity, commentEntity).ifPresent(it -> {
            throw new ProchatException(ErrorCode.ALREADY_LIKED_COMMENT, String.format("userName %s already like the post %s", MemberId, commentId));
        });

        likeRepository.save(LikeEntity.ofComment(commentEntity, memberEntity));
        String content = String.format("%s 님이 댓글에 좋아요를 눌렀습니다.",memberEntity.getEmail());
        alarmService.send(AlarmType.NEW_LIKE_ON_COMMENT, new AlarmArgs(memberEntity.getEmail(), commentId), commentEntity.getMember() ,content);

    }

    public Integer getLikeCount(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        List<LikeEntity> likes = likeRepository.findAllByPost(postEntity);
        return likes.size();
    }

}
