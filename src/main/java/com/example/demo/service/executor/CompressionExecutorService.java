package com.example.demo.service.executor;

public interface CompressionExecutorService {
	
	public boolean scheduleCompression(String source, String destination,int maxSize,int parallelCount);
	
	public boolean scheduleDecompression(String source, String destination,int parallelCount);

}
