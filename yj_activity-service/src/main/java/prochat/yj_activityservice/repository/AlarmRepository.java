package prochat.yj_activityservice.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prochat.yj_activityservice.model.entity.AlarmEntity;

public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {

    Page<AlarmEntity> findAllByMembers(String MembersId, Pageable pageable);

}