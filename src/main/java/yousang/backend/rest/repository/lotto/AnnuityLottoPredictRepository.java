package yousang.backend.rest.repository.lotto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yousang.backend.rest.entity.lotto.PredictAnnuityLottoResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnuityLottoPredictRepository extends JpaRepository<PredictAnnuityLottoResult, Long> {
    List<PredictAnnuityLottoResult> findAllByPredictDrwNo(int predictDrwNo);

    List<PredictAnnuityLottoResult> findAllByPredictPerIsNull();

    Optional<PredictAnnuityLottoResult> findTopByOrderByPredictDrwNoDesc();
}