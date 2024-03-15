package yousang.backend.rest.service.lotto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yousang.backend.rest.dto.lotto.AnnuityLottoDTO;
import yousang.backend.rest.dto.lotto.LottoDTO;
import yousang.backend.rest.dto.lotto.PredictAnnuityLottoDTO;
import yousang.backend.rest.dto.lotto.PredictLottoDTO;
import yousang.backend.rest.entity.lotto.AnnuityLottoResult;
import yousang.backend.rest.entity.lotto.LottoResult;
import yousang.backend.rest.entity.lotto.PredictAnnuityLottoResult;
import yousang.backend.rest.entity.lotto.PredictLottoResult;
import yousang.backend.rest.repository.lotto.LottoPredictRepository;
import yousang.backend.rest.repository.lotto.LottoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public record LottoUtilService(LottoRepository lottoRepository, LottoPredictRepository lottoPredictRepository) {

    public int getLottoLatestDrwNo(String mode) {
        if (Objects.equals(mode, "annuity")) {
            PredictLottoResult result = lottoPredictRepository.findTopByOrderByPredictDrwNoDesc().orElse(null);
            return result != null ? result.getPredictDrwNo() + 1 : 0;
        } else if (Objects.equals(mode, "lotto")) {
            LottoResult result = lottoRepository.findTopByOrderByDrwNoDesc().orElse(null);
            return result != null ? result.getDrwNo() + 1 : 0;
        }
        return 0;
    }

    public static LottoDTO lottoFromEntityToDTO(LottoResult lottoResult) {
        return new LottoDTO(
                lottoResult.getDrwNo(),
                lottoResult.getDrwNoDate().toString(),
                lottoResult.getDrwtNo1(),
                lottoResult.getDrwtNo2(),
                lottoResult.getDrwtNo3(),
                lottoResult.getDrwtNo4(),
                lottoResult.getDrwtNo5(),
                lottoResult.getDrwtNo6(),
                lottoResult.getBnusNo(),
                lottoResult.getFirstAccumamnt(),
                lottoResult.getFirstPrzwnerCo(),
                lottoResult.getFirstWinamnt(),
                lottoResult.getTotSellamnt(),
                lottoResult.getReturnValue());
    }

    public static List<PredictLottoDTO> predictLottoFromEntityToDTO(List<PredictLottoResult> lottoPredictResults) {
        List<PredictLottoDTO> predictLottoNumberResults = new ArrayList<>();

        for (PredictLottoResult lottoPredictResult : lottoPredictResults) {
            PredictLottoDTO predictLottoNumberResult = new PredictLottoDTO(
                    lottoPredictResult.getPredictDrwNo(),
                    lottoPredictResult.getDrwtNo1(),
                    lottoPredictResult.getDrwtNo2(),
                    lottoPredictResult.getDrwtNo3(),
                    lottoPredictResult.getDrwtNo4(),
                    lottoPredictResult.getDrwtNo5(),
                    lottoPredictResult.getDrwtNo6(),
                    lottoPredictResult.getPredictPer(),
                    lottoPredictResult.getPredictEpoch());
            predictLottoNumberResults.add(predictLottoNumberResult);
        }

        return predictLottoNumberResults;
    }

    public static AnnuityLottoDTO annuityLottoFromEntityToDTO(AnnuityLottoResult annuityLottoResult) {
        return new AnnuityLottoDTO(
                annuityLottoResult.getDrwNo(),
                annuityLottoResult.getDrwNoDate().toString(),
                annuityLottoResult.getDrwtNo1(),
                annuityLottoResult.getDrwtNo2(),
                annuityLottoResult.getDrwtNo3(),
                annuityLottoResult.getDrwtNo4(),
                annuityLottoResult.getDrwtNo5(),
                annuityLottoResult.getDrwtNo6(),
                annuityLottoResult.getBonusNo1(),
                annuityLottoResult.getBonusNo2(),
                annuityLottoResult.getBonusNo3(),
                annuityLottoResult.getBonusNo4(),
                annuityLottoResult.getBonusNo5(),
                annuityLottoResult.getBonusNo6());
    }

    public static List<PredictAnnuityLottoDTO> predictAnnuityLottoFromEntityToDTO(List<PredictAnnuityLottoResult> predictAnnuityLottoResults) {
        List<PredictAnnuityLottoDTO> predictAnnuityLottoNumberResults = new ArrayList<>();

        for (PredictAnnuityLottoResult predictAnnuityLottoResult : predictAnnuityLottoResults) {
            PredictAnnuityLottoDTO predictAnnuityLottoNumberResult = new PredictAnnuityLottoDTO(
                    predictAnnuityLottoResult.getPredictDrwNo(),
                    predictAnnuityLottoResult.getDrwtNo1(),
                    predictAnnuityLottoResult.getDrwtNo2(),
                    predictAnnuityLottoResult.getDrwtNo3(),
                    predictAnnuityLottoResult.getDrwtNo4(),
                    predictAnnuityLottoResult.getDrwtNo5(),
                    predictAnnuityLottoResult.getDrwtNo6(),
                    predictAnnuityLottoResult.getPredictPer(),
                    predictAnnuityLottoResult.getPredictEpoch());
            predictAnnuityLottoNumberResults.add(predictAnnuityLottoNumberResult);
        }

        return predictAnnuityLottoNumberResults;
    }
}
