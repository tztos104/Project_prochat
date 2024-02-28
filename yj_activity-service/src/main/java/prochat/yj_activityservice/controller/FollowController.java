package prochat.yj_activityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RestController;
import prochat.yj_activityservice.model.entity.FollowEntity;
import prochat.yj_activityservice.service.FollowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "FollowController", description = "팔로우 관련 Api")
@RestController
@RequestMapping("/api/v1/follow")
@AllArgsConstructor
public class FollowController {

    private  final FollowService followService;

    @Operation(summary = "팔로워 확인하기 API")
    @GetMapping("/followers/{memberId}")
    public List<FollowEntity> getFollowers(@PathVariable String memberId) {
        return followService.getFollowers(memberId);
    }
    @Operation(summary = "팔로잉 확인하기 API")
    @GetMapping("/following/{memberId}")
    public List<FollowEntity> getFollowing(@PathVariable String memberId) {
        return followService.getFollowing(memberId);
    }
    @Operation(summary = "팔로워 하기 API")
    @PostMapping("/follow")
    public void follow(@RequestParam String follower, @RequestParam String following) {

        followService.follow(follower, following);
    }
    @Operation(summary = "팔로워 하기 API")
    @PostMapping("/unfollow")
    public void unfollow(@RequestParam String follower, @RequestParam String following) {
        followService.unfollow(follower, following);
    }
    @Operation(summary = "팔로워 수 확인 하기 API")
    @GetMapping("/followCount/{memberId}")
    public Integer getFollowCount(@PathVariable String memberId) {
        return followService.getFollowCount(memberId);
    }
    @Operation(summary = "팔로잉 수 확인 하기 API")
    @GetMapping("/followingCount/{memberId}")
    public Integer getFollowingCount(@PathVariable String memberId) {
        return followService.getFollowingCount(memberId);
    }
}
