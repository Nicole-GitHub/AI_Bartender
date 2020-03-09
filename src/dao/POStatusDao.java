package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.POStatus;
import util.CommonUtil;
import util.DBConn;
import util.DateUtil;

public class POStatusDao {
	static String sql;
	static final String tablePS = "POStatus";
	static boolean b;
	static final CommonUtil comm = new CommonUtil();
	static final DateUtil date = new DateUtil();
	static final Connection conn = DBConn.getConn();
	static PreparedStatement ps = null;
	static ResultSet rs = null;

	/**
	 * 列出POStatus
	 */
	public ArrayList<POStatus> query(String id, String action) {
		ArrayList<POStatus> list = new ArrayList<POStatus>();
		POStatus poStatus;
		sql = "select * from " + tablePS + " where poid = ? order by updateTime desc "
				+ (action.equals("final") ? "limit 1" : "");
		System.out.println(sql);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				poStatus = new POStatus();
				poStatus.setPoId(comm.getString(rs.getString("poid")));
				poStatus.setPoStatus(comm.getString(rs.getString("postatus")));
				poStatus.setUpdateUser(comm.getString(rs.getString("updateUser")));
				poStatus.setUpdateTime(comm.getString(rs.getString("updateTime")));
				list.add(poStatus);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
