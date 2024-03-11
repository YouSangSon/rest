package yousang.backend.rest.repository.lotto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yousang.backend.rest.entity.lotto.PredictLottoResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface LottoPredictRepository extends JpaRepository<PredictLottoResult, Long> {
    List<PredictLottoResult> findAllByPredictDrwNo(int predictDrwNo);

    List<PredictLottoResult> findAllByPredictPerIsNull();

    Optional<PredictLottoResult> findTopByOrderByPredictDrwNoDesc();
}
