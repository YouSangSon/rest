package yousang.backend.rest.entity.lotto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lotto_results")
public class LottoResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    private Long totSellamnt;
    private String returnValue;
    private Date drwNoDate;
    private Long firstWinamnt;
    private int drwtNo1;
    private int drwtNo2;
    private int drwtNo3;
    private int drwtNo4;
    private int drwtNo5;
    private int drwtNo6;
    private int bnusNo;
    private Long firstAccumamnt;
    private int drwNo;
    private int firstPrzwnerCo;
}
