package prochat.yj_newsfeedservice.model.entity;


import com.example.yj_userservice.dto.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "follow")
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private UsersEntity following;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private UsersEntity follower;

    private Timestamp follow_date;

    @PrePersist
    void follow_date() {
        this.follow_date = Timestamp.from(Instant.now());
    }

    public static FollowEntity of(UsersEntity follower, UsersEntity following) {
        FollowEntity entity = new FollowEntity();
        entity.setFollowing(following);
        entity.setFollower(follower);
        return entity;
    }

}