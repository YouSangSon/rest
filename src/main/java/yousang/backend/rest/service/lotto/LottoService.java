package yousang.backend.rest.service.lotto;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yousang.backend.rest.entity.lotto.PredictLottoResult;
import yousang.backend.rest.entity.lotto.LottoResult;
import yousang.backend.rest.repository.lotto.LottoPredictRepository;
import yousang.backend.rest.repository.lotto.LottoRepository;
import yousang.backend.rest.response.ApiResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
            return new ApiResponse(HttpStatus.OK.value(), "Success",
                    LottoUtilService.lottoFromEntityToDTO(lottoResult.get()));
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
}
