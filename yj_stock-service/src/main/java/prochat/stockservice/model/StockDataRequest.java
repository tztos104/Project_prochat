package prochat.stockservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockDataRequest {
    String symbol;
    String timeframe;
    int count;
}
