package prochat.yj_activityservice.client;

import com.example.yj_userservice.dto.Users;
import com.example.yj_userservice.dto.entity.UsersEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/users/{email}")  // 유저 서비스의 API 엔드포인트를 정의
    Users findByEmail(@PathVariable String email);
    @GetMapping("/user/{id}")  // 유저 서비스의 API 엔드포인트를 정의
    Users findByUserId(@PathVariable Long userId);

}
