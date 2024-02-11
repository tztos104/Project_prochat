package prochat.yj_activityservice;


import com.example.yj_userservice.dto.entity.UsersEntity;
import com.example.yj_userservice.exception.ErrorCode;
import com.example.yj_userservice.exception.ProchatException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prochat.yj_activityservice.model.Comment;
import prochat.yj_activityservice.model.alarm.AlarmArgs;
import prochat.yj_activityservice.model.alarm.AlarmType;
import prochat.yj_activityservice.model.entity.CommentEntity;
import prochat.yj_activityservice.model.entity.LikeEntity;
import prochat.yj_activityservice.repository.CommentRepository;
import prochat.yj_activityservice.repository.LikeRepository;
import prochat.yj_activityservice.repository.PostRepository;
import prochat.yj_activityservice.repository.UserRepository;
import prochat.yj_activityservice.service.AlarmService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final AlarmService alarmService;


    @Transactional
    public void create(String title, String content, String memberId, String stockCode){

        //유저 찾기
        UsersEntity memberEntity = userRepository.findByEmail(memberId).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 찾을 수 없습니다.", memberId)));
        PostEntity postEntity = PostEntity.of(title, content, memberEntity,stockCode);
        //글저장
        postRepository.save(postEntity);


    }

    // 뉴스피드 보여주기
    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(Long MembersId, Pageable pageable) {
        return postRepository.findAllByMembersId(MembersId, pageable).map(Post::fromEntity);
    }


    @Transactional
    public Post modify(Long Id, Long postId, String title, String content) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() ->
                new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));


        if (!Objects.equals(postEntity.getMembers().getId(), Id)) {
            throw new ProchatException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with post %d", Id, postId));
        }

        postEntity.setTitle(title);
        postEntity.setContent(content);

        return Post.fromEntity(postRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(Long userId, Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        if (!Objects.equals(postEntity.getMembers().getId(), userId)) {
            throw new ProchatException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with post %d", userId, postId));
        }
        likeRepository.deleteAllByPost(postEntity);
        commentRepository.deleteAllByPost(postEntity);
        postRepository.delete(postEntity);
    }

    @Transactional
    public void comment(Long postId, String memberId, String comment) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        UsersEntity memberEntity = userRepository.findByEmail(memberId)
                .orElseThrow(() -> new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", memberId)));

        commentRepository.save(CommentEntity.of(comment, postEntity, memberEntity));
        String content = String.format("%s 님이 포스트에 댓글을 달았습니다.",memberEntity.getEmail());
        alarmService.send(AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(memberEntity.getEmail(), postId), postEntity.getMembers().getId(),comment);
    }

    public Page<Comment> getComments(Long postId, Pageable pageable) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        return commentRepository.findAllByPost(postEntity, pageable).map(Comment::fromEntity);
    }

    @Transactional
    public void postLike(Long postId, String MemberId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        UsersEntity memberEntity = userRepository.findByEmail(MemberId)
                .orElseThrow(() -> new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", MemberId)));

        likeRepository.findByMemberAndPost(memberEntity, postEntity).ifPresent(it -> {
            throw new ProchatException(ErrorCode.ALREADY_LIKED_POST, String.format("userName %s already like the post %s", MemberId, postId));
        });

        likeRepository.save(LikeEntity.ofPost(postEntity, memberEntity));
        String content = String.format("%s 님이 포스트에 좋아요를 눌렀습니다.",memberEntity.getEmail());
        alarmService.send(AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(memberEntity.getEmail(), postId), postEntity.getMembers().getId(),content);

    }
    @Transactional
    public void commentLike(Long commentId, String MemberId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", commentId)));
        UsersEntity memberEntity = userRepository.findByEmail(MemberId)
                .orElseThrow(() -> new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", MemberId)));

        likeRepository.findByMemberAndComment(memberEntity, commentEntity).ifPresent(it -> {
            throw new ProchatException(ErrorCode.ALREADY_LIKED_COMMENT, String.format("userName %s already like the post %s", MemberId, commentId));
        });

        likeRepository.save(LikeEntity.ofComment(commentEntity, memberEntity));
        String content = String.format("%s 님이 댓글에 좋아요를 눌렀습니다.",memberEntity.getEmail());
        alarmService.send(AlarmType.NEW_LIKE_ON_COMMENT, new AlarmArgs(memberEntity.getEmail(), commentId), commentEntity.getMember().getId(),content);

    }

    public Integer getLikeCount(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ProchatException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        List<LikeEntity> likes = likeRepository.findAllByPost(postEntity);
        return likes.size();
    }

}
