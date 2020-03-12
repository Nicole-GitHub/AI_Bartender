package servlet;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DateUtil;
import util.ExcelUtil;
import util.PropertiesUtil;

/**
 * Servlet implementation class Export
 */
@WebServlet("/Export")
public class Export extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Export() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Properties prop = new PropertiesUtil().getProperties();
		String filePath = prop.getProperty("filePath");
		String fileName = prop.getProperty("fileName");
		List<String> list = Arrays.asList("wine", "SommelierChoice"); // 要匯出的table名

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			//匯出資料至上述路徑下
			new ExcelUtil().exp(filePath + fileName+".xlsx", list);
			
			// 設定respones的資料格式
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			// 設定下載後的檔名
			response.setHeader("Content-Disposition",
					"attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, "utf-8") + "_"
							+ new DateUtil().getNowDateTimeFormat("yyyyMMdd") + ".xlsx");

			// 從上述路徑下取出剛剛匯出的檔案並透過網頁下載到使用者電腦
			bis = new BufferedInputStream(new FileInputStream(filePath + fileName+".xlsx"));
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}

		} catch (IOException | ClassNotFoundException | SQLException e) {
			response.getWriter().print(e.getMessage());
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
