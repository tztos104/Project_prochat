package prochat.stockservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "stock_data")
public class StockDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 엔티티의 식별자

    private String symbol; // 주식 코드
    private String stockDate; // 주식 날짜

    private int highPrice; // 고가

    private int lowPrice; // 저가

    private int startPrice; // 시가

    private int endPrice; // 종가

    private long volume; // 거래량
}