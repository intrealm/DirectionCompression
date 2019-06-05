package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "file_entity")
public class FileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String compressionDirectory;
	
	@Column
	private String compressedFileName;
	
	@Column
	private String originalRelativeFileName;
	
	@Column
	private int partsCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getCompressionDirectory() {
		return compressionDirectory;
	}

	public void setCompressionDirectory(String compressionDirectory) {
		this.compressionDirectory = compressionDirectory;
	}

	public String getCompressedFileName() {
		return compressedFileName;
	}

	public void setCompressedFileName(String compressedFileName) {
		this.compressedFileName = compressedFileName;
	}


	public String getOriginalRelativeFileName() {
		return originalRelativeFileName;
	}

	public void setOriginalRelativeFileName(String originalRelativeFileName) {
		this.originalRelativeFileName = originalRelativeFileName;
	}

	public int getPartsCount() {
		return partsCount;
	}

	public void setPartsCount(int partsCount) {
		this.partsCount = partsCount;
	}

	@Override
	public String toString() {
		return "FileEntity [id=" + id + ", compressionDirectory=" + compressionDirectory + ", compressedFileName="
				+ compressedFileName + ", originalRelativeFileName=" + originalRelativeFileName + ", partsCount="
				+ partsCount + "]";
	}
	
	
		
}
