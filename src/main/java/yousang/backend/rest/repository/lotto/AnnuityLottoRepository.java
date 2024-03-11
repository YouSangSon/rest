package yousang.backend.rest.repository.lotto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yousang.backend.rest.entity.lotto.AnnuityLottoResult;
import yousang.backend.rest.entity.lotto.PredictLottoResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnuityLottoRepository extends JpaRepository<AnnuityLottoResult, Long> {
    Optional<AnnuityLottoResult> findTopByOrderByDrwNoDesc();

    Optional<AnnuityLottoResult> findByDrwNo(int drwNo);
}