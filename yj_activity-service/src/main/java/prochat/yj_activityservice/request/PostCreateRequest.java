package prochat.yj_activityservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateRequest {

    private String title;
    private String content;
    private String stockCode;

    public PostCreateRequest() {
    }
}
