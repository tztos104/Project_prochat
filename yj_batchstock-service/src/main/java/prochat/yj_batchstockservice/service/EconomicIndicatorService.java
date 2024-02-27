package prochat.yj_batchstockservice.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prochat.yj_batchstockservice.model.StockDataEntity;
import prochat.yj_batchstockservice.repository.StockDataEntityRepository;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class EconomicIndicatorService {

    private final StockDataEntityRepository stockDataEntityRepository;

    private List<Double> extractEndPrices(String symbol) {
        List<StockDataEntity> endPriceBySymbol = stockDataEntityRepository.findEndPriceBySymbol(symbol);
        List<Double> endPrices = new ArrayList<>();

        for (StockDataEntity stockData : endPriceBySymbol) {
            endPrices.add((double) stockData.getEndPrice());
        }

        return endPrices;
    }
    // 종가를 입력으로 받아 이동평균 계산
    public static List<Double> movingAverage(List<Double> closingPrices, int period) {
        List<Double> movingAverages = new ArrayList<>();

        // 이동평균 계산
        for (int i = period - 1; i < closingPrices.size(); i++) {
            double sum = 0;
            for (int j = 0; j < period; j++) {
                sum += closingPrices.get(i - j);
            }
            movingAverages.add(sum / period);
        }

        return movingAverages;
    }



    // 상단 볼린저 밴드 계산
    public static List<Double> BollingerUpperBand(List<Double> closingPrices, int period, double multiplier) {
        List<Double> upperBands = new ArrayList<>();

        // 이동평균 계산
        List<Double> movingAverages = movingAverage(closingPrices, period);

        // 상단 볼린저 밴드 계산
        for (int i = period - 1; i < closingPrices.size(); i++) {
            double standardDeviation = standardDeviation(closingPrices.subList(i - period + 1, i + 1), movingAverages.get(i - period + 1));
            upperBands.add(movingAverages.get(i) + multiplier * standardDeviation);
        }

        return upperBands;
    }
    // 주어진 데이터의 표준편차 계산
    private static double standardDeviation(List<Double> prices, double mean) {
        double sumSquaredDifference = 0;

        for (double price : prices) {
            sumSquaredDifference += Math.pow(price - mean, 2);
        }

        return Math.sqrt(sumSquaredDifference / prices.size());
    }

    // MACD 계산
    public static List<Double> calculateMACD(List<Double> closingPrices, int shortPeriod, int longPeriod, int signalPeriod) {
        List<Double> macdValues = new ArrayList<>();

        // 단기 및 장기 지수 이동평균 계산
        List<Double> shortTermEMA = calculateEMA(closingPrices, shortPeriod);
        List<Double> longTermEMA = calculateEMA(closingPrices, longPeriod);

        // MACD 값 계산
        for (int i = longPeriod - 1; i < closingPrices.size(); i++) {
            macdValues.add(shortTermEMA.get(i) - longTermEMA.get(i));
        }

        // 신호선 계산
        List<Double> signalLine = movingAverage(macdValues, signalPeriod);

        return macdValues.subList(signalPeriod - 1, macdValues.size());
    }

    // 지수 이동평균 (EMA) 계산
    private static List<Double> calculateEMA(List<Double> closingPrices, int period) {
        List<Double> emaValues = new ArrayList<>();

        double multiplier = 2.0 / (period + 1);
        emaValues.add(closingPrices.get(0));

        // EMA 계산
        for (int i = 1; i < closingPrices.size(); i++) {
            double ema = (closingPrices.get(i) - emaValues.get(i - 1)) * multiplier + emaValues.get(i - 1);
            emaValues.add(ema);
        }

        return emaValues;
    }


}
