package ch.zrhdev.springobjectstorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringObjectStorageApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringObjectStorageApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringObjectStorageApplication.class, args);
	}

}
