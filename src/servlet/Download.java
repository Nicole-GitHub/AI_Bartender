package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DateUtil;
import util.FileUtil;
import util.PropertiesUtil;

/**
 * Servlet implementation class Download
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Properties prop = new PropertiesUtil().getProperties();
		String filePath = getServletContext().getRealPath(File.separator)+File.separator+prop.getProperty("wineSampleFilePath");
		String fileName = prop.getProperty("wineSampleFileName");
		String fileFullPath = filePath+fileName;
		
		try {
			// 設定respones的資料格式
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			// 設定下載後的檔名
			response.setHeader("Content-Disposition","attachment; filename=" + fileName);
			new FileUtil().download(fileFullPath, response);
		} catch (Exception e) {
			response.getWriter().print(e.getMessage());
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
