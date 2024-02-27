package prochat.yj_batchstockservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prochat.yj_batchstockservice.model.StockDataEntity;


import java.util.List;
@Repository
public interface StockDataEntityRepository extends JpaRepository<StockDataEntity, Long> {

    List<StockDataEntity> findBySymbol(String symbol);
    // 같은 symbol을 가진 모든 데이터의 endPrice 값만을 선택하여 리스트 반환
    List<StockDataEntity> findEndPriceBySymbol(String symbol);
}