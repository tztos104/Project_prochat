package prochat.yj_activityservice.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_activityservice.model.Post;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String member;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removerDate;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember(),
                post.getRegDate(),
                post.getUpdateDate(),
                post.getRemoveDate()
        );
    }
}
