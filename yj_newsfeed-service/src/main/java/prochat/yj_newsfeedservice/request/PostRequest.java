package prochat.yj_newsfeedservice.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_newsfeedservice.model.Post;
import prochat.yj_newsfeedservice.response.PostResponse;
import prochat.yj_newsfeedservice.response.UserResponse;


import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class PostRequest {

    private Long id;
    private String title;
    private String content;
    private UserResponse member;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removerDate;

    public PostRequest() {
    }
}
