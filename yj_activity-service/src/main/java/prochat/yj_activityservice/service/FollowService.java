package prochat.yj_activityservice.service;


import com.example.yj_userservice.dto.Users;
import com.example.yj_userservice.dto.entity.UsersEntity;
import com.example.yj_userservice.exception.ErrorCode;
import com.example.yj_userservice.exception.ProchatException;
import com.example.yj_userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prochat.yj_activityservice.client.UserFeignClient;
import prochat.yj_activityservice.model.alarm.AlarmArgs;
import prochat.yj_activityservice.model.alarm.AlarmType;
import prochat.yj_activityservice.model.entity.FollowEntity;
import prochat.yj_activityservice.repository.FollowRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserFeignClient userFeignClient;
    private final AlarmService alarmService;

    //지정된 사용자의 팔로워 목록을 가져오는 메서드.
    public List<FollowEntity> getFollowers(String memberId) {
        Users memberEntity = userFeignClient.findByEmail(memberId);
        return followRepository.findAllByFollower(memberEntity.getEmail());
    }
    // 지정된 사용자가 팔로우 중인 목록을 가져오는 메서드.
    public List<FollowEntity> getFollowing(String memberId) {
        Users memberEntity = userFeignClient.findByEmail(memberId);
        return followRepository.findAllByFollowing(memberEntity.getEmail());
    }
    //팔로우
    public void follow(String follower, String following) {
        if (follower.equals(following)) {
            throw new ProchatException(ErrorCode.INVALID_FOLLOW, "자기 자신을 팔로우할 수 없습니다");
        }
        Users followerId = userFeignClient.findByEmail(follower);
        Users followingById = userFeignClient.findByEmail(following);

        followRepository.findByFollowerAndFollowing(followerId.getEmail(), followingById.getEmail()).ifPresent(it -> {
                    throw new ProchatException(ErrorCode.DUPLICATED_FOLLOW, String.format("%s 는 %s 를 이미 팔로우 하고 있습니다", follower, following));
                });
        followRepository.save(FollowEntity.of(followerId.getEmail(), followingById.getEmail()));
        String content = String.format("%s 님에게 팔로우 요청이 들어왔습니다",followerId.getEmail());
        alarmService.send(AlarmType.NEW_FOLLOW, new AlarmArgs(followerId.getEmail(), followingById.getId()), followingById.getEmail(),content);

    }

    public void unfollow(String follower, String following) {
        Users followerId = userFeignClient.findByEmail(follower);
        Users followingById = userFeignClient.findByEmail(following);
        FollowEntity followEntity = followRepository.findByFollowerAndFollowing(followerId.getEmail(), followingById.getEmail()).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 는 %s 를 이미 팔로우 하고 있습니다", follower, following)));
        followRepository.delete(followEntity);
    }
    public Integer getFollowCount(String memberId) {
        Users memberEntity = userFeignClient.findByEmail(memberId);
        List<FollowEntity> allByFollower = followRepository.findAllByFollower(memberEntity.getEmail());

        return allByFollower.size();
    }
    public Integer getFollowingCount(String memberId) {
        Users memberEntity = userFeignClient.findByEmail(memberId);
        List<FollowEntity> allByFollower = followRepository.findAllByFollowing(memberEntity.getEmail());

        return allByFollower.size();
    }

}
