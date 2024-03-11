package yousang.backend.rest.service;

import org.springframework.stereotype.Service;

@Service
public class BlockingIOService {

	public void blocking() throws InterruptedException {
		Thread.sleep(5000);
	}
}