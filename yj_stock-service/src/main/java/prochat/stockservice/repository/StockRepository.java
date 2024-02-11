package prochat.stockservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prochat.stockservice.model.StockEntity;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Long> {
    @Query("SELECT s.stockCode FROM StockEntity s")
    List<String> findStockCode();

    Optional<StockEntity> findByStockCode(String stockCode);
    Page<StockEntity> findAllByStockCode(String stockCode, Pageable pageable);

    Page<StockEntity> findByStockNameContainingIgnoreCase(String searchKeyword, PageRequest pageRequest);

    Page<StockEntity> findByStockCodeContainingIgnoreCase(String searchKeyword, PageRequest pageRequest);

    Page<StockEntity> findByStockIdxAndStockNameContainingIgnoreCase(int i, String searchKeyword, PageRequest pageRequest);
}
