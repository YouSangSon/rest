package yousang.backend.rest.controller.lotto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yousang.backend.rest.response.ApiResponse;
import yousang.backend.rest.service.lotto.LottoService;

@RestController
@RequestMapping("/lotto")
@Slf4j
public class LottoController {
    private final LottoService lottoService;

    public LottoController(LottoService lottoService) {
        this.lottoService = lottoService;
    }

    @GetMapping("/lotto-number/")
    public ResponseEntity<ApiResponse> getLottoNumber(@RequestParam int number) {
        ApiResponse response = lottoService.getLottoNumber(number);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/lotto-number/")
    public ResponseEntity<ApiResponse> putLottoNumber() {
        int latestDrwNo = lottoService.getLottoLatestDrwNo("lotto");
        log.info("latestDrwNo: " + latestDrwNo);
        ApiResponse response = lottoService.putLottoNumber(latestDrwNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/predict-lotto-number/")
    public ResponseEntity<ApiResponse> getPredictLottoNumber(@RequestParam int number) {
        ApiResponse response = lottoService.getPredictLottoNumber(number);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/annuity-lotto-number/")
    public ResponseEntity<ApiResponse> getAnnuityLottoNumber(@RequestParam int number) {
        ApiResponse response = lottoService.getAnnuityLottoNumber(number);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/annuity-lotto-number/")
    public ResponseEntity<ApiResponse> putAnnuityLottoNumber() {
        int latestDrwNo = lottoService.getLottoLatestDrwNo("annuity");
        ApiResponse response = lottoService.putAnnuityLottoNumber(latestDrwNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/predict-annuity-lotto-number/")
    public ResponseEntity<ApiResponse> getPredictAnnuityLottoNumber(@RequestParam int number) {
        ApiResponse response = lottoService.getPredictAnnuityLottoNumber(number);
        return ResponseEntity.ok(response);
    }
}