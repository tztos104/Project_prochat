package prochat.yj_newsfeedservice.controller.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetUserResponseDto  {
    String code;
    String message;
    UserDto userDto;

    @Builder
    private GetUserResponseDto(String code, String message, UserDto userDto) {
        this.code = code;
        this.message = message;
        this.userDto = userDto;
    }
}
