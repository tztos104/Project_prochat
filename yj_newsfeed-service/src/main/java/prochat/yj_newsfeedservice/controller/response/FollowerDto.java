package prochat.yj_newsfeedservice.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowerDto {

    private String followerName;
    private Long followerId;


    @Builder
    private FollowerDto(String followerName, Long followerId) {
        this.followerName = followerName;
        this.followerId = followerId;
    }
}
