package prochat.stockservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prochat.stockservice.model.StockCodeList;

public interface StockCodeListEntityRepository extends JpaRepository<StockCodeList, Integer> {
}