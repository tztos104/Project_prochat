package prochat.yj_activityservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_activityservice.model.entity.LikeEntity;

import java.sql.Timestamp;


@Getter
@AllArgsConstructor
public class Like {
    private Long id;
    private Long memberId;
    private String memberName;
    private Long postId;
    private Long commentId;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;


    public static Like fromEntity(LikeEntity entity) {
        return new Like(
                entity.getId(),
                entity.getMember().getId(),
                entity.getMember().getEmail(),
                entity.getPost().getId(),
                entity.getComment().getId(),
                entity.getRegDate(),
                entity.getUpdateDate(),
                entity.getRemoveDate()
        );
    }
}
