package servlet;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * Servlet implementation class Import
 */
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

//		String filePath = "C:\\Users\\User\\22\\Create DB and TABLE\\"; // Windows 路徑
		String filePath = "/Users/nicole/22/AI_Bartender 商品資料/"; // Mac 路徑
		try {
			new ExcelUtil().imp(filePath+"wineImportTest.xlsx");
		} catch (InvalidFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			response.getWriter().print(e.getMessage());
		}
		response.getWriter().print("ok");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
