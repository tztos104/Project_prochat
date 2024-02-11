package prochat.stockservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prochat.stockservice.model.StockDataEntity;

import java.util.List;

public interface StockDataEntityRepository extends JpaRepository<StockDataEntity, Long> {

    List<StockDataEntity> findBySymbol(String symbol);
}