package com.rest.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class SaveMetadata {

	@Autowired
	private Environment env;

	public void saveMetadata(File file) throws IOException {

		System.out.println("inside class");
		//Path file1 = file.toPath();
		Path file1 = Paths.get(file.getAbsolutePath());
		System.out.println("after class"+file1);
		BasicFileAttributes attrs = Files.readAttributes(file1, BasicFileAttributes.class);// Reading
																							// basic
																							// attribute/metadata
																							// of
																							// the
																							// file
		System.out.println("directory: " + attrs.isDirectory());
		System.out.println("is other : " + attrs.isOther());
		System.out.println("regular  : " + attrs.isRegularFile());
		System.out.println("symlink  : " + attrs.isSymbolicLink());
		System.out.println("size     : " + attrs.size());
		System.out.println("unique id: " + attrs.fileKey());
		System.out.println("access time  : " + attrs.lastAccessTime());
		System.out.println("creation time: " + attrs.creationTime());
		System.out.println("modified time: " + attrs.lastModifiedTime());

		//FileWriter fw = new FileWriter("d:\\test\\Write.txt");
		FileWriter fw = new FileWriter("metadata/Write.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		// String content = "This is the content to write into file\n";
		System.out.println("after bw");
		bw.write("last access time is : " + attrs.lastAccessTime().toString());
		bw.newLine();
		bw.write(attrs.creationTime().toString());
		System.out.println("bytes written");

		bw.newLine();
		bw.write("last modified time is : " + attrs.lastModifiedTime().toString());
		bw.newLine();
		System.out.println("Done");
		bw.close();
		fw.close();
	}
}