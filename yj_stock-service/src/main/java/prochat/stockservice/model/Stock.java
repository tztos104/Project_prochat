package prochat.stockservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Stock {

    private int id;
    private String stockCode;  // 주식 코드
    private Date stockDate;  // 주식 날짜
    private String stockName;  // 주식 이름
    private int stockIdx;  // 주식 지수 (KOSPI or KOSDAQ)
    private int highPrice;  // 고가
    private int lowPrice;  // 저가
    private int startPrice;  // 시가
    private int endPrice;  // 종가
    private int fPrice;  //차이가격

    private float fRate;  // 등락률

    public Stock(StockEntity stockEntity) {
    }

    public static Stock fromEntity(StockEntity entity){
        return new Stock(
                entity.getId(),
                entity.getStockCode(),
                entity.getStockDate(),
                entity.getStockName(),
                entity.getStockIdx(),
                entity.getHighPrice(),
                entity.getLowPrice(),
                entity.getStartPrice(),
                entity.getEndPrice(),
                entity.getFPrice(),
                entity.getFRate()

        );
    }




}