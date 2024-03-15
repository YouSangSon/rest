package yousang.backend.rest.repository.lotto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yousang.backend.rest.entity.lotto.AnnuityLottoResult;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AnnuityLottoRepository extends JpaRepository<AnnuityLottoResult, Long> {
    Optional<AnnuityLottoResult> findTopByOrderByDrwNoDesc();

    Optional<AnnuityLottoResult> findByDrwNo(int drwNo);

    List<AnnuityLottoResult> findAllByDrwNoIn(Set<Integer> drwNos);
}