package prochat.yj_batchstockservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prochat.yj_batchstockservice.repository.StockDataEntityRepository;
import prochat.yj_batchstockservice.service.EconomicIndicatorService;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/economic") // 요청 URL을 지정해주는 어노테이션
public class EconomicController {


    private final EconomicIndicatorService economicIndicatorService;
    private final StockDataEntityRepository stockDataEntityRepository;

    @GetMapping("/moving-average")
    public ResponseEntity<List<Double>> getMovingAverage(@RequestParam List<Double> closingPrices, @RequestParam int period) {

        List<Double> movingAverages = EconomicIndicatorService.movingAverage(closingPrices, period);
        return new ResponseEntity<>(movingAverages, HttpStatus.OK);
    }

    @GetMapping("/bollinger-upper-band")
    public ResponseEntity<List<Double>> getBollingerUpperBand(@RequestParam List<Double> closingPrices, @RequestParam int period, @RequestParam double multiplier) {
        List<Double> upperBands = EconomicIndicatorService.BollingerUpperBand(closingPrices, period, multiplier);
        return new ResponseEntity<>(upperBands, HttpStatus.OK);
    }

    @GetMapping("/macd")
    public ResponseEntity<List<Double>> getMACD(@RequestParam List<Double> closingPrices, @RequestParam int shortPeriod, @RequestParam int longPeriod, @RequestParam int signalPeriod) {
        List<Double> macdValues = EconomicIndicatorService.calculateMACD(closingPrices, shortPeriod, longPeriod, signalPeriod);
        return new ResponseEntity<>(macdValues, HttpStatus.OK);
    }
}
