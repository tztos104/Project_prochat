package prochat.yj_batchstockservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;



@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name="stock")
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String stockCode;  // 주식 코드

    @Temporal(TemporalType.DATE)
    private Date stockDate;  // 주식 날짜

    private String stockName;  // 주식 이름

    private int stockIdx;  // 주식 지수 (KOSPI or KOSDAQ)

    private int highPrice;  // 고가

    private int lowPrice;  // 저가

    private int startPrice;  // 시가

    private int endPrice;  // 종가

    private int fPrice;  //차이가격

    private float fRate;  // 등락률


    public StockEntity(String abc, String companyAbc) {
    }
}