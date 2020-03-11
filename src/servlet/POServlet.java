package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.PODao;
import model.Common;
import model.PO;
import model.PODetail;
import util.CommonUtil;

@WebServlet("/POServlet")
public class POServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public POServlet() {
		super();
	}
	public void Json2Entity(PO po){
    	System.out.println("================== PO =====================");
		System.out.println(po.getId());
    	System.out.println(po.getTotal());
    	System.out.println(po.getOwner());
    	System.out.println(po.getStatus());
    	System.out.println(po.getFreightId());
    	System.out.println(po.getFreightName());
    	System.out.println(po.getCreateUser());
    	System.out.println(po.getCreateTime());
    	System.out.println(po.getUpdateUser());
    	System.out.println(po.getUpdateTime());
    	System.out.println(po.getPoDetail());
    	
    	for(PODetail pd : po.getPoDetail()){
        	System.out.println("================== PODetail =====================");
        	System.out.println(pd.getPoId());
        	System.out.println(pd.getWineId());
        	System.out.println(pd.getPrice());
        	System.out.println(pd.getUnit());
        	System.out.println(pd.getQuantity());
        	System.out.println(pd.getSubtotal());
    	}
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
		PODao dao = new PODao();
		PO po = new PO();
		System.out.println(request.getParameter("po"));
        Common common = g.fromJson(request.getParameter("common"), Common.class);
        String id = comm.getString(request.getParameter("id"));
        String owner = comm.getString(request.getParameter("owner"));
        Json2Entity(common);
		boolean b = false;
		
		if (common.getAction().equals("del") && !comm.isBlank(id)) {
			po.setId(id);
			po.setOwner(owner);
			b = dao.del(po);
		}else if(!common.getAction().equals("del")) {
	        po = g.fromJson(request.getParameter("po"), PO.class);
	        Json2Entity(po);
			b = dao.update(po,common);
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
