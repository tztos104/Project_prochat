package prochat.yj_activityservice.controller.response;


import com.example.yj_userservice.dto.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_activityservice.model.Follow;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class FollowResponse {

    int id;
    private String following;
    private String follower;
    private Timestamp follow_date;

    public static FollowResponse fromFollow(Follow follow) {
        return new FollowResponse(
                follow.getId(),
                follow.getFollowing(),
                follow.getFollower(),
                follow.getFollow_date()
        );
    }

}
