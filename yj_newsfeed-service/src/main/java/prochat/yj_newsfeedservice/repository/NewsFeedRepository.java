package prochat.yj_newsfeedservice.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prochat.yj_newsfeedservice.model.entity.NewsFeedEntity;
import prochat.yj_newsfeedservice.model.entity.NewsFeedType;

import java.util.List;

public interface NewsFeedRepository extends JpaRepository<NewsFeedEntity,Long> {
    // 유저 아이디와 뉴스피드 타입을 이용하여 페이징으로 뉴스피드 찾기
    Page<NewsFeedEntity> findByUserId(Long userId, Pageable pageable);
    Page<NewsFeedEntity> findByUserIdAndNewsFeedTypeIn(Long userId, List<NewsFeedType> newsFeedType, Pageable pageable);
    List<NewsFeedEntity> findAllByUserEmail(String email);
    List<NewsFeedEntity> findByUserEmailOrderByRegDateDesc(String email);
}
