package prochat.yj_activityservice.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prochat.yj_activityservice.PostEntity;


public interface PostRepository extends JpaRepository<PostEntity, Long> {
    public Page<PostEntity> findAllByMembersId(Long membersID, Pageable pageable);
}