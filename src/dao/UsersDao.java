package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Users;
import util.CommonUtil;
import util.DBConn;
import util.DateUtil;

public class UsersDao {
	static String sql;
	static final String table = "Users";
	static boolean b;
	static final CommonUtil comm = new CommonUtil();
	static final DateUtil date = new DateUtil();
	static final Connection conn = DBConn.getConn();
	static PreparedStatement ps = null;
	static ResultSet rs = null;

	/**
	 * 列出Users的所有項目
	 */
	public ArrayList<Users> getUsersList() {
		ArrayList<Users> list = new ArrayList<Users>();
		Users users;
		sql = "select * from " + table + " where type = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setNString(1, "一般");
			rs = ps.executeQuery();
			while (rs.next()) {
				users = new Users();
				users.setEmail(comm.getString(rs.getString("email")));
				users.setName(comm.getString(rs.getString("name")));
				users.setPassword(comm.getString(rs.getString("password")));
				users.setMobile(comm.getString(rs.getString("mobile")));
				users.setAddress(comm.getString(rs.getString("address")));
				users.setBday(comm.getString(rs.getString("bday")));
				users.setType(comm.getString(rs.getString("type")));
				users.setStatus(comm.getString(rs.getString("status")));
				list.add(users);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
