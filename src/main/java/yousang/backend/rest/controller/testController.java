package yousang.backend.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yousang.backend.rest.response.ApiResponse;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class testController {

    @GetMapping("/thread")
    public ResponseEntity<ApiResponse> exampleHelloWorld() {
        ApiResponse response = new ApiResponse(HttpStatus.OK.value(), "Hello world!",
                Thread.currentThread().isVirtual());
        return ResponseEntity.ok(response);
    }
}
