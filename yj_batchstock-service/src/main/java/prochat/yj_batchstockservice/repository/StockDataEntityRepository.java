package prochat.yj_batchstockservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prochat.yj_batchstockservice.model.StockDataEntity;


import java.util.List;
@Repository
public interface StockDataEntityRepository extends JpaRepository<StockDataEntity, Long> {

    List<StockDataEntity> findBySymbol(String symbol);
}