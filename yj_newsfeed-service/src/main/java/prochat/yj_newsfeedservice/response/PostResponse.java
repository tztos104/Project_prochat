package prochat.yj_newsfeedservice.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_newsfeedservice.model.Post;


import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String member;
    private String stockCode;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removerDate;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUserEmail(),
                post.getStockCode(),
                post.getRegDate(),
                post.getUpdateDate(),
                post.getRemoveDate()
        );
    }

    public Post getPost() {
        return new Post(
                id,
                title,
                content,
                member,
                stockCode,
                regDate,
                updateDate,
                removerDate
        );
    }
}
