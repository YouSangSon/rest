package yousang.backend.rest.dto.lotto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class AnnuityLottoDTO {
    public record AnnuityLotto(int drwNo, String drwNoDate, int drwtNo1, int drwtNo2, int drwtNo3, int drwtNo4, int drwtNo5, int drwtNo6, int bonusNo1,
                               int bonusNo2, int bonusNo3, int bonusNo4, int bonusNo5, int bonusNo6) {
    }

    public record PredictAnnuityLotto(int predictDrwNo, int drwtNo1, int drwtNo2, int drwtNo3, int drwtNo4, int drwtNo5, int drwtNo6, BigDecimal predictPer,
                                      BigInteger predictEpoch) {
    }
}
