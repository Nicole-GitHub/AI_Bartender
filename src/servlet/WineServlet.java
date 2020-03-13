package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import dao.WineDao;
import model.Common;
import model.Wine;
import util.CommonUtil;
import util.ExcelUtil;
import util.FileUtil;

/**
 * Servlet implementation class WineServlet
 */
@MultipartConfig
@WebServlet("/WineServlet")
public class WineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WineServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF8");
		CommonUtil comm = new CommonUtil();
		WineDao dao = new WineDao();
		Common common = new Common();
		Wine wine = new Wine();

    	wine.setId(comm.getString(req.getParameter("id")));
    	wine.setEnName(comm.getString(req.getParameter("enName")));
    	wine.setChName(comm.getString(req.getParameter("chName")));
    	wine.setType(comm.getString(req.getParameter("type")));
    	wine.setPercent(comm.StringToDouble(req.getParameter("percent")));
    	wine.setMl(comm.StringToInt(req.getParameter("ml")));
    	wine.setPrice(comm.StringToInt(req.getParameter("price")));
    	wine.setUnit(comm.getString(req.getParameter("unit")));
    	wine.setPlace(comm.getString(req.getParameter("place")));
    	wine.setGrape(comm.getString(req.getParameter("grape")));
    	wine.setFeature(comm.getString(req.getParameter("feature")));
    	wine.setStatus(comm.getString(req.getParameter("status")));
    	common.setAction(comm.getString(req.getParameter("action")));
		boolean b = false;
		
		
		if (common.getAction().equals("del") && !comm.isBlank(wine.getId())) {
			wine.setId(wine.getId());
			b = dao.del(wine);
			res.getWriter().print("ok");
		}else if(!common.getAction().equals("del")) {

			FileUtil fileUtil = new FileUtil();

	    	Part part = req.getPart("imgPath");
			System.out.println(part);
			
			String filename = fileUtil.getFilename(part);
	    	wine.setImgPath(filename);
			System.out.println("newFilename="+filename);
			String tempFilePath = getServletContext().getRealPath("/")+filename;		

			if(filename != null) {
				fileUtil.writeTo(tempFilePath, part);
			}
			b = dao.update(wine,common);
			res.sendRedirect("jsp/back/Wine.jsp");
		}else {
			System.out.println("Error: 刪除時id不可為空");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
