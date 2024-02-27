package prochat.yj_activityservice.request;


import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String email;


}
