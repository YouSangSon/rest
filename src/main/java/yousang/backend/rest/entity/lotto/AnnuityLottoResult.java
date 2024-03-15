package yousang.backend.rest.entity.lotto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yousang.backend.rest.dto.lotto.AnnuityLottoDTO;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "annuity_lotto_results")
public class AnnuityLottoResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    private int drwNo;
    private Date drwNoDate;
    private int group;
    private int drwtNo1;
    private int drwtNo2;
    private int drwtNo3;
    private int drwtNo4;
    private int drwtNo5;
    private int drwtNo6;
    private int bonusNo1;
    private int bonusNo2;
    private int bonusNo3;
    private int bonusNo4;
    private int bonusNo5;
    private int bonusNo6;
}
