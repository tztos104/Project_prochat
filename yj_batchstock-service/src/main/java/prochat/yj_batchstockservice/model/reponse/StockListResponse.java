package prochat.yj_batchstockservice.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

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
