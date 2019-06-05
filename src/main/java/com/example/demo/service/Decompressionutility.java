package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.InflaterInputStream;

public class Decompressionutility {
	
	private Decompressionutility()
	{
		//since this a utility class
	}

	private final static String PARTS_SEPERATOR = "_PART_";

	public static void deflateToDestination(String absoluteCompressedFileName, String targetDirectory,
			String targetFileName, int partCounts) {

		try {
			int i;
			FileInputStream fin = new FileInputStream(absoluteCompressedFileName);
			InflaterInputStream in = new InflaterInputStream(fin);

			File outputDir = new File(targetDirectory);
			if (!outputDir.exists()) {
				outputDir.mkdir();
			}
			File outputFile = new File(targetDirectory + targetFileName);
			if (!outputFile.exists()) {
				if (outputFile.createNewFile()) {
					throw new RuntimeException("Couldn't create new file");
				}
			}

			FileOutputStream fout = new FileOutputStream(outputFile, false);

			while ((i = in.read()) != -1) {
				fout.write((byte) i);
				fout.flush();
			}
			for (int part = 2; part <= partCounts; part++) {
				// close
				fin.close();
				in.close();
				fin = new FileInputStream(absoluteCompressedFileName + PARTS_SEPERATOR + part);
				in = new InflaterInputStream(fin);
				while ((i = in.read()) != -1) {
					fout.write((byte) i);
					fout.flush();
				}
			}
			fin.close();
			in.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getCause());
		}
	}

}
