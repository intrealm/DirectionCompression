package com.example.demo.thread;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.entity.FileEntity;
import com.example.demo.repository.FileRepository;
import com.example.demo.service.CompressionUtility;

@Component
@Scope("prototype")
public class CompressionThread implements Runnable {

	private String sourceDir;

	private String destinationDir;

	private int maxFileLimit;

	private int parallelCount;
	
	@Autowired
	private FileRepository fileRepository;

	private static final String BASE_DIRECTORY = "C:\\temporary";

	private static final String FILE_SEPERATOR = System.getProperty("file.separator");

	@Override
	public void run() {
		File parentFolder = new File(BASE_DIRECTORY + FILE_SEPERATOR + sourceDir);
		if (parentFolder.exists())
			compressFiles(parentFolder, destinationDir, maxFileLimit);
	}

	public void compressFiles(File source, String destinationDir, int maxFileLimit) {
		Queue<File> queueDir = new LinkedBlockingQueue<>();
		ThreadPoolExecutor executor=null;
		if (source.isDirectory())
		{
			queueDir.add(source);
			executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.parallelCount<10?parallelCount:10);
		}
	
		if(executor==null)
			return;
					
		while (!queueDir.isEmpty()) {
			File currDir = queueDir.poll();

			for (File curFile : currDir.listFiles()) {
				if (curFile.isDirectory()) {
					queueDir.add(curFile);
					continue;
				}
				// Compress and persist To dir

		        executor.execute(()->{
					String fileName = CompressionUtility
							.getIncreasingFileName(BASE_DIRECTORY + FILE_SEPERATOR + destinationDir);

					int fileCount = CompressionUtility.persistAndCompress(curFile,
							BASE_DIRECTORY + FILE_SEPERATOR + destinationDir, maxFileLimit, fileName);

					insertRecord(fileName, fileCount, curFile.getAbsolutePath());
		        });
				
			}
		}
	}

	public void insertRecord(String fileName, int fileCount, final String sourceAbosoluatePath) {
		FileEntity entity = new FileEntity();
		entity.setCompressionDirectory(destinationDir);
		entity.setCompressedFileName(fileName);
		String relativeFileName = sourceAbosoluatePath.replace(BASE_DIRECTORY, "")
				.replace(this.sourceDir + FILE_SEPERATOR, "");

		entity.setOriginalRelativeFileName(relativeFileName);
		entity.setPartsCount(fileCount);
		// try catch
		this.fileRepository.save(entity);

		System.out.println(entity);
	}

	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public String getDestinationDir() {
		return destinationDir;
	}

	public void setDestinationDir(String destinationDir) {
		this.destinationDir = destinationDir;
	}

	public int getMaxFileLimit() {
		return maxFileLimit;
	}

	public void setMaxFileLimit(int maxFileLimit) {
		this.maxFileLimit = maxFileLimit;
	}

	public CompressionThread() {
		super();
	}

	public CompressionThread(String sourceDir, String destinationDir, int maxFileLimit) {
		super();
		this.sourceDir = sourceDir;
		this.destinationDir = destinationDir;
		this.maxFileLimit = maxFileLimit;
	}

	public int getParallelCount() {
		return parallelCount;
	}

	public void setParallelCount(int parallelCount) {
		this.parallelCount = parallelCount;
	}
	

}
