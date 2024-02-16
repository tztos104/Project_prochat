package prochat.yj_batchstockservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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