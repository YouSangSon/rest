package yousang.backend.rest.service.lotto;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import yousang.backend.rest.entity.lotto.AnnuityLottoResult;
import yousang.backend.rest.entity.lotto.PredictAnnuityLottoResult;
import yousang.backend.rest.repository.lotto.AnnuityLottoPredictRepository;
import yousang.backend.rest.repository.lotto.AnnuityLottoRepository;
import yousang.backend.rest.response.ApiResponse;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnnuityLottoService {
    private final AnnuityLottoRepository annuityLottoRepository;
    private final AnnuityLottoPredictRepository annuityLottoPredictRepository;

    public AnnuityLottoService(AnnuityLottoRepository annuityLottoRepository, AnnuityLottoPredictRepository annuityLottoPredictRepository) {
        this.annuityLottoRepository = annuityLottoRepository;
        this.annuityLottoPredictRepository = annuityLottoPredictRepository;
    }

    public ApiResponse getAnnuityLottoNumber(int drwNo) {
        try {
            Optional<AnnuityLottoResult> annuityLottoResult = annuityLottoRepository.findByDrwNo(drwNo);
            return annuityLottoResult.map(lottoResult -> new ApiResponse(HttpStatus.OK.value(), "Success",
                    LottoUtilService.annuityLottoFromEntityToDTO(lottoResult))).orElseGet(() -> new ApiResponse(HttpStatus.NOT_FOUND.value(), "Not Found"));
        } catch (Exception e) {
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage());
        }
    }

    public ApiResponse putAnnuityLottoNumber(int drwNo) {
        try {
            String url = "https://dhlottery.co.kr/gameResult.do?method=win720";
            Document response = Jsoup.connect(url).data("Round", String.valueOf(drwNo)).post();

            Element winResultTag = response.selectFirst("div.win_result");

            assert winResultTag != null;
            String roundNumText = Objects.requireNonNull(winResultTag.selectFirst("strong")).text().replace("|", "").replace("회", "");
            int roundNum = Integer.parseInt(roundNumText);

            String drawDateText = Objects.requireNonNull(winResultTag.selectFirst("p.desc")).text();
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

            Element divGroup = win720NumTags.getFirst().selectFirst("div.group");
            assert divGroup != null;
            int numGroup = Integer.parseInt(divGroup.select("span").get(1).text());

            List<Integer> win720Nums = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                Element numElement = win720NumTags.getFirst().selectFirst("span.num.al720_color" + i + ".large span");
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
            List<PredictAnnuityLottoResult> predictAnnuityLottoResult = annuityLottoPredictRepository.findAllByPredictDrwNo(drwNo);
            return new ApiResponse(HttpStatus.OK.value(), "Success",
                    LottoUtilService.predictAnnuityLottoFromEntityToDTO(predictAnnuityLottoResult));
        } catch (Exception e) {
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage(), null);
        }
    }

    public ApiResponse compareAnnuityLottoWinNumber() {
        try {
            List<PredictAnnuityLottoResult> predictAnnuityLottoResults = annuityLottoPredictRepository.findAllByPredictPerIsNull();

            if (!predictAnnuityLottoResults.isEmpty()) {
                Set<Integer> drwNos = new HashSet<>();

                for (PredictAnnuityLottoResult predictAnnuityLottoResult : predictAnnuityLottoResults) {
                    drwNos.add(predictAnnuityLottoResult.getPredictDrwNo());
                }

                List<AnnuityLottoResult> annuityLottoResults = annuityLottoRepository.findAllByDrwNoIn(drwNos);

                for (PredictAnnuityLottoResult predictAnnuityLottoResult : predictAnnuityLottoResults) {
                    for (AnnuityLottoResult annuityLottoResult : annuityLottoResults) {
                        if (predictAnnuityLottoResult.getPredictDrwNo() == annuityLottoResult.getDrwNo()) {
                            List<Integer> annuityPredictNumbers = getIntegers(predictAnnuityLottoResult);
                            List<Integer> annuityNumbers = List.of(
                                    annuityLottoResult.getDrwtNo1(),
                                    annuityLottoResult.getDrwtNo2(),
                                    annuityLottoResult.getDrwtNo3(),
                                    annuityLottoResult.getDrwtNo4(),
                                    annuityLottoResult.getDrwtNo5(),
                                    annuityLottoResult.getDrwtNo6()
                            );

                            Set<Integer> matchingNumbers = annuityNumbers.stream().filter(annuityPredictNumbers::contains).collect(Collectors.toSet());

                            double predictPer = (double) matchingNumbers.size() / 6 * 100;

                            PredictAnnuityLottoResult newPredictAnnuityLottoResult = PredictAnnuityLottoResult.builder()
                                    .id(predictAnnuityLottoResult.getId())
                                    .predictDrwNo(predictAnnuityLottoResult.getPredictDrwNo())
                                    .drwtNo1(predictAnnuityLottoResult.getDrwtNo1())
                                    .drwtNo2(predictAnnuityLottoResult.getDrwtNo2())
                                    .drwtNo3(predictAnnuityLottoResult.getDrwtNo3())
                                    .drwtNo4(predictAnnuityLottoResult.getDrwtNo4())
                                    .drwtNo5(predictAnnuityLottoResult.getDrwtNo5())
                                    .drwtNo6(predictAnnuityLottoResult.getDrwtNo6())
                                    .predictPer(BigDecimal.valueOf(predictPer))
                                    .predictEpoch(predictAnnuityLottoResult.getPredictEpoch())
                                    .build();

                            annuityLottoPredictRepository.save(newPredictAnnuityLottoResult);
                        }
                    }
                }
            }
            return new ApiResponse(HttpStatus.OK.value(), "Success");
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", null);
        }
    }

    private static List<Integer> getIntegers(PredictAnnuityLottoResult predictAnnuityLottoResult) {
        List<Integer> annuityLottoPredictNumbers;
        if (predictAnnuityLottoResult.getPredictEpoch() != null) {
            annuityLottoPredictNumbers = List.of(
                    predictAnnuityLottoResult.getId().intValue(),
                    predictAnnuityLottoResult.getDrwtNo1(),
                    predictAnnuityLottoResult.getDrwtNo2(),
                    predictAnnuityLottoResult.getDrwtNo3(),
                    predictAnnuityLottoResult.getDrwtNo4(),
                    predictAnnuityLottoResult.getDrwtNo5(),
                    predictAnnuityLottoResult.getDrwtNo6(),
                    predictAnnuityLottoResult.getPredictEpoch().intValue()
            );
        } else {
            annuityLottoPredictNumbers = List.of(
                    predictAnnuityLottoResult.getId().intValue(),
                    predictAnnuityLottoResult.getDrwtNo1(),
                    predictAnnuityLottoResult.getDrwtNo2(),
                    predictAnnuityLottoResult.getDrwtNo3(),
                    predictAnnuityLottoResult.getDrwtNo4(),
                    predictAnnuityLottoResult.getDrwtNo5(),
                    predictAnnuityLottoResult.getDrwtNo6(),
                    0
            );
        }
        return annuityLottoPredictNumbers;
    }
}
