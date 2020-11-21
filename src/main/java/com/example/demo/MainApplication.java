package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.upload.UploadInToDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;


@SpringBootApplication
public class MainApplication {

/*
	@Value("${upload.file}")
	private String path;


 */

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
/*
	@PostConstruct
	public void upload() {
		UploadInToDB uploadInToDB = new UploadInToDB();
		uploadInToDB.uploadDB();
	}
*/
}
