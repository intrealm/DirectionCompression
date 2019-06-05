package com.example.demo.service.executor;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.example.demo.thread.CompressionThread;
import com.example.demo.thread.DecompressionThread;

@Service
public class CompressionExecutorServiceImpl implements CompressionExecutorService {

	@Resource(name = "CompressionThreadPool")
	private TaskExecutor comtaskExecutor;

	@Autowired
	private ApplicationContext applicationContext;

	@Resource(name = "DecompressionThreadPool")
	private TaskExecutor decomtaskExecutor;
		

	@Override
	public boolean scheduleCompression(String source, String destination, int maxSize,int parallelCount) {
		try {
			final CompressionThread thread = applicationContext.getBean(CompressionThread.class);
			thread.setDestinationDir(destination);
			thread.setSourceDir(source);
			thread.setMaxFileLimit(maxSize);
			thread.setParallelCount(parallelCount);
			comtaskExecutor.execute(thread);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean scheduleDecompression(String source, String destination,int parallelCount) {

		try {
			final DecompressionThread thread = applicationContext.getBean(DecompressionThread.class);
			thread.setDestinationDir(destination);
			thread.setSourceDir(source);
			thread.setParallelCount(parallelCount);
			decomtaskExecutor.execute(thread);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
