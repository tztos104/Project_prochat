package prochat.yj_newsfeedservice.response;



import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_newsfeedservice.model.entity.Users;


@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String name;


    public static UserResponse fromUser(Users user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName()

        );
    }
}
