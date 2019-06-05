package com.example.demo.thread;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.entity.FileEntity;
import com.example.demo.repository.FileRepository;
import com.example.demo.service.Decompressionutility;

@Component
@Scope("prototype")
public class DecompressionThread implements Runnable {

	private String sourceDir;

	private String destinationDir;

	private int parallelCount;

	@Autowired
	private FileRepository fileRepository;

	private static final String BASE_DIRECTORY = "C:\\temporary";

	private static final String FILE_SEPERATOR = System.getProperty("file.separator");

	@Override
	public void run() {
		if (sourceDir == null || destinationDir == null)
			return;

		List<FileEntity> files = fileRepository.findByCompressionDirectory(sourceDir);
		if (files != null) {
			ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
					.newFixedThreadPool(this.parallelCount < 10 ? parallelCount : 10);

			for (FileEntity fileEntity : files) {
				executor.execute(() -> {
					try {
						decompressFiles(fileEntity);

					} catch (Exception e) {
						System.err.println(fileEntity);
					}
				});
			}
			// directory completed
		} else {
			// empty directory
		}
	}

	public void decompressFiles(FileEntity entity) {

		String sourceFileName = entity.getCompressedFileName();
			String targetRelativeFile = entity.getOriginalRelativeFileName();
			int partsCount = entity.getPartsCount();

			String absoluateTargetFile = BASE_DIRECTORY + FILE_SEPERATOR + destinationDir + FILE_SEPERATOR
					+ targetRelativeFile;
			int indexOFPath = absoluateTargetFile.lastIndexOf(FILE_SEPERATOR);
			String targetFileName = absoluateTargetFile.substring(indexOFPath);
			String directory = absoluateTargetFile.replace(targetFileName, "");
			System.out.println(String.format("%s targetFile, %s targetOfPath, %s directory", absoluateTargetFile,
					targetFileName, directory));
			Decompressionutility.deflateToDestination(
					BASE_DIRECTORY + FILE_SEPERATOR + sourceDir + FILE_SEPERATOR + sourceFileName, directory,
					targetFileName, partsCount);
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

	public DecompressionThread() {
		super();
	}

	public DecompressionThread(String sourceDir, String destinationDir) {
		super();
		this.sourceDir = sourceDir;
		this.destinationDir = destinationDir;
	}

	public int getParallelCount() {
		return parallelCount;
	}

	public void setParallelCount(int parallelCount) {
		this.parallelCount = parallelCount;
	}

}
