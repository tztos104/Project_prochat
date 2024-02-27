package prochat.yj_activityservice.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prochat.yj_activityservice.model.entity.PostEntity;


public interface PostRepository extends JpaRepository<PostEntity, Long> {

   Page<PostEntity> findAllByMembers(String members, Pageable pageable);
}