package br.com.andremussio.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.andremussio.data.vo.v1.UploadFileResponseVO;
import br.com.andremussio.services.FileStorageServices;
import io.swagger.annotations.Api;

@Api(tags = "FileEndpoint")
@RestController
@RequestMapping(value = "/api/file/v1")
public class FileController {

	// Cria um factory para acessar o logger da aplicação para classe FileController
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileStorageServices fileStorageServices;
	
	@PostMapping(value = "/uploadFile")
	public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
		// Armazena o arquivo recebido por upload e devolve o nome do arquivo
		String fileName = fileStorageServices.storeFile(file);
		
		// Monta URL de download do arquivo que foi armazenado
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/file/v1/downloadFile/")
				.path(fileName)
				.toUriString();
		
		return new UploadFileResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	@PostMapping(value = "/uploadMultipleFiles")
	public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}
	
	// ":.+ indica que pode passar o nome do arquivo com extensão
	@GetMapping(value = "/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable(value = "fileName") String fileName, HttpServletRequest request) {
		
		Resource resource = fileStorageServices.loadFileAsResource(fileName);
		String contentType = null;
		
		try {
			// Busca o MIME-TYPE do arquivo que será baixado
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			
		} catch (Exception e) {
			logger.info("Could not determine file type!");
		}
		
		if (contentType == null) {
			// Se não conseguiu determinar o MIME-TYPE do arquivo, assume o genérico ("octet-stream")
			contentType = "application/octet-stream";
		}
		
		// Montagem do ResponseEntity de retorno:
		// - Setando MediaType no ResponseEntity;
		// - Setando no cabeçalho da response o anexo enviado, cujo nome é definido em filename
		// - Anexando o arquivo no body do ResponseEntity
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
