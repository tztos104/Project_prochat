package prochat.stockservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prochat.stockservice.ResponseDTO;
import prochat.stockservice.model.StockDataRequest;
import prochat.stockservice.model.StockEntity;
import prochat.stockservice.model.reponse.Response;
import prochat.stockservice.model.reponse.StockDataResponse;
import prochat.stockservice.repository.StockDataEntityRepository;
import prochat.stockservice.repository.StockRepository;
import prochat.stockservice.service.CrawlingService;

import java.util.List;

@RestController
@RequestMapping("/crawling")
public class CrawlingController {
    @Autowired
    private CrawlingService service;
    @Autowired
    private StockRepository stockRepository;
  /*  @GetMapping("/stockinfo")
    public String Stockinfo() {
        
        String str = service.StockData();
        return str;
    }*/
    
    @GetMapping("/stockinfojson")
    public Response<?> CStock2() {

        service.StockData();

        return Response.success();
    }

    @GetMapping("/stockData")
    public Response<?> stockData(@RequestBody StockDataRequest request) {
        List<String> stockCode = stockRepository.findStockCode();
        for(  String stockCode1  : stockCode) {
            service.crawlAndSaveStockData(stockCode1, request.getTimeframe(), request.getCount());
        }
        return Response.success();
    }








//
//     // 3-1. 주식 목록 보여주기 API
//
//
//    // 3-2. 한 종목 상세보기 API
//    @GetMapping("/{id}")
//    public StockEntity getStockDetails(@PathVariable int id) {
//        return stockService.getStockDetails(id);
//    }
//
//    // 3-3. 주가 차트 보여주기 API (예: 최근 1개월)
//    @GetMapping("/{id}/chart")
//    public StockChartDTO getStockChart(@PathVariable int id,
//                                       @RequestParam String timeframe) {
//        return stockService.getStockChart(id, timeframe);
//    }
//
//    // 4. 배치 작업을 통한 주기적 데이터 업데이트
//    @Scheduled(cron = "0 0 1 * * ?") // 매일 1시에 실행
//    public void updateStockData() {
//        stockService.updateStockData();
//    }
}
