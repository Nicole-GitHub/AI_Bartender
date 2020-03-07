package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Common;
import model.PO;
import model.PODetail;
import model.Users;
import model.Wine;
import util.CommonUtil;
import util.DBConn;
import util.DateUtil;

public class PODao {
	String sql;
	String sqlPO;
	String sqlPD;
	String tablePO = "PO";
	String tablePD = "PODetail";
	boolean b;
	CommonUtil comm = new CommonUtil();
	DateUtil date = new DateUtil();
	static Connection conn = DBConn.getConn();
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
		} catch (SQLException e) {
			System.out.println(e);
		}

		return b;
	}

	public boolean del(String id) {
		sqlPO = "update " + tablePO + " set status = ? where id = ? ";

		try {
			ps = conn.prepareStatement(sqlPO);
			ps.setNString(1, "已取消");
			ps.setString(2, id);
			b = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println(e);
		}

		return b;
	}

	/**
	 * 查詢(全、單一筆、搜尋)
	 * 
	 * @param po
	 * @return
	 */
	public ArrayList<PO> query(PO po) {
		sqlPO = "select id,total,owner,status,createUser,createTime,updateUser,updateTime from " + tablePO + " where 1=1";
		boolean poIsNotNull = po != null && !comm.getString(po.getId()).equals("");
		
		//進入訂單明細頁所需
		if (poIsNotNull) {
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

	/**
	 * 列出wine的所有項目或單一個項目
	 */
	public ArrayList<Wine> getWineList() {
		ArrayList<Wine> list = new ArrayList<Wine>();
		Wine wine;
		sql = "select * from Wine";
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
	 * 列出Users的所有項目
	 */
	public ArrayList<Users> getUsersList() {
		ArrayList<Users> list = new ArrayList<Users>();
		Users users;
		sql = "select * from Users where type = ?";
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

	public static void main(String[] arg) {
		new PODao().getId();
//		System.out.println(conn);
//		ArrayList<PO> arr = new PODao().query(null);
//		System.out.println(arr.size());
	}

}
