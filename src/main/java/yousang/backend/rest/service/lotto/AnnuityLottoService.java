package yousang.backend.rest.service.lotto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import yousang.backend.rest.entity.lotto.AnnuityLottoResult;
import yousang.backend.rest.entity.lotto.PredictAnnuityLottoResult;
import yousang.backend.rest.repository.lotto.AnnuityLottoPredictRepository;
import yousang.backend.rest.repository.lotto.AnnuityLottoRepository;
import yousang.backend.rest.response.ApiResponse;

@Service
@Slf4j
public class AnnuityLottoService {
    private final AnnuityLottoRepository annuityLottoRepository;
    private final AnnuityLottoPredictRepository annuityLottoPredictRepository;

    public AnnuityLottoService(AnnuityLottoRepository annuityLottoRepository,
            AnnuityLottoPredictRepository annuityLottoPredictRepository) {
        this.annuityLottoRepository = annuityLottoRepository;
        this.annuityLottoPredictRepository = annuityLottoPredictRepository;
    }

    public ApiResponse getAnnuityLottoNumber(int drwNo) {
        try {
            Optional<AnnuityLottoResult> annuityLottoResult = annuityLottoRepository.findByDrwNo(drwNo);
            return new ApiResponse(HttpStatus.OK.value(), "Success",
                    LottoUtilService.annuitylottoFromEntityToDTO(annuityLottoResult.get()));
        } catch (Exception e) {
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage());
        }
    }

    public ApiResponse putAnnuityLottoNumber(int drwNo) {
        try {
            String url = "https://dhlottery.co.kr/gameResult.do?method=win720";
            Document response = Jsoup.connect(url).data("Round", String.valueOf(drwNo)).post();

            Element winResultTag = response.selectFirst("div.win_result");

            String roundNumText = winResultTag.selectFirst("strong").text().replace("|", "").replace("회", "");
            Integer roundNum = Integer.parseInt(roundNumText);

            String drawDateText = winResultTag.selectFirst("p.desc").text();
            String cleanedDateText = drawDateText.replace("년", "-")
                    .replace("월", "-")
                    .replace("일 추첨", "")
                    .replace("(", "")
                    .replace(")", "")
                    .replace(" ", "")
                    .trim();
            log.info("cleanedDateText: " + cleanedDateText);

            Date drawDate = Date.valueOf(LocalDate.parse(cleanedDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            Elements win720NumTags = winResultTag.select("div.win720_num");

            Element divGroup = win720NumTags.get(0).selectFirst("div.group");
            Integer numGroup = Integer.parseInt(divGroup.select("span").get(1).text());

            List<Integer> win720Nums = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                Element numElement = win720NumTags.get(0).selectFirst("span.num.al720_color" + i + ".large span");
                if (numElement != null) {
                    Integer num = Integer.parseInt(numElement.text());
                    win720Nums.add(num);
                }
            }

            List<Integer> bonusNums = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                Element numElement = win720NumTags.get(1).selectFirst("span.num.al720_color" + i + ".large span");
                if (numElement != null) {
                    Integer num = Integer.parseInt(numElement.text());
                    bonusNums.add(num);
                }
            }

            AnnuityLottoResult annuityLottoResult = new AnnuityLottoResult();
            annuityLottoResult.setDrwNo(roundNum);
            annuityLottoResult.setDrwNoDate(drawDate);
            annuityLottoResult.setGroup(numGroup);
            annuityLottoResult.setDrwtNo1(win720Nums.get(0));
            annuityLottoResult.setDrwtNo2(win720Nums.get(1));
            annuityLottoResult.setDrwtNo3(win720Nums.get(2));
            annuityLottoResult.setDrwtNo4(win720Nums.get(3));
            annuityLottoResult.setDrwtNo5(win720Nums.get(4));
            annuityLottoResult.setDrwtNo6(win720Nums.get(5));
            annuityLottoResult.setBonusNo1(bonusNums.get(0));
            annuityLottoResult.setBonusNo2(bonusNums.get(1));
            annuityLottoResult.setBonusNo3(bonusNums.get(2));
            annuityLottoResult.setBonusNo4(bonusNums.get(3));
            annuityLottoResult.setBonusNo5(bonusNums.get(4));
            annuityLottoResult.setBonusNo6(bonusNums.get(5));

            annuityLottoRepository.save(annuityLottoResult);

            return new ApiResponse(HttpStatus.OK.value(), "Success", annuityLottoResult);
        } catch (Exception e) {
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage(), null);
        }
    }

    public ApiResponse getPredictAnnuityLottoNumber(int drwNo) {
        try {
            List<PredictAnnuityLottoResult> predictAnnuityLottoResult = annuityLottoPredictRepository
                    .findAllByPredictDrwNo(drwNo);
            return new ApiResponse(HttpStatus.OK.value(), "Success",
                    LottoUtilService.predictAnnuityLottoFromEntityToDTO(predictAnnuityLottoResult));
        } catch (Exception e) {
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage(), null);
        }
    }
}
