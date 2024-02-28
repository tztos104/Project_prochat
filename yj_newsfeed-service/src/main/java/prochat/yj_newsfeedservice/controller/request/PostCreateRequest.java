package prochat.yj_newsfeedservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateRequest {
    private String title;
    private String content;
    private String email; // 작성자의 이메일
    private String stockCode; // 주식 코드

    public PostCreateRequest() {
    }
}
