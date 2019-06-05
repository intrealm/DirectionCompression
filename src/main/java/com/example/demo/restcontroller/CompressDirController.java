package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.executor.CompressionExecutorService;

@RestController
public class CompressDirController {

	@Autowired
	private CompressionExecutorService compressionExecutorService;

	@PostMapping(path = "/compress")
	public @ResponseBody String compress(final @RequestParam(name = "sourcedir") String sourceDir,
			final @RequestParam(name = "destdir") String destdir, final @RequestParam(name = "maxsize") int maxSize,
			final @RequestParam(name="parallelCount",defaultValue = "1", required = false) int parallelCount) 
	{
		if (compressionExecutorService.scheduleCompression(sourceDir, destdir, maxSize, parallelCount))
			return "SCHEDULED";
		return "ERROR";
	}

	@PostMapping(path = "/decompress")
	public @ResponseBody String deCompress(final @RequestParam(name = "sourcedir") String sourceDir,
			final @RequestParam(name = "destdir") String destdir,final @RequestParam
			(name="parallelCount",defaultValue = "1", required = false) int parallelCount) 
	{
		if (compressionExecutorService.scheduleDecompression(sourceDir, destdir,parallelCount))
			return "SCHEDULED";
		return "ERROR";
	}

}
