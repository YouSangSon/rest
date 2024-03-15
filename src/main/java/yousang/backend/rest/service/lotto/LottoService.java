package yousang.backend.rest.service.lotto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yousang.backend.rest.entity.lotto.LottoResult;
import yousang.backend.rest.entity.lotto.PredictLottoResult;
import yousang.backend.rest.repository.lotto.LottoPredictRepository;
import yousang.backend.rest.repository.lotto.LottoRepository;
import yousang.backend.rest.response.ApiResponse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LottoService {
    private final LottoRepository lottoRepository;
    private final LottoPredictRepository lottoPredictRepository;

    public LottoService(LottoRepository lottoRepository, LottoPredictRepository lottoPredictRepository) {
        this.lottoRepository = lottoRepository;
        this.lottoPredictRepository = lottoPredictRepository;
    }

    public ApiResponse getLottoNumber(int drwNo) {
        try {
            Optional<LottoResult> lottoResult = lottoRepository.findByDrwNo(drwNo);
            return lottoResult.map(result -> new ApiResponse(HttpStatus.OK.value(), "Success",
                    LottoUtilService.lottoFromEntityToDTO(result))).orElseGet(() -> new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Invalid drwNo", null));
        } catch (Exception e) {
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage());
        }
    }

    public ApiResponse putLottoNumber(int drwNo) {
        try {
            if (drwNo == 0) {
                return new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Invalid drwNo", null);
            }

            String url = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" + drwNo;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
            headers.setContentType(MediaType.TEXT_HTML);

            HttpEntity<String> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            String body = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            LottoResult lottoResult = objectMapper.readValue(body, LottoResult.class);

            if (lottoResult.getDrwNo() == 0) {
                return new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Invalid drwNo", null);
            }

            lottoRepository.save(lottoResult);

            return new ApiResponse(HttpStatus.OK.value(), "Success", lottoResult.getDrwNo());
        } catch (Exception e) {
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage(), null);
        }
    }

    public ApiResponse getPredictLottoNumber(int drwNo) {
        try {
            List<PredictLottoResult> lottoPredictResults = lottoPredictRepository.findAllByPredictDrwNo(drwNo);
            return new ApiResponse(HttpStatus.OK.value(), "Success",
                    LottoUtilService.predictLottoFromEntityToDTO(lottoPredictResults));
        } catch (Exception e) {
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage(), null);
        }
    }

    public ApiResponse compareLottoWinNumber() {
        try {
            List<PredictLottoResult> predictLottoResults = lottoPredictRepository.findAllByPredictPerIsNull();

            if (!predictLottoResults.isEmpty()) {
                Set<Integer> drwNos = new HashSet<>();

                for (PredictLottoResult predictLottoResult : predictLottoResults) {
                    drwNos.add(predictLottoResult.getPredictDrwNo());
                }

                List<LottoResult> lottoResults = lottoRepository.findAllByDrwNoIn(drwNos);

                for (PredictLottoResult predictLottoResult : predictLottoResults) {
                    for (LottoResult lottoResult : lottoResults) {
                        if (predictLottoResult.getPredictDrwNo() == lottoResult.getDrwNo()) {
                            Set<Integer> lottoPredictNumbers = getIntegers(predictLottoResult);
                            Set<Integer> lottoNumbers = Set.of(
                                    lottoResult.getDrwtNo1(),
                                    lottoResult.getDrwtNo2(),
                                    lottoResult.getDrwtNo3(),
                                    lottoResult.getDrwtNo4(),
                                    lottoResult.getDrwtNo5(),
                                    lottoResult.getDrwtNo6()
                            );

                            Set<Integer> matchingNumbers = lottoNumbers.stream().filter(lottoPredictNumbers::contains).collect(Collectors.toSet());

                            double predictPer = (double) matchingNumbers.size() / 6 * 100;

                            PredictLottoResult newPredictLottoResult = new PredictLottoResult();

                            newPredictLottoResult.setPredictDrwNo(predictLottoResult.getPredictDrwNo());
                            newPredictLottoResult.setDrwtNo1(predictLottoResult.getDrwtNo1());
                            newPredictLottoResult.setDrwtNo2(predictLottoResult.getDrwtNo2());
                            newPredictLottoResult.setDrwtNo3(predictLottoResult.getDrwtNo3());
                            newPredictLottoResult.setDrwtNo4(predictLottoResult.getDrwtNo4());
                            newPredictLottoResult.setDrwtNo5(predictLottoResult.getDrwtNo5());
                            newPredictLottoResult.setDrwtNo6(predictLottoResult.getDrwtNo6());
                            newPredictLottoResult.setPredictPer(predictLottoResult.getPredictPer());
                            newPredictLottoResult.setPredictPer(BigDecimal.valueOf(predictPer));

                            lottoPredictRepository.save(newPredictLottoResult);
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

    private static Set<Integer> getIntegers(PredictLottoResult predictLottoResult) {
        Set<Integer> lottoPredictNumbers;
        if (predictLottoResult.getPredictEpoch() != null) {
            lottoPredictNumbers = Set.of(
                    predictLottoResult.getId().intValue(),
                    predictLottoResult.getDrwtNo1(),
                    predictLottoResult.getDrwtNo2(),
                    predictLottoResult.getDrwtNo3(),
                    predictLottoResult.getDrwtNo4(),
                    predictLottoResult.getDrwtNo5(),
                    predictLottoResult.getDrwtNo6(),
                    predictLottoResult.getPredictEpoch().intValue()
            );
        } else {
            lottoPredictNumbers = Set.of(
                    predictLottoResult.getId().intValue(),
                    predictLottoResult.getDrwtNo1(),
                    predictLottoResult.getDrwtNo2(),
                    predictLottoResult.getDrwtNo3(),
                    predictLottoResult.getDrwtNo4(),
                    predictLottoResult.getDrwtNo5(),
                    predictLottoResult.getDrwtNo6(),
                    0
            );
        }
        return lottoPredictNumbers;
    }
}
