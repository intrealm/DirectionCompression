package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.DeflaterOutputStream;

public class CompressionUtility {

	private final static String FILE_SEPERATOR = System.getProperty("file.separator");

	private final static String PARTS_SEPERATOR = "_PART_";
	
	public static int persistAndCompress(File file, String destination, int maxSize, String newFileName) {
		try {
			return moveToACompressedFile(file, destination, maxSize, newFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public synchronized static String getIncreasingFileName(String destination) {
		File destinationDir = new File(destination);
		if (!destinationDir.exists())
			destinationDir.mkdir();

		int fileCount = destinationDir.listFiles(
				fNFilter -> fNFilter.isDirectory() ? false : !fNFilter.getName().contains(PARTS_SEPERATOR)).length + 1;
		return String.valueOf(fileCount);

	}

	public static int moveToACompressedFile(File file, String destination, int maxSize, String newFileName) {
		final long MAX_ZIP_SIZE = maxSize * 1024 * 1024 > 32212254L ? 32212254 : maxSize * 1024 * 1024; // 30 MB
		long currentZIPSize = 0;
		int partsCount = 1;
		String newAbsoluteFileName = destination + FILE_SEPERATOR + newFileName;
		DeflaterOutputStream out = null;
		FileOutputStream fout = null;
		try (FileInputStream fin = new FileInputStream(file);) {
			fout = new FileOutputStream(newAbsoluteFileName);
			// use spring properties to switch to different compression technique
			out = new DeflaterOutputStream(fout);
			int i;
			while ((i = fin.read()) != -1) {
				if (currentZIPSize > MAX_ZIP_SIZE) {
					out.close();
					partsCount++;
					fout = new FileOutputStream(newAbsoluteFileName + PARTS_SEPERATOR + partsCount);
					out = new DeflaterOutputStream(fout);
					currentZIPSize = 0;
				}
				out.write((byte) i);
				out.flush();
				currentZIPSize++;
			}
			out.close();// closes fout impl
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return partsCount;

	}
}
