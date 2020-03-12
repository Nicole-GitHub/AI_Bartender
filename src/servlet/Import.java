package servlet;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import util.ExcelUtil;
import util.FileUtil;

/**
 * Servlet implementation class Import
 */
@MultipartConfig
@WebServlet("/Import")
public class Import extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Import() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String rs = "fail";
    	FileUtil fileUtil = new FileUtil();

    	Part part = request.getPart("uploadXlsx");
		System.out.println(part);
		
		String filename = fileUtil.getFilename(part);
		System.out.println("newFilename="+filename);
		String tempFilePath = getServletContext().getRealPath("/")+filename;		

		try {
			if(filename != null) {

				fileUtil.writeTo(tempFilePath,filename, part);
				new ExcelUtil().imp(tempFilePath);
				rs = "ok";
			}
		} catch (InvalidFormatException | ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			fileUtil.deleTempFile(tempFilePath);
		}
		response.sendRedirect("jsp/back/Wine.jsp?ImportRS="+rs);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
