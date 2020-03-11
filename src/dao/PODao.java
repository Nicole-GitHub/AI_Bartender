package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Common;
import model.PO;
import model.PODetail;
import model.POStatus;
import util.CommonUtil;
import util.DBConn;
import util.DateUtil;
import view.PODetailV;

public class PODao {
	static String sql,sqlPO,sqlPD,sqlPS;
	static final String tablePO = "PO";
	static final String tablePD = "PODetail";
	static final String tablePS = "POStatus";
	static boolean b;
	static final CommonUtil comm = new CommonUtil();
	static final DateUtil date = new DateUtil();
	static final Connection conn = DBConn.getConn();
	static PreparedStatement ps = null;
	static ResultSet rs = null;

	public boolean update(PO po, Common common) {

		if (common.getAction().equals("add")) {
			sqlPO = "insert into " + tablePO + " values(?,?,?,?,?,?,?,sysdate(),?,sysdate())";
		} else {
			sqlPO = "update " + tablePO + " set total = ? , status = ? , freightId = ? , freightName = ? ,"
					+ " updateUser = ? , updateTime = sysdate() where id = ? ";
			sql = "delete from " + tablePD + " where poid = ? ";
		}

		sqlPD = "insert into " + tablePD + " values(?,?,?,?,?,?)";

		try {
			if (common.getAction().equals("add")) {
				ps = conn.prepareStatement(sqlPO);
				ps.setString(1, po.getId());
				ps.setInt(2, po.getTotal());
				ps.setString(3, po.getOwner());
				ps.setNString(4, po.getStatus());
				ps.setString(5, po.getFreightId());
				ps.setNString(6, po.getFreightName());
				ps.setString(7, po.getCreateUser());
				ps.setString(8, po.getUpdateUser());
				b = ps.executeUpdate() > 0;
			} else if (common.getAction().equals("update")) {
				ps = conn.prepareStatement(sqlPO);
				ps.setInt(1, po.getTotal());
				ps.setNString(2, po.getStatus());
				ps.setString(3, po.getFreightId());
				ps.setNString(4, po.getFreightName());
				ps.setString(5, po.getUpdateUser());
				ps.setString(6, po.getId());
				b = ps.executeUpdate() > 0;

				ps = conn.prepareStatement(sql);
				ps.setString(1, po.getId());
				b = ps.executeUpdate() > 0;
			}

			for (PODetail detail : po.getPoDetail()) {
				ps = conn.prepareStatement(sqlPD);
				ps.setString(1, detail.getPoId());
				ps.setString(2, detail.getWineId());
				ps.setInt(3, detail.getPrice());
				ps.setNString(4, detail.getUnit());
				ps.setInt(5, detail.getQuantity());
				ps.setInt(6, detail.getSubtotal());
				b = ps.executeUpdate() > 0;
			}
			chgStatus(po);
			
		} catch (SQLException e) {
			System.out.println(e);
		}

		return b;
	}

	public boolean del(PO po) {
		sqlPO = "update " + tablePO + " set status = ? where id = ? ";

		try {
			ps = conn.prepareStatement(sqlPO);
			ps.setNString(1, "已取消");
			ps.setString(2, po.getId());
			b = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println(e);
		}
		po.setStatus("已取消");
		po.setUpdateUser(po.getOwner());
		chgStatus(po);
		return b;
	}
	
