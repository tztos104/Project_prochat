package prochat.yj_activityservice.controller;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RestController;
import prochat.yj_activityservice.model.entity.FollowEntity;
import prochat.yj_activityservice.service.FollowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follow")
@AllArgsConstructor
public class FollowController {

    private  final FollowService followService;


    @GetMapping("/followers/{memberId}")
    public List<FollowEntity> getFollowers(@PathVariable String memberId) {
        return followService.getFollowers(memberId);
    }

    @GetMapping("/following/{memberId}")
    public List<FollowEntity> getFollowing(@PathVariable String memberId) {
        return followService.getFollowing(memberId);
    }

    @PostMapping("/follow")
    public void follow(@RequestParam String follower, @RequestParam String following) {

        followService.follow(follower, following);
    }

    @PostMapping("/unfollow")
    public void unfollow(@RequestParam String follower, @RequestParam String following) {
        followService.unfollow(follower, following);
    }

    @GetMapping("/followCount/{memberId}")
    public Integer getFollowCount(@PathVariable String memberId) {
        return followService.getFollowCount(memberId);
    }

    @GetMapping("/followingCount/{memberId}")
    public Integer getFollowingCount(@PathVariable String memberId) {
        return followService.getFollowingCount(memberId);
    }
}
