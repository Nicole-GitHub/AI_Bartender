package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import model.Wine;
import util.CommonUtil;
import util.DBConn;
import util.DateUtil;
import view.PODetailV;

public class WineDao {
	static String sql;
	static final String table = "Wine";
	static boolean b;
	static final CommonUtil comm = new CommonUtil();
	static final DateUtil date = new DateUtil();
	static final Connection conn = DBConn.getConn();
	static PreparedStatement ps = null;
	static ResultSet rs = null;

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
