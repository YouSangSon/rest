package yousang.backend.rest.dto.lotto;

public record LottoDTO(int drwNo, String drwNoDate, int drwtNo1, int drwtNo2, int drwtNo3, int drwtNo4,
        int drwtNo5, int drwtNo6, int bnusNo, long firstAccumamnt, int firstPrzwnerCo,
        long firstWinamnt, long totSellamnt, String returnValue) {
}
