package prochat.yj_batchstockservice.service;


import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import prochat.yj_batchstockservice.model.StockDataEntity;
import prochat.yj_batchstockservice.model.StockEntity;
import prochat.yj_batchstockservice.repository.StockDataEntityRepository;
import prochat.yj_batchstockservice.repository.StockRepository;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CrawlingService {

    private static final String StockUrlforCookies = "https://finance.naver.com/sise/field_submit.naver?menu=market_sum&returnUrl=http%3A%2F%2Ffinance.naver.com%2Fsise%2Fsise_market_sum.naver&fieldIds=quant&fieldIds=market_sum&fieldIds=open_val&fieldIds=prev_quant&fieldIds=high_val&fieldIds=low_val";
    private static final String NaverStockUrl = "https://finance.naver.com/sise/sise_market_sum.naver";
    private final StockRepository stockRepository;
    private final StockDataEntityRepository stockDataEntityRepository;

    /**
     * 네이버에서 주식 정보를 크롤링하여 StockEntity 리스트로 반환하는 메서드
     * @return 크롤링한 주식 정보를 담은 StockEntity 리스트
     */
    public void StockData() {


        try {
            // 네이버 시가총액 페이지에 접속하여 쿠키 획득
            Connection.Response response = Jsoup.connect(StockUrlforCookies).method(Method.GET).execute();
            Map<String,String> cookies = response.cookies();

            // 코스피(0)와 코스닥(1)에 대해 주식 정보 크롤링
            for(int stockidx = 0; stockidx < 2; stockidx++) {
                // 마지막 페이지 번호 초기화
                int lastpage = 1;

                for(int i = 1; i <= lastpage; i++) {
                    // 네이버 주식 시가총액 페이지에 접속하여 크롤링 진행
                    response = Jsoup.connect(NaverStockUrl + "?sosok=" + stockidx + "&page=" + i).cookies(cookies).method(Method.GET).execute();
                    Document doc = response.parse();

                    // 첫 페이지일 경우, 마지막 페이지 번호 갱신
                    if(i == 1) {
                        Elements pagenavi = doc.select("table.Nnavi td.pgRR");
                        lastpage = Integer.parseInt(pagenavi.select("a").attr("href").substring(41));
                    }

                    // 각 행별로 주식 정보 추출하여 StockEntity 객체 생성 후 리스트에 추가
                    Elements rows = doc.select("div.box_type_l table.type_2 tbody tr");
                    for(Element row : rows) {
                        Elements stockinfo = row.select("td");

                        if(stockinfo.first().text().isEmpty()) continue;

                        StockEntity stockEntity = new StockEntity();

                        // 주식 정보 설정
                        stockEntity.setId(Integer.parseInt(stockinfo.get(0).text()));
                        String code = stockinfo.get(1).select("a").attr("href").substring(22);
                        stockEntity.setStockCode(code);
                        stockEntity.setStockName(stockinfo.get(1).text());
                        stockEntity.setStockIdx(stockidx);
                        stockEntity.setStockDate(java.sql.Date.valueOf(LocalDate.now()));
                        stockEntity.setEndPrice(Integer.parseInt(stockinfo.get(2).text().replaceAll("\\,","").trim()));
                        stockEntity.setFPrice(Integer.parseInt(stockinfo.get(3).text().replaceAll("\\,","").trim()));
                        stockEntity.setFRate(Float.parseFloat(stockinfo.get(4).text().replace("%","").trim()));
                        stockEntity.setStartPrice(Integer.parseInt(stockinfo.get(8).text().replaceAll("\\,","").trim()));
                        stockEntity.setHighPrice(Integer.parseInt(stockinfo.get(9).text().replaceAll("\\,","").trim()));
                        stockEntity.setLowPrice(Integer.parseInt(stockinfo.get(10).text().replaceAll("\\,","").trim()));

                        stockRepository.save(stockEntity);


                    }
                }
            }

             }
        catch(Exception e) {
            e.printStackTrace();
        }


    }



    public   List<StockDataEntity> crawlAndSaveStockData(String symbol, String timeframe, String count) {
        String url = "https://fchart.stock.naver.com/sise.nhn?symbol=" + symbol + "&timeframe=" + timeframe + "&count=" + count + "&requestType=0";

        List<StockDataEntity> stockDataEntities = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements items = document.select("item");

            for (Element item : items) {
                String[] dataParts = item.attr("data").split("\\|");
                StockDataEntity stockData = new StockDataEntity();
                stockData.setSymbol(symbol);

                stockData.setStockDate(dataParts[0]); // 종목명은 고정이거나 다른 방법으로 가져와야 합니다.
                stockData.setStartPrice(Integer.parseInt(dataParts[1]));
                stockData.setHighPrice(Integer.parseInt(dataParts[2]));
                stockData.setLowPrice(Integer.parseInt(dataParts[3]));
                stockData.setEndPrice(Integer.parseInt(dataParts[4]));
                stockData.setVolume(Long.parseLong(dataParts[5]));
                stockDataEntities.add(stockData);
                //stockDataEntityRepository.save(stockData);

            }
            return stockDataEntities;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockDataEntities;
    }


}