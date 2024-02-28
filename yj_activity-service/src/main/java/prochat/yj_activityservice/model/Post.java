package prochat.yj_activityservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_activityservice.model.entity.PostEntity;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Post {
    private Long id;
    private String title;
    private String content;
    private String member;
    private String StockCode;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removeDate;

    public static Post fromEntity(PostEntity entity) {
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getMembers(),
                entity.getStockCode(),
                entity.getRegDate(),
                entity.getUpdateDate(),
                entity.getRemoveDate()
        );
    }
}
