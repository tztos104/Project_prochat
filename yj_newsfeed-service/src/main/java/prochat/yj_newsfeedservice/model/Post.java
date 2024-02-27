package prochat.yj_newsfeedservice.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_newsfeedservice.model.entity.Users;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Post {
    private Long id = null;
    private String title;
    private String content;
    private String userEmail;
    private String StockCode;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removeDate;

}
