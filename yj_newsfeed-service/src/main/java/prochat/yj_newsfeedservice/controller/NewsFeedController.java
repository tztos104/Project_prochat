package prochat.yj_newsfeedservice.controller;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prochat.yj_newsfeedservice.controller.request.CreateNewsFeedRequest;
import prochat.yj_newsfeedservice.controller.response.Response;
import prochat.yj_newsfeedservice.model.entity.NewsFeedEntity;
import prochat.yj_newsfeedservice.service.NewsFeedService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/newsFeed")
@RequiredArgsConstructor
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    @GetMapping("/user/{userId}")
    public List<NewsFeedEntity> getNewsFeedsByUserId(@PathVariable String userId) {
        return newsFeedService.getNewsFeedsByUserId(userId);
    }

    @GetMapping("/user/{userId}/orderByDateDesc")
    public List<NewsFeedEntity> getNewsFeedsByUserIdOrderByDateDesc(@PathVariable String userId) {
        return newsFeedService.getNewsFeedsByUserIdOrderByDateDesc(userId);
    }

    @GetMapping("/all/orderByDateDesc")
    public List<NewsFeedEntity> getAllNewsFeedsOrderByDateDesc() {
        return newsFeedService.getAllNewsFeedsOrderByDateDesc();
    }

    @PostMapping("/create")
    public NewsFeedEntity createNewsFeed(@RequestBody CreateNewsFeedRequest request) {
        // 클라이언트에서 요청 바디로 전달한 정보를 사용하여 뉴스피드를 생성합니다.
        return newsFeedService.createNewsFeed(request.getUserId(), request.getNewsFeedType(),
                request.getActivityUserId(), request.getRelatedUserId(),
                request.getRelatedPosterId());
    }

}
