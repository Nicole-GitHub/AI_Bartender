package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import model.Common;
import model.Wine;
import util.CommonUtil;
import util.DBConn;
import util.DateUtil;
import view.PODetailV;

public class WineDao {

	 String sql;
	static final String table = "Wine";
	static boolean b; 
	static final CommonUtil comm = new CommonUtil();
	static final DateUtil date = new DateUtil();
	static final Connection conn = DBConn.getConn();
	static PreparedStatement ps = null;
	static ResultSet rs = null;
	static int paramIndex = 1;

	public boolean update(Wine wine, Common common) {

		if (common.getAction().equals("add")) {
			sql = "insert into " + table + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,'admin',sysdate(),'admin',sysdate())";
		} else {
			sql = "update " + table + " set enName = ?, chName = ?, type = ?,"
					+ " percent = ?, ml = ?, price = ?, unit = ?, place = ?, grape = ?,"
					+ " feature = ?, status = ?, imgPath = ?,"
					+ " updateUser = ? , updateTime = sysdate() where id = ? ";
		}
		
		try {
			if (common.getAction().equals("add")) {
				paramIndex = 1;
				ps = conn.prepareStatement(sql);
				ps.setString(paramIndex++, getId(wine.getPlace()));
				ps.setString(paramIndex++, wine.getEnName());
				ps.setString(paramIndex++, wine.getChName());
				ps.setNString(paramIndex++, wine.getType());
				ps.setDouble(paramIndex++, wine.getPercent());
				ps.setInt(paramIndex++, wine.getMl());
				ps.setInt(paramIndex++, wine.getPrice());
				ps.setNString(paramIndex++, wine.getUnit());
				ps.setNString(paramIndex++, wine.getPlace());
				ps.setNString(paramIndex++, wine.getGrape());
				ps.setNString(paramIndex++, wine.getFeature());
				ps.setNString(paramIndex++, wine.getStatus());
				ps.setNString(paramIndex++, wine.getImgPath());
				b = ps.executeUpdate() > 0;
			} else if (common.getAction().equals("update")) {
				paramIndex = 1;
				ps = conn.prepareStatement(sql);
				ps.setString(paramIndex++, wine.getEnName());
				ps.setString(paramIndex++, wine.getChName());
				ps.setNString(paramIndex++, wine.getType());
				ps.setDouble(paramIndex++, wine.getPercent());
				ps.setInt(paramIndex++, wine.getMl());
				ps.setInt(paramIndex++, wine.getPrice());
				ps.setNString(paramIndex++, wine.getUnit());
				ps.setNString(paramIndex++, wine.getPlace());
				ps.setNString(paramIndex++, wine.getGrape());
				ps.setNString(paramIndex++, wine.getFeature());
				ps.setNString(paramIndex++, wine.getStatus());
				ps.setNString(paramIndex++, wine.getImgPath());
				ps.setString(paramIndex++, "admin");
				ps.setString(paramIndex++, wine.getId());
				b = ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return b;
	}

	public boolean del(Wine wine) {
		sql = "delete from " + table + " where id = ? ";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, wine.getId());
			b = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return b;
	}
	
