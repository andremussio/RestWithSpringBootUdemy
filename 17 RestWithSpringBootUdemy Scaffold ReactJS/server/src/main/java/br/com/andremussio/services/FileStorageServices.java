package br.com.andremussio.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.andremussio.config.FileStorageConfig;
import br.com.andremussio.exception.FileStorageException;
import br.com.andremussio.exception.MyFileNotFoundException;

@Service
public class FileStorageServices {

	private final Path fileStorageLocation;
	
	@Autowired
	public FileStorageServices(FileStorageConfig fileStorageConfig) {
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath()
				.normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored", e);
		}
	}

	public String storeFile(MultipartFile file) {
		// cleanPath trata espaços em branco e caracteres especiais no nome do arquivo
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			// Verifica se o nome do arquivo contém ".."
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			
			/*
			  Essas duas linhas seguintes consistem da regra de armazenamento do arquivo enviado.
			  Caso seja necessário armazenar o arquivo no BD ou na AWS, por exemplo, basta alterar essas duas linhas.
			  O restante não muda.
			 */
			// Define o path do arquivo dentro da pasta padrão de upload
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			// Copia o arquivo para a pasta padrão de upload
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
			
		} catch (Exception e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
	}
	
	public Resource loadFileAsResource(String fileName) {
		
		try {
			/*
			  Essas duas linhas seguintes consistem da regra de obtenção do arquivo para download.
			  Caso seja necessário buscar o arquivo do BD ou da AWS, por exemplo, basta alterar essas duas linhas.
			  O restante não muda.
			 */
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if (resource.exists() ) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}

		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found " + fileName, e);
		}
	}
	
}
