package prochat.yj_activityservice.model;


import com.example.yj_userservice.dto.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import prochat.yj_activityservice.model.entity.FollowEntity;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private Users following;
    private Users follower;
    private Timestamp follow_date;





    public static Follow fromEntity(FollowEntity entity) {
        return new Follow(
                entity.getId(),
                Users.fromEntity(entity.getFollower()),
                Users.fromEntity(entity.getFollowing()),
                entity.getFollow_date()

        );
    }
}
