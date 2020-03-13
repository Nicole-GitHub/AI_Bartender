package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class FileUtil {
	
	public void deleTempFile(String tempFilePath) {
		File file = new File(tempFilePath);
		if(file.exists()) {
			file.delete();
		}
	}
	
	public String getFilename(Part part) {
        String header = part.getHeader("Content-Disposition");
        String filename = header.substring(header.indexOf("filename=\"") + 10,
                header.lastIndexOf("\""));
        if(filename.equals("")) {
        	return null;
        }
        String newfilename = "imgs/"+filename+"";
        return newfilename;
    }
	public void writeTo(String tempFilePath, Part part) throws IOException,
            FileNotFoundException {
        InputStream in = part.getInputStream();
        OutputStream out = new FileOutputStream(tempFilePath);
        byte[] buffer = new byte[1024];
        int length = -1;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    }
	
	public void download(String fileFullPath ,HttpServletResponse response) throws Exception {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			// 從上述路徑下取出剛剛匯出的檔案並透過網頁下載到使用者電腦
			bis = new BufferedInputStream(new FileInputStream(fileFullPath));
			bos = new BufferedOutputStream(response.getOutputStream());
	
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (bos != null) {
				bos.flush();
				bos.close();
			}
			if (bis != null) {
				bis.close();
			}
		}
	}
}
