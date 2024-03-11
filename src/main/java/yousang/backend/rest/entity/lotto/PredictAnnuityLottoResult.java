package yousang.backend.rest.entity.lotto;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Entity
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
    private BigInteger predictEpoch = null;
}
