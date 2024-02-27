package prochat.yj_newsfeedservice.controller.request;

import com.example.stockmsanewsfeed.web.dto.request.RequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMyFollowersRequestDto extends RequestDto {
    private Long userId;

    @Builder
    private GetMyFollowersRequestDto(Long userId) {
        this.userId = userId;
    }
}
