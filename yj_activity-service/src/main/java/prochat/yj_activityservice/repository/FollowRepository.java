package prochat.yj_activityservice.repository;


import com.example.yj_userservice.dto.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prochat.yj_activityservice.model.entity.FollowEntity;

import java.util.List;
import java.util.Optional;


public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    List<FollowEntity> findAllByFollower(UsersEntity follower);
    List<FollowEntity> findAllByFollowing(UsersEntity following);
    Optional<FollowEntity> findByFollowerAndFollowing(UsersEntity followerId, UsersEntity followingId);

    @Query(value = "SELECT COUNT(*) from FollowEntity entity WHERE entity.follower = :memberId")
    Integer countByfollower(@Param("memberId") UsersEntity memberId);

    @Query(value = "SELECT COUNT(*) from FollowEntity entity WHERE entity.following = :memberId")
    Integer countByfollowing(@Param("memberId") UsersEntity memberId);
}