	private void chgStatus(PO po){
		sqlPS = "insert into " + tablePS + " values(?,?,?,sysdate())";

		try {
			ArrayList<POStatus> posList = new POStatusDao().query(po.getId(),"final");
			String oldStatus = "";
			for(POStatus oldpos : posList) {
				oldStatus = oldpos.getPoStatus();
			}
			if(!oldStatus.equals(po.getStatus())) {
				ps = conn.prepareStatement(sqlPS);
				ps.setString(1, po.getId());
				ps.setNString(2, po.getStatus());
				ps.setString(3, po.getUpdateUser());
				b = ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * 查詢
	 * 	後端 訂單管理 查詢頁 po = null
	 * 	後端 訂單管理 明細頁/更新頁 po 有 id
	 * 	後端 訂單管理 查詢頁搜尋 po 有查詢條件的欄位值
	 * 	前端 查歷史詢問單 po 有 owner
	 * 
	 * @param po
	 * @return
	 */
	public ArrayList<PO> query(PO po) {
		sqlPO = "select * from " + tablePO + " where 1=1";
		boolean poIsNotNull = po != null && !comm.getString(po.getId()).equals("");
		
		//進入訂單明細頁所需
		if (poIsNotNull && !comm.getString(po.getId()).equals("")) {
			sqlPO += " and id = '" + po.getId() + "'";
			sqlPD = "select pd.*,w.chName from " + tablePD + " pd left join Wine w on pd.wineId = w.id "
					+ " where pd.poid = '" + po.getId() + "'";
		}
		
		//搜尋條件
		if(!comm.getString(po.getOwner()).equals("")) {
			sqlPO += " and owner = ?";
		}

		if(!comm.getString(po.getStatus()).equals("")) {
			sqlPO += " and status = ?";
		}
		
		sqlPO += " order by id desc;";
//		System.out.println("PO =" + po);
		System.out.println("sqlPO =" + sqlPO);
		ArrayList<PO> arr = new ArrayList<PO>();
		PO rsPo = null;
		PODetail rsPoDetail = null;
		try {
			Connection conn = DBConn.getConn();
			ps = conn.prepareStatement(sqlPO);
			int parameterIndex = 1;
			//搜尋條件
			if(!comm.getString(po.getOwner()).equals("")) {
				ps.setString(parameterIndex++, po.getOwner());
			}

			if(!comm.getString(po.getStatus()).equals("")) {
				ps.setNString(parameterIndex++, po.getStatus());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				rsPo = new PO();
				rsPo.setId(rs.getString("id"));
				rsPo.setTotal(rs.getInt("total"));
				rsPo.setOwner(rs.getString("owner"));
				rsPo.setStatus(rs.getString("status"));
				rsPo.setFreightId(rs.getString("freightid"));
				rsPo.setFreightName(rs.getString("freightname"));
				rsPo.setCreateUser(rs.getString("createUser"));
				rsPo.setCreateTime(rs.getString("createTime"));
				rsPo.setUpdateUser(rs.getString("updateUser"));
				rsPo.setUpdateTime(rs.getString("updateTime"));
				arr.add(rsPo);
			}
			
			//進入訂單明細頁所需
			if (poIsNotNull) {
				arr = new ArrayList<PO>();
				System.out.println("sqlPD =" + sqlPD);
				ps = conn.prepareStatement(sqlPD);
				rs = ps.executeQuery();

				rs.last();
				int c = rs.getRow();
				rs.first();
//				System.out.println("C==>" + c);
				PODetail[] rsPoDetailArr = new PODetail[c];
				int i = 0;
				do {
					rsPoDetail = new PODetail();
					rsPoDetail.setPoId(rs.getString("poid"));
					rsPoDetail.setPrice(rs.getInt("price"));
					rsPoDetail.setQuantity(rs.getInt("quantity"));
					rsPoDetail.setSubtotal(rs.getInt("subtotal"));
					rsPoDetail.setUnit(rs.getString("unit"));
					rsPoDetail.setWineId(rs.getString("wineId"));
					rsPoDetail.setWineName(rs.getString("chName"));
					rsPoDetailArr[i++] = rsPoDetail;
				} while (rs.next());
				rsPo.setPoDetail(rsPoDetailArr);
				arr.add(rsPo);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return arr;
	}
	
	/**
	 * 前端 訂單明細查詢 
	 * 
	 * @param po
	 * @return
	 */
	public ArrayList<PODetailV> queryPODetailV(String poId) {
		sqlPD = "select pd.*,w.chName,w.imgPath,w.place,w.grape "
				+ " from " + tablePD + " pd left join Wine w on pd.wineId = w.id "
				+ " where pd.poid = ? ";
		
		System.out.println("sqlPD =" + sqlPD);
		ArrayList<PODetailV> arr = new ArrayList<PODetailV>();
		PODetailV rsPoDetailV = null;
		try {
			Connection conn = DBConn.getConn();
			ps = conn.prepareStatement(sqlPD);
			ps.setString(1, poId);
			rs = ps.executeQuery();
			while (rs.next()) {
				rsPoDetailV = new PODetailV();
				rsPoDetailV.setImgPath(rs.getString("ImgPath"));
				rsPoDetailV.setWineChName(rs.getString("chName"));
				rsPoDetailV.setPlace(rs.getString("Place"));
				rsPoDetailV.setGrape(rs.getString("Grape"));
				rsPoDetailV.setQuantity(rs.getInt("Quantity"));
				rsPoDetailV.setUnit(rs.getString("unit"));
				rsPoDetailV.setPrice(rs.getInt("Price"));
				rsPoDetailV.setSubtotal(rs.getInt("Subtotal"));
				arr.add(rsPoDetailV);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return arr;
	}
	/**
	 * 取po的id
	 * 
	 * @return
	 */
	public String getId() {
		String now = new DateUtil().getNowDateTimeFormat("yyMMdd");
//		System.out.println(now);
		sqlPO = "select max(id) id from " + tablePO + " where  id like '" + now + "%'";
		Integer id = 0;

		try {
			ps = conn.prepareStatement(sqlPO);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = comm.StringToInt(rs.getString("id"));
				if (id == 0) {
					id = Integer.parseInt(now + "1001");
				} else {
					id = id + 1;
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return id.toString();
	}

	public static void main(String[] arg) {
		new PODao().getId();
//		System.out.println(conn);
//		ArrayList<PO> arr = new PODao().query(null);
//		System.out.println(arr.size());
	}

}
