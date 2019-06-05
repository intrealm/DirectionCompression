package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FileEntity;


public interface FileRepository extends JpaRepository<FileEntity, Integer>{

	
	public List<FileEntity> findByCompressionDirectory(String compressionDirectory);
}
