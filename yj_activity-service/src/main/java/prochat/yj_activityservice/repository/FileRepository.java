package prochat.yj_activityservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import prochat.yj_activityservice.model.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
