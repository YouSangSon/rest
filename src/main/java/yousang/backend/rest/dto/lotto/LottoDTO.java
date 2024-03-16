package yousang.backend.rest.dto.lotto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LottoDTO {
    public record Lotto(int drwNo, String drwNoDate, int drwtNo1, int drwtNo2, int drwtNo3, int drwtNo4, int drwtNo5, int drwtNo6, int bnusNo,
                        long firstAccumamnt, int firstPrzwnerCo, long firstWinamnt, long totSellamnt, String returnValue) {
    }

    public record PredictLotto(int predictDrwNo, int drwtNo1, int drwtNo2, int drwtNo3, int drwtNo4, int drwtNo5, int drwtNo6, BigDecimal predictPer,
                               BigInteger predictEpoch) {
    }
}
