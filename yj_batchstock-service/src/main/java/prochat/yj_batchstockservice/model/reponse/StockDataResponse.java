package prochat.yj_batchstockservice.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockDataResponse {
    String symbol;
    String timeframe;
    String count;
}
