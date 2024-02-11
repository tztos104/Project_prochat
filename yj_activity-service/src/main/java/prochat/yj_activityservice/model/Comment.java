package prochat.yj_activityservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_activityservice.model.entity.CommentEntity;

import java.sql.Timestamp;
@Getter
@AllArgsConstructor
public class Comment {
    private Long id;
    private String comment;
    private Long userId;
    private String userName;
    private Long postId;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removeDate;

    public static Comment fromEntity(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getComment(),
                entity.getMember().getId(),
                entity.getMember().getEmail(),
                entity.getPost().getId(),
                entity.getRegDate(),
                entity.getUpdateDate(),
                entity.getRemoveDate()
        );
    }
}
