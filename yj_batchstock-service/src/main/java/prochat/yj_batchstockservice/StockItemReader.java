package prochat.yj_batchstockservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Bean;
import prochat.yj_batchstockservice.model.StockEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class StockItemReader implements ItemReader<List<StockEntity>> {
   private static final  String stockUrlForCookies = "https://finance.naver.com/sise/field_submit.naver?menu=market_sum&returnUrl=http%3A%2F%2Ffinance.naver.com%2Fsise%2Fsise_market_sum.naver&fieldIds=quant&fieldIds=market_sum&fieldIds=open_val&fieldIds=prev_quant&fieldIds=high_val&fieldIds=low_val";
    private static final String naverStockUrl = "https://finance.naver.com/sise/sise_market_sum.naver";


    @Override
    public List<StockEntity> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        log.info("읽기~여기와1");

        // 네이버 시가총액 페이지에 접속하여 쿠키 획득
        Connection.Response response = Jsoup.connect(stockUrlForCookies).method(Connection.Method.GET).execute();
        Map<String, String> cookies = response.cookies();
        List<StockEntity> stockEntities = new ArrayList<>();

        // 코스피(0)와 코스닥(1)에 대해 주식 정보 크롤링
        for (int stockIdx = 0; stockIdx < 2; stockIdx++) {
            // 마지막 페이지 번호 초기화
            int lastPage = 1;

            for (int i = 1; i <= lastPage; i++) {
                // 네이버 주식 시가총액 페이지에 접속하여 크롤링 진행
                response = Jsoup.connect(naverStockUrl + "?sosok=" + stockIdx + "&page=" + i).cookies(cookies).method(Connection.Method.GET).execute();
                Document doc = response.parse();

                // 첫 페이지일 경우, 마지막 페이지 번호 갱신
                if (i == 1) {
                    Elements pageNavi = doc.select("table.Nnavi td.pgRR");
                    lastPage = Integer.parseInt(pageNavi.select("a").attr("href").substring(41));
                }

                // 각 행별로 주식 정보 추출하여 StockEntity 객체 생성 후 리스트에 추가
                Elements rows = doc.select("div.box_type_l table.type_2 tbody tr");
                for (Element row : rows) {
                    Elements stockInfo = row.select("td");

                    if (stockInfo.first().text().isEmpty()) continue;

                    StockEntity stockEntity = new StockEntity();

                    // 주식 정보 설정
                    stockEntity.setId(Integer.parseInt(stockInfo.get(0).text()));
                    String code = stockInfo.get(1).select("a").attr("href").substring(22);
                    stockEntity.setStockCode(code);
                    stockEntity.setStockName(stockInfo.get(1).text());
                    stockEntity.setStockIdx(stockIdx);
                    stockEntity.setStockDate(java.sql.Date.valueOf(LocalDate.now()));
                    stockEntity.setEndPrice(Integer.parseInt(stockInfo.get(2).text().replaceAll("\\,", "").trim()));
                    stockEntity.setFPrice(Integer.parseInt(stockInfo.get(3).text().replaceAll("\\,", "").trim()));
                    stockEntity.setFRate(Float.parseFloat(stockInfo.get(4).text().replace("%", "").trim()));
                    stockEntity.setStartPrice(Integer.parseInt(stockInfo.get(8).text().replaceAll("\\,", "").trim()));
                    stockEntity.setHighPrice(Integer.parseInt(stockInfo.get(9).text().replaceAll("\\,", "").trim()));
                    stockEntity.setLowPrice(Integer.parseInt(stockInfo.get(10).text().replaceAll("\\,", "").trim()));

                    stockEntities.add(stockEntity);
                }
            }
        }

        return stockEntities.isEmpty() ? Collections.emptyList() : stockEntities;

    }
}
