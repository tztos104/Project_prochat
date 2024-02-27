package prochat.yj_activityservice.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import prochat.yj_activityservice.model.FileUploadDownload;
import prochat.yj_activityservice.model.entity.File;
import prochat.yj_activityservice.repository.FileRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("file")
public class FileController {

	@Autowired
	FileUploadDownload fileUploadDownload;
	
	@Autowired
	FileRepository fileRepository;
	
	@PostMapping("upload")
	public ResponseEntity<Object> upload(
			@RequestParam("tempFile") MultipartFile tempFile,
			@RequestParam("writer") String writer) {
		
		Map<String, Object> dt = new LinkedHashMap<>();
		String fName = fileUploadDownload.fileSave(tempFile);
		
		File file = new File();
		file.setFileName(fName);
		file.setMemberId(writer);

		fileRepository.save(file);
		
		dt.put("sFileName", fName);
		dt.put("sFileURL", "/upload_file/"+fName);
		dt.put("bNewLine", true);
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(dt);
	}
}
