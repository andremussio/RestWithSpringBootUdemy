package br.com.andremussio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * prefix = define o prefixo que identifica as propriedades que serão lidas do application.properties e armazenadas nesta classe.
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {

	private String uploadDir;

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	
}
