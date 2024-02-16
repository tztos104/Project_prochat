package prochat.yj_batchstockservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockDataRequest {
    String symbol;
    String timeframe;
    String count;
}
