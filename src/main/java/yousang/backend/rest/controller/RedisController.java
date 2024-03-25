package yousang.backend.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/get/{key}")
    public Object getValue(@PathVariable String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @PutMapping("/set/{key}/{value}")
    public String setValue(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Value set";
    }

    @DeleteMapping("/delete/{key}")
    public String deleteValue(@PathVariable String key) {
        redisTemplate.delete(key);
        return "Value deleted";
    }
}
