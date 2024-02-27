package prochat.yj_activityservice.model;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.regex.Pattern;

@Component
@Data
public class FileUploadDownload {
	String path = "src/main/webapp/upload_file";
	String path2 = "src/main/resources/static/upload_file";
		
	// 파일저장
	public String fileSave(MultipartFile mf) {
		String ptn = ".";
		String ptn2 = null;
		String ptn3 = ".*\\(1\\)$";
		
		String fName = mf.getOriginalFilename();
		File ff = new File(path+"/"+fName);
			
		try {
			String rename = null;
			String exe = null;
			
			int i = 1;
			while(ff.exists()) { // 중복 확인
				// 확장자 앞 파일 제목
				rename = fName.substring(0, fName.lastIndexOf(ptn));
				// 확장자
				exe = fName.substring(fName.lastIndexOf(ptn), fName.length());
				boolean checkFile = Pattern.matches(ptn3, rename);
				
				// 새로고침했을 때 반복된 파일업로드 고려(실제로 새로고침을 해도 파일이 올라가지 않게
				// 막을테니 큰 의미 없다고 생각)
				if(!checkFile && i==1) {
					rename += "(1)";
					fName = rename + exe;
					ff = new File(path+"/"+fName);
					continue;
				}
				ptn2 = rename.substring(rename.length()-(2+(i+"").length()));
				
				while(true) {
					if(ptn2.equals("(" + i + ")")) {
						i++;
					}else {
						if(i > 1) {
							rename = rename.replace(ptn2, ("(" + i + ")"));
							fName = rename + exe;
							break;
						}
					}
				}
				ff = new File(path+"/"+fName);
			}
			FileOutputStream fos = new FileOutputStream(ff);
			fos.write(mf.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fName;
	}
	
	public String videoSave(MultipartFile mf) {
		String ptn = ".";
		String ptn2 = null;
		String ptn3 = ".*\\(1\\)$";
		
		String fName = mf.getOriginalFilename();
		File ff = new File(path2+"/"+fName);
			
		try {
			String rename = null;
			String exe = null;
			
			int i = 1;
			while(ff.exists()) { // 중복 확인
				// 확장자 앞 파일 제목
				rename = fName.substring(0, fName.lastIndexOf(ptn));
				// 확장자
				exe = fName.substring(fName.lastIndexOf(ptn), fName.length());
				boolean checkFile = Pattern.matches(ptn3, rename);
				
				// 새로고침했을 때 반복된 파일업로드 고려(실제로 새로고침을 해도 파일이 올라가지 않게
				// 막을테니 큰 의미 없다고 생각)
				if(!checkFile && i==1) {
					rename += "(1)";
					fName = rename + exe;
					ff = new File(path2+"/"+fName);
					continue;
				}
				ptn2 = rename.substring(rename.length()-(2+(i+"").length()));
				
				while(true) {
					if(ptn2.equals("(" + i + ")")) {
						i++;
					}else {
						if(i > 1) {
							rename = rename.replace(ptn2, ("(" + i + ")"));
							fName = rename + exe;
							break;
						}
					}
				}
				ff = new File(path2+"/"+fName);
			}
			FileOutputStream fos = new FileOutputStream(ff);
			fos.write(mf.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fName;
	}
	
	public void download(String fileName, HttpServletResponse response) {
		try {
			// 다운로드 할 파일 제목을 PathVariable로 받음
			FileInputStream fis = new FileInputStream(path+"/"+fileName);
			String encName = URLEncoder.encode(fileName, "utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+encName);
			
			ServletOutputStream sos = response.getOutputStream();
			byte[] buf = new byte[1024];
			
			while(fis.available() > 0) {
				int len = fis.read(buf);
				sos.write(buf, 0, len);
			}
			sos.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fileDelete(String fileNames) {
		new File(path+"/"+fileNames).delete();
	}
}
