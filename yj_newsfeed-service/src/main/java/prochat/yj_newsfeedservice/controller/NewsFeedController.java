package prochat.yj_newsfeedservice.controller;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prochat.yj_newsfeedservice.controller.response.Response;
import prochat.yj_newsfeedservice.service.NewsFeedService;


@Slf4j
@RestController
@RequestMapping("/api/newsFeed")
@RequiredArgsConstructor
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    @PostMapping("/get-myNewsFeed")
    Response<GetMyNewsFeedRequestDto> getMyNewsFeed(
           @RequestBody GetMyNewsFeedRequestDto requestBody,
            HttpServletRequest request
    ){
        String jwtToken = getTokenFromHeader(request.getHeader(HEADER_STRING));
        Long loginId = getUserIdFromToken(jwtToken);
        Long userId = requestBody.getUserId();
        if(loginId!=userId) return ResponseDto.certificationFail();
        ResponseEntity<? super GetMyNewsFeedResponseDto> response = newsFeedService.getMyNewsFeeds(requestBody);
        return response;
    }

    @PostMapping("/get-myNewsFeed-by-types")
    ResponseEntity<? super GetMyNewsFeedByTypeResponseDto> getMyNewsFeedByTypes(
            @Valid@RequestBody GetMyNewsFeedByTypesRequestDto requestBody,
            HttpServletRequest request
    ){
        String jwtToken = getTokenFromHeader(request.getHeader(HEADER_STRING));
        Long loginId = getUserIdFromToken(jwtToken);
        Long userId = requestBody.getUserId();
        ResponseEntity<? super GetMyNewsFeedByTypeResponseDto> response = newsFeedService.getMyNewsFeedsByType(requestBody);
        return response;
    }

    @PostMapping("create-newsFeed")
    ResponseEntity<? super CreateNewsFeedResponseDto>createNewsFeed(
            @RequestBody CreateNewsFeedRequestDto requestBody
    ){
        log.info("뉴스피드 메시지 도착");
        ResponseEntity<? super CreateNewsFeedResponseDto> response = newsFeedService.createNewsFeed(requestBody);
        return response;
    }


}
