package prochat.yj_batchstockservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prochat.yj_batchstockservice.model.Stock;
import prochat.yj_batchstockservice.model.StockDataEntity;
import prochat.yj_batchstockservice.model.StockEntity;
import prochat.yj_batchstockservice.model.reponse.Response;
import prochat.yj_batchstockservice.model.reponse.StockListResponse;
import prochat.yj_batchstockservice.repository.StockDataEntityRepository;
import prochat.yj_batchstockservice.repository.StockRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Tag(name = "StockController", description = "주식 종목 관련 API")
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockRepository stockRepository;
    private final StockDataEntityRepository stockDataEntityRepository;
    //종목리스트 가져오기 코드만
    @Operation(summary = "종목코드 리스트 출력", description = "모든 종목 리스트 출력.")
    @GetMapping("/list")
    public Response<StockListResponse> getStockCodeList() {
        List<String> stockList = stockRepository.findStockCode();
        return Response.success(new StockListResponse(stockList));
    }
    //종목 리스트 보기
    @Operation(summary = "종목 리스트 출력", description = "모든 종목 리스트 출력.")
    @GetMapping
    public ResponseEntity<List<StockEntity>> getStockList() {
        List<StockEntity> stockList = stockRepository.findAll();
        return ResponseEntity.ok(stockList);
    }

 //종목상세보기
    @Operation(summary = "종목 상세 보기", description = "종목코드를 받으면 종목 상세보기")
    @GetMapping("/stocks/{stockCode}")
    public ResponseEntity<StockEntity> getStockDetails(@PathVariable String stockCode) {
        Optional<StockEntity> stockOptional = stockRepository.findByStockCode(stockCode);
        return stockOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//차트보기
@Operation(summary = "종목 차트 보기", description = "종목코드를 받으면 종목 차트보기")
    @GetMapping("/stocks/{symbol}")
    public ResponseEntity<List<StockDataEntity>> getStockChart(
            @PathVariable String symbol) {
        List<StockDataEntity> stockChart = stockDataEntityRepository.findBySymbol(symbol);
        return ResponseEntity.ok(stockChart);
    }

//정렬 보기
@Operation(summary = "종목 정렬", description = "5개 파라미터를 받아 검색 및 정렬 가능")
    @GetMapping("/stocks")
    public ResponseEntity<List<Stock>> getStockPageList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortField", defaultValue = "stockCode") String sortField,
            @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam(name = "searchType", defaultValue = "name") String searchType,
            @RequestParam(name = "searchKeyword", defaultValue = "") String searchKeyword) {

        PageRequest pageRequest = PageRequest.of(page, size, createSort(sortField, sortDirection));
        Page<StockEntity> stockPage = stockRepository.findAll(pageRequest);
        List<Stock> stockDTOList = stockPage.getContent().stream()
                .map(Stock::new)
                .collect(Collectors.toList());
        if ("name".equals(searchType)) {
            stockPage = stockRepository.findByStockNameContainingIgnoreCase(searchKeyword, pageRequest);
        } else if ("code".equals(searchType)) {
            stockPage = stockRepository.findByStockCodeContainingIgnoreCase(searchKeyword, pageRequest);
        } else if ("kospi".equals(searchType)) {
            stockPage = stockRepository.findByStockIdxAndStockNameContainingIgnoreCase(0, searchKeyword, pageRequest);
        } else if ("kosdaq".equals(searchType)) {
            stockPage = stockRepository.findByStockIdxAndStockNameContainingIgnoreCase(1, searchKeyword, pageRequest);
        } else {
            stockPage = stockRepository.findAll(pageRequest);
        }
        return ResponseEntity.ok(stockDTOList);
    }

    private Sort createSort(String sortField, String sortDirection) {
        if ("desc".equalsIgnoreCase(sortDirection)) {
            return Sort.by(Sort.Order.desc(sortField));
        } else {
            return Sort.by(Sort.Order.asc(sortField));
        }
    }
}
