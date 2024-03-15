package yousang.backend.rest.controller.lotto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yousang.backend.rest.response.ApiResponse;
import yousang.backend.rest.service.lotto.LottoService;
import yousang.backend.rest.service.lotto.LottoUtilService;

@RestController
@RequestMapping("/lotto")
@Slf4j
public class LottoController {
    private final LottoService lottoService;
    private final LottoUtilService lottoUtilService;

    public LottoController(LottoService lottoService, LottoUtilService lottoUtilService) {
        this.lottoService = lottoService;
        this.lottoUtilService = lottoUtilService;
    }

    @GetMapping("/lotto-number/")
    public ResponseEntity<ApiResponse> getLottoNumber(@RequestParam int number) {
        ApiResponse response = lottoService.getLottoNumber(number);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/lotto-number/")
    public ResponseEntity<ApiResponse> putLottoNumber() {
        int latestDrwNo = lottoUtilService.getLottoLatestDrwNo("lotto");
        log.info("latestDrwNo: " + latestDrwNo);
        ApiResponse response = lottoService.putLottoNumber(latestDrwNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/predict-lotto-number/")
    public ResponseEntity<ApiResponse> getPredictLottoNumber(@RequestParam int number) {
        ApiResponse response = lottoService.getPredictLottoNumber(number);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/compare-lotto-number/")
    public ResponseEntity<ApiResponse> compareLottoNumber() {
        ApiResponse response = lottoService.compareLottoWinNumber();
        return ResponseEntity.ok(response);
    }
}