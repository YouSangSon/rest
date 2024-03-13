package yousang.backend.rest.repository.lotto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yousang.backend.rest.entity.lotto.LottoResult;

import java.util.Optional;

@Repository
public interface LottoRepository extends JpaRepository<LottoResult, Long> {
    Optional<LottoResult> findTopByOrderByDrwNoDesc();

    Optional<LottoResult> findByDrwNo(int drwNo);
}
