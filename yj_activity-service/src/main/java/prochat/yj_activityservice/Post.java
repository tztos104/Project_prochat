package prochat.yj_activityservice;


import com.example.yj_userservice.dto.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Post {
    private Long id = null;
    private String title;
    private String content;
    private Users member;
    private String StockCode;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removeDate;

    public static Post fromEntity(PostEntity entity) {
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                Users.fromEntity(entity.getMembers()),
                entity.getStockCode(),
                entity.getRegDate(),
                entity.getUpdateDate(),
                entity.getRemoveDate()
        );
    }
}
