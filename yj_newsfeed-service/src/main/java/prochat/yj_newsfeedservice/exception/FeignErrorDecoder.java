package prochat.yj_newsfeedservice.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                break;
            case 404:
                if(methodKey.contains("getOrders")){
                    return new ProchatException(ErrorCode.DATABASE_ERROR);
                }
                break;
            default:
                return new Exception(response.reason());
        }



        return null;
    }
}
