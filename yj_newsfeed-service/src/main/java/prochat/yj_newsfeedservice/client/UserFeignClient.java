package prochat.yj_newsfeedservice.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import prochat.yj_newsfeedservice.request.UserRequest;
import prochat.yj_newsfeedservice.response.UserResponse;


@FeignClient(name = "user-service")
public interface UserFeignClient {
    @PostMapping(path = "/find-by-email")
    UserResponse getUserById(@RequestBody String email);

//    @PostMapping("/get-my-follower")
//    GetMyFollowersResponseDto getMyFollower(GetMyFollowersRequestDto dto);



}