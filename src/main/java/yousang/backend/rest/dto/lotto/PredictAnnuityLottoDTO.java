package yousang.backend.rest.dto.lotto;

import java.math.BigDecimal;
import java.math.BigInteger;

public record PredictAnnuityLottoDTO(int predictDrwNo, int drwtNo1, int drwtNo2, int drwtNo3, int drwtNo4, int drwtNo5,
        int drwtNo6, BigDecimal predictPer, BigInteger predictEpoch) {
}
