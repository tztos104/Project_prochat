package prochat.yj_activityservice.response;


import com.example.yj_userservice.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;

    public static UserResponse fromUser(Users user) {
        return new UserResponse(
                user.getId(),
                user.getEmail()
        );
    }
}
