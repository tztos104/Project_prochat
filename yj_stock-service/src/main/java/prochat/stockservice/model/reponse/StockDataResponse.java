package prochat.stockservice.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockDataResponse {
    String symbol;
    String timeframe;
    String count;
}
