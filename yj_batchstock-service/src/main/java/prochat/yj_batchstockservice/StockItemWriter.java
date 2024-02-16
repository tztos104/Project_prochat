package prochat.yj_batchstockservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import prochat.yj_batchstockservice.model.StockDataEntity;
import prochat.yj_batchstockservice.model.StockEntity;
import prochat.yj_batchstockservice.repository.StockDataEntityRepository;
import prochat.yj_batchstockservice.repository.StockRepository;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class StockItemWriter implements ItemStreamWriter<List<StockDataEntity>> {
    private final StockDataEntityRepository stockDataEntityRepository;

    @Override
    public void write(Chunk<? extends List<StockDataEntity>> items) throws Exception {

        stockDataEntityRepository.saveAll(items.getItems().get(0));

    }
}
