package yousang.backend.rest.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import yousang.backend.rest.entity.User.User;
import yousang.backend.rest.repository.User.UserRepository;

@Service
public class UserService {

    // @Autowired
    // private UserRepository userRepository;

    // @Autowired
    // private RedisTemplate<String, Object> redisTemplate;

    // public void join(User user) {
    //     // 비밀번호 암호화
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));

    //     // DB에 저장
    //     userRepository.save(user);

    //     // Redis에 저장
    //     redisTemplate.opsForValue().set(user.getUsername(), user);
    // }

    // public String login(String username, String password) {
    //     // 사용자 정보 조회
    //     User user = userRepository.findByUsername(username);

    //     // 비밀번호 일치 여부 확인
    //     if (!passwordEncoder.matches(password, user.getPassword())) {
    //         throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    //     }

    //     // JWT 토큰 생성
    //     String token = JwtUtils.createToken(user.getUsername(), user.getRoles());

    //     return token;
    // }

    // public void withdraw(String username) {
    //     // DB에서 삭제
    //     userRepository.deleteById(username);

    //     // Redis에서 삭제
    //     redisTemplate.delete(username);
    // }
}