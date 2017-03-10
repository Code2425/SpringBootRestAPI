package com.example;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rest.service.SaveMetadata;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.header.FormDataContentDisposition.FormDataContentDispositionBuilder;

@RestController
public class RestAPI {

	@Autowired
	private Environment env;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		// LOGGER.info("Request Received for Index Page ");
		return "index.html";
	}

	@RequestMapping("/hello")
	private String getName() {
		return "hi";

	}

	@RequestMapping(value = "/getfile", method = RequestMethod.GET)
	public Response getFile() throws IOException {
		
		

		System.out.println("Inside GET method");

		String file = env.getProperty("upload.file.path");
		ResponseBuilder response = javax.ws.rs.core.Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=DisplayName-Demofile.txt");
		return response.build();
	}

	// to upload the file
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(@RequestParam("uploadFile") MultipartFile uploadFile) {
		System.out.println("Request Received for File Upload ");
		
		try {
			
			String filename = uploadFile.getOriginalFilename();
			String directory = env.getProperty("upload.file.path");
			String uploadFilePath = Paths.get("." + File.separator + directory, filename).toString();
					
			final BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(uploadFilePath)));
			stream.write(uploadFile.getBytes());
			String output = "File uploaded to : " + uploadFilePath;
			stream.close();
			File file = new File(uploadFilePath);
			System.out.println("file"+file);
			SaveMetadata saveMetadata = new SaveMetadata();
			saveMetadata.saveMetadata(file);
		} catch (Exception e) {
			System.out.println("ERROR"+e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