	/**
	 * 查詢
	 * 	後端 商品管理 查詢頁 wine = null
	 * 	後端 商品管理 明細頁/更新頁 wine 有 id
	 * 	後端 商品管理 查詢頁搜尋 wine 有查詢條件的欄位值
	 * 	前端 查歷史詢問單 wine 有 owner
	 * 
	 * @param wine
	 * @return
	 */
	public ArrayList<Wine> query(Wine wine) {
		sql = "select * from " + table + " where 1=1";
		boolean wineIsNotNull = wine != null && !comm.getString(wine.getId()).equals("");
		
		//進入訂單明細頁所需
		if (wineIsNotNull && !comm.getString(wine.getId()).equals("")) {
			sql += " and id = '" + wine.getId() + "'";
		}
		
		//搜尋條件
		if(!comm.getString(wine.getChName()).equals("")) {
			sql += " and chName like ? ";
		}
		if(!comm.getString(wine.getEnName()).equals("")) {
			sql += " and enName like ? ";
		}
		if(!comm.getString(wine.getStatus()).equals("")) {
			sql += " and status = ?";
		}
		if(!comm.getString(wine.getPlace()).equals("")) {
			sql += " and place = ?";
		}
		if(!comm.getString(wine.getType()).equals("")) {
			sql += " and type = ?";
		}
		if(!comm.getString(wine.getGrape()).equals("")) {
			sql += " and grape like ? ";
		}
		
		sql += " order by id ;";
		System.out.println("sql =" + sql);
		ArrayList<Wine> arr = new ArrayList<Wine>();
		Wine rsWine = null;
		try {
			Connection conn = DBConn.getConn();
			ps = conn.prepareStatement(sql);
			paramIndex = 1;
			//搜尋條件
			if(!comm.getString(wine.getChName()).equals("")) {
				ps.setNString(paramIndex++, "%"+wine.getChName()+"%");
			}
			if(!comm.getString(wine.getEnName()).equals("")) {
				ps.setNString(paramIndex++, "%"+wine.getEnName()+"%");
			}
			if(!comm.getString(wine.getStatus()).equals("")) {
				ps.setNString(paramIndex++, wine.getStatus());
			}
			if(!comm.getString(wine.getPlace()).equals("")) {
				ps.setNString(paramIndex++, wine.getPlace());
			}
			if(!comm.getString(wine.getType()).equals("")) {
				ps.setNString(paramIndex++, wine.getType());
			}
			if(!comm.getString(wine.getGrape()).equals("")) {
				ps.setNString(paramIndex++, "%"+wine.getGrape()+"%");
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				rsWine = new Wine();
				rsWine.setId(rs.getString("id"));
				rsWine.setEnName(rs.getString("enName"));
				rsWine.setChName(rs.getString("chName"));
				rsWine.setPlace(rs.getString("place"));
				rsWine.setType(rs.getString("type"));
				rsWine.setPrice(rs.getInt("price"));
				rsWine.setPercent(rs.getDouble("percent"));
				rsWine.setMl(rs.getInt("ml"));
				rsWine.setUnit(rs.getString("unit"));
				rsWine.setGrape(rs.getString("grape"));
				rsWine.setFeature(rs.getString("feature"));
				rsWine.setStatus(rs.getString("status"));
				rsWine.setImgPath(rs.getString("imgPath"));
				rsWine.setCreateUser(rs.getString("createUser"));
				rsWine.setCreateTime(rs.getString("createTime"));
				rsWine.setUpdateUser(rs.getString("updateUser"));
				rsWine.setUpdateTime(rs.getString("updateTime"));
				arr.add(rsWine);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return arr;
	}

	/**
	 * 取wine的id
	 * 
	 * @return
	 */
	public String getId(String str) {
		str = str.substring(0,2);
//		System.out.println(now);
		String sql = "select max(id) id from " + table + " where  id like '" + str + "%'";
		String id = "";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = comm.getString(rs.getString("id"));
				if (id.isEmpty()) {
					id = "1001";
				} else {
					id = String.valueOf(Integer.parseInt(id.substring(2)) + 1);
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return str.toUpperCase()+id;
	}

	
	
	
	
	
	
	
	
	
	
	//========================================================================
	

	/**
	 * 列出wine的所有項目
	 */
	public ArrayList<Wine> getWineList() {
		ArrayList<Wine> list = new ArrayList<Wine>();
		Wine wine;
		sql = "select * from " + table;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				wine = new Wine();
				wine.setId(comm.getString(rs.getString("id")));
				wine.setEnName(comm.getString(rs.getString("enName")));
				wine.setChName(comm.getString(rs.getString("chName")));
				wine.setType(comm.getString(rs.getString("type")));
				wine.setPercent(comm.StringToDouble(rs.getString("percent")));
				wine.setMl(comm.StringToInt(rs.getString("ml")));
				wine.setPrice(comm.StringToInt(rs.getString("price")));
				wine.setUnit(comm.getString(rs.getString("unit")));
				wine.setPlace(comm.getString(rs.getString("place")));
				wine.setGrape(comm.getString(rs.getString("grape")));
				wine.setFeature(comm.getString(rs.getString("feature")));
				wine.setStatus(comm.getString(rs.getString("status")));
				wine.setImgPath(comm.getString(rs.getString("imgPath")));
				list.add(wine);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 列出購物車的所有項目
	 */
	public ArrayList<PODetailV> getCartDetail(ArrayList<Map<String, String>> buylist) {
		ArrayList<PODetailV> list = new ArrayList<PODetailV>();
		PODetailV rsPoDetailV;
		String listCount = "";
		Integer[] quantity = new Integer[buylist.size()];
		Integer subTotal = 0;
		Integer rsIndex = 0;
		for (Map<String, String> map : buylist) {
			listCount += "?,";
		}
//		System.out.println(listCount);
		sql = "select * from " + table + " where id in (" + comm.delFinalWord(listCount) + ")";
		try {
			int i = 0;
			ps = conn.prepareStatement(sql);
			for (Map<String, String> map : buylist) {
				quantity[i++] = Integer.parseInt(map.get("quantity"));
				ps.setString(i, map.get("wineId"));
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				rsIndex = rs.getRow() - 1;
				rsPoDetailV = new PODetailV();
				rsPoDetailV.setWineId(rs.getString("id"));
				rsPoDetailV.setImgPath(rs.getString("ImgPath"));
				rsPoDetailV.setWineChName(rs.getString("chName"));
				rsPoDetailV.setPlace(rs.getString("Place"));
				rsPoDetailV.setGrape(rs.getString("Grape"));
				rsPoDetailV.setQuantity(quantity[rsIndex]);
				rsPoDetailV.setUnit(rs.getString("unit"));
				rsPoDetailV.setPrice(rs.getInt("Price"));
				subTotal = quantity[rsIndex] * rs.getInt("Price");
				rsPoDetailV.setSubtotal(subTotal);
				list.add(rsPoDetailV);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
