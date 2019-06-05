package com.example.demo;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.service.executor.CompressionExecutorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompressDecompressApplicationTests {

	@Resource
	private CompressionExecutorService compressionExecutorService;

	@Test
	public void compression() {
		compressionExecutorService.scheduleCompression("source", "target", 4, 1);
	}

	
	@Test
	public void decompression() {
		compressionExecutorService.scheduleDecompression("target", "sourceEQ", 1);
	}
}
