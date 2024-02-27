package prochat.yj_newsfeedservice.controller.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetMyFollowersResponseDto  {
    List<FollowerDto> followerList;

    @Builder
    private GetMyFollowersResponseDto(List<FollowerDto> followerList) {
        this.followerList = followerList;
    }
}
