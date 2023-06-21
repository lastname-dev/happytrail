package com.ssafy.enjoytrip.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class FileController {
	@GetMapping("/static/resources/upload/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
	    try {
	        String filePath = "static/resources/upload/" + filename;
	        Resource resource = new ClassPathResource(filePath);
	        if (resource.exists() && resource.isReadable()) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.parseMediaType(determineContentType(resource)));

	            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

    private String determineContentType(Resource resource) throws IOException {
        String contentType;
        try {
            contentType = Files.probeContentType(Paths.get(resource.getURI()));
        } catch (IOException e) {
            contentType = Files.probeContentType(resource.getFile().toPath());
        }
        return contentType != null ? contentType : "application/octet-stream";
    }
}
