package prochat.yj_activityservice.repository;


import com.example.yj_userservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import prochat.yj_activityservice.model.entity.PostEntity;
import prochat.yj_activityservice.model.entity.CommentEntity;
import prochat.yj_activityservice.model.entity.LikeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity,Long> {
    Optional<LikeEntity> findByMemberAndPost(Users member, PostEntity post);
    Optional<LikeEntity> findByMemberAndComment(Users member, CommentEntity comment);


    @Query(value = "SELECT COUNT(*) from LikeEntity entity WHERE entity.post = :post")
    Integer countByPost(@Param("post") PostEntity post);

    List<LikeEntity> findAllByPost(PostEntity post);


    @Transactional
    @Modifying
    @Query("UPDATE LikeEntity entity SET entity.removeDate = NOW() where entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity post);
}
