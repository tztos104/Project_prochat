package prochat.yj_activityservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import prochat.yj_activityservice.model.entity.FollowEntity;

import java.util.List;
import java.util.Optional;


public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    List<FollowEntity> findAllByFollower(String follower);
    List<FollowEntity> findAllByFollowing(String following);
    Optional<FollowEntity> findByFollowerAndFollowing(String followerId, String followingId);

//    @Query(value = "SELECT COUNT(*) from FollowEntity entity WHERE entity.follower = :memberId")
//    Integer countByfollower(@Param("memberId") String memberId);
//
//    @Query(value = "SELECT COUNT(*) from FollowEntity entity WHERE entity.following = :memberId")
//    Integer countByfollowing(@Param("memberId") String memberId);
}
