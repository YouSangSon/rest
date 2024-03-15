package yousang.backend.rest.controller.lotto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import yousang.backend.rest.response.ApiResponse;
import yousang.backend.rest.service.lotto.AnnuityLottoService;
import yousang.backend.rest.service.lotto.LottoUtilService;

@RestController
@RequestMapping("/annuity-lotto")
@Slf4j
public class AnnuityLottoController {
    private final AnnuityLottoService annuityLottoService;
    private final LottoUtilService lottoUtilService;

    public AnnuityLottoController(AnnuityLottoService annuityLottoService, LottoUtilService lottoUtilService) {
        this.annuityLottoService = annuityLottoService;
        this.lottoUtilService = lottoUtilService;
    }

    @GetMapping("/annuity-lotto-number/")
    public ResponseEntity<ApiResponse> getAnnuityLottoNumber(@RequestParam int number) {
        ApiResponse response = annuityLottoService.getAnnuityLottoNumber(number);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/annuity-lotto-number/")
    public ResponseEntity<ApiResponse> putAnnuityLottoNumber() {
        int latestDrwNo = lottoUtilService.getLottoLatestDrwNo("annuity");
        log.info("latestDrwNo: " + latestDrwNo);
        ApiResponse response = annuityLottoService.putAnnuityLottoNumber(latestDrwNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/predict-annuity-lotto-number/")
    public ResponseEntity<ApiResponse> getPredictAnnuityLottoNumber(@RequestParam int number) {
        ApiResponse response = annuityLottoService.getPredictAnnuityLottoNumber(number);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/compare-annuity-lotto-number/")
    public ResponseEntity<ApiResponse> compareAnnuityLottoNumber() {
        ApiResponse response = annuityLottoService.compareAnnuityLottoWinNumber();
        return ResponseEntity.ok(response);
    }
}
