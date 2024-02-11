package prochat.yj_activityservice.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_activityservice.model.Comment;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String comment;
    private Long userId;
    private String userName;
    private Long postId;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removeDate;

    public static CommentResponse fromComment(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUserId(),
                comment.getUserName(),
                comment.getPostId(),
                comment.getRegDate(),
                comment.getUpdateDate(),
                comment.getRemoveDate()
        );
    }
}
