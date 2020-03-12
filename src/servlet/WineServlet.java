package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.WineDao;
import model.Common;
import model.Wine;
import util.CommonUtil;

/**
 * Servlet implementation class WineServlet
 */
@WebServlet("/WineServlet")
public class WineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WineServlet() {
		super();
	}
	public void Json2Entity(Wine wine){
    	System.out.println("================== Wine =====================");
		System.out.println(wine.getId());
    	System.out.println(wine.getStatus());
    	System.out.println(wine.getCreateUser());
    	System.out.println(wine.getCreateTime());
    	System.out.println(wine.getUpdateUser());
    	System.out.println(wine.getUpdateTime());
    	
    }
	

	public void Json2Entity(Common common){
    	System.out.println("================== Common =====================");
		
    	System.out.println(common.getAction());
    	System.out.println(common.getPageNum());
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommonUtil comm = new CommonUtil();
        Gson g=new Gson();
		WineDao dao = new WineDao();
		Wine wine = new Wine();
		System.out.println(request.getParameter("wine"));
        Common common = g.fromJson(request.getParameter("common"), Common.class);
        String id = comm.getString(request.getParameter("id"));
        Json2Entity(common);
		boolean b = false;
		
		if (common.getAction().equals("del") && !comm.isBlank(id)) {
			wine.setId(id);
			b = dao.del(wine);
		}else if(!common.getAction().equals("del")) {
	        wine = g.fromJson(request.getParameter("wine"), Wine.class);
	        Json2Entity(wine);
			b = dao.update(wine,common);
		}else {
			System.out.println("Error: 刪除時id不可為空");
		}
		if(b) response.getWriter().print("ok");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
