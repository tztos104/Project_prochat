package prochat.yj_newsfeedservice.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import prochat.yj_newsfeedservice.model.entity.Users;


@Data
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String email;
    private String name;

    public UserRequest(Long userId) {
    }
}
