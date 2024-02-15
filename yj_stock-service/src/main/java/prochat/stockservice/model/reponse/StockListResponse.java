package prochat.stockservice.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import prochat.stockservice.model.Stock;
import prochat.stockservice.model.StockEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class StockListResponse {
   List<String> StockCode;
   List<String> StockName;

    public StockListResponse(List<String> stockCode) {
        StockCode = stockCode;
    }
}
