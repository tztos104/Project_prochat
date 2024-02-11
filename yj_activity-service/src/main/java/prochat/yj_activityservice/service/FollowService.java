package prochat.yj_activityservice.service;


import com.example.yj_userservice.dto.entity.UsersEntity;
import com.example.yj_userservice.exception.ErrorCode;
import com.example.yj_userservice.exception.ProchatException;
import com.example.yj_userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prochat.yj_activityservice.model.alarm.AlarmArgs;
import prochat.yj_activityservice.model.alarm.AlarmType;
import prochat.yj_activityservice.model.entity.FollowEntity;
import prochat.yj_activityservice.repository.FollowRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    private final UserRepository userRepository;
    private final AlarmService alarmService;

    //지정된 사용자의 팔로워 목록을 가져오는 메서드.
    public List<FollowEntity> getFollowers(String memberId) {
        UsersEntity memberEntity = userRepository.findByEmail(memberId).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 유저를 찾을 수 없습니다", memberId)));
        return followRepository.findAllByFollower(memberEntity);
    }
    // 지정된 사용자가 팔로우 중인 목록을 가져오는 메서드.
    public List<FollowEntity> getFollowing(String memberId) {
        UsersEntity memberEntity = userRepository.findByEmail(memberId).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 유저를 찾을 수 없습니다", memberId)));
        return followRepository.findAllByFollowing(memberEntity);
    }
    //팔로우
    public void follow(String follower, String following) {
        if (follower.equals(following)) {
            throw new ProchatException(ErrorCode.INVALID_FOLLOW, "자기 자신을 팔로우할 수 없습니다");
        }
        UsersEntity followerId = userRepository.findByEmail(follower).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 유저를 찾을 수 없습니다", follower)));
        UsersEntity followingById = userRepository.findByEmail(following).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 유저를 찾을 수 없습니다", follower)));

        followRepository.findByFollowerAndFollowing(followerId, followingById).ifPresent(it -> {
                    throw new ProchatException(ErrorCode.DUPLICATED_FOLLOW, String.format("%s 는 %s 를 이미 팔로우 하고 있습니다", follower, following));
                });
        followRepository.save(FollowEntity.of(followerId, followingById));
        String content = String.format("%s 님에게 팔로우 요청이 들어왔습니다",followerId.getEmail());
        alarmService.send(AlarmType.NEW_FOLLOW, new AlarmArgs(followerId.getEmail(), followingById.getId()), followingById.getId(),content);

    }

    public void unfollow(String follower, String following) {
        UsersEntity followerId = userRepository.findByEmail(follower).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 유저를 찾을 수 없습니다", follower)));
        UsersEntity followingById = userRepository.findByEmail(following).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 유저를 찾을 수 없습니다", follower)));
        FollowEntity followEntity = followRepository.findByFollowerAndFollowing(followerId, followingById).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 는 %s 를 이미 팔로우 하고 있습니다", follower, following)));
        followRepository.delete(followEntity);
    }
    public Integer getFollowCount(String memberId) {
        UsersEntity memberEntity = userRepository.findByEmail(memberId).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 유저를 찾을 수 없습니다", memberId)));
        List<FollowEntity> allByFollower = followRepository.findAllByFollower(memberEntity);

        return allByFollower.size();
    }
    public Integer getFollowingCount(String memberId) {
        UsersEntity memberEntity = userRepository.findByEmail(memberId).orElseThrow(() ->
                new ProchatException(ErrorCode.USER_NOT_FOUND, String.format("%s 유저를 찾을 수 없습니다", memberId)));
        List<FollowEntity> allByFollower = followRepository.findAllByFollowing(memberEntity);

        return allByFollower.size();
    }

}
