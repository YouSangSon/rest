package yousang.backend.rest.entity.lotto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@Entity
//@AllArgsConstructor
@NoArgsConstructor
@Table(name = "predict_annuity_lotto_results")
public class PredictAnnuityLottoResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    private int predictDrwNo;
    private int drwtNo1;
    private int drwtNo2;
    private int drwtNo3;
    private int drwtNo4;
    private int drwtNo5;
    private int drwtNo6;
    private BigDecimal predictPer;
    private BigInteger predictEpoch;

    @Builder
    public PredictAnnuityLottoResult(Long id, int predictDrwNo, int drwtNo1, int drwtNo2, int drwtNo3, int drwtNo4, int drwtNo5, int drwtNo6, BigDecimal predictPer, BigInteger predictEpoch) {
        this.id = id;
        this.predictDrwNo = predictDrwNo;
        this.drwtNo1 = drwtNo1;
        this.drwtNo2 = drwtNo2;
        this.drwtNo3 = drwtNo3;
        this.drwtNo4 = drwtNo4;
        this.drwtNo5 = drwtNo5;
        this.drwtNo6 = drwtNo6;
        this.predictPer = predictPer;
        this.predictEpoch = predictEpoch;
    }
}
