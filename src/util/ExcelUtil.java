package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	static CommonUtil comm = new CommonUtil();
	static String tableName = "";
	static String sqlR = "";
	static String sqlU = "";
	static String sqlC = "";
	static String insKey = "";
	static String insVal = "";
	static String updateSet = "";
	static String id = "";
	static int rowCount = 0;

	static FileInputStream fis = null;
	static FileOutputStream fos = null;
	static Workbook workbook = null;
	static Sheet sheet = null;

	static Connection conn = null;
	static ResultSet rs = null;
	static Statement st = null;

//	public static void main(String[] args)
//			throws IOException, InvalidFormatException, ClassNotFoundException, SQLException {
////		String filePath = "C:\\Users\\User\\22\\Create DB and TABLE\\"; // Windows 路徑
//		String filePath = "/Users/nicole/22/Create DB and TABLE/"; // Mac 路徑
//		new ExcelUtil().imp(filePath+"wine.xlsx");
////		List<String> list = Arrays.asList("wine", "SommelierChoice"); // 要匯出的table名
////		new ExcelUtil().exp(filePath + "wineOutput.xlsx", list);
//		System.out.println("done");
//	}

	public void imp(String file) throws IOException, InvalidFormatException, ClassNotFoundException, SQLException {

		fis = new FileInputStream(file);
		workbook = WorkbookFactory.create(fis);
		conn = DBConn.getConn();
		st = conn.createStatement();
		int sheetCount = workbook.getNumberOfSheets();

		for (int s = 0; s < sheetCount; s++) {
			tableName = workbook.getSheetName(s);
			sheet = workbook.getSheetAt(s);
			rowCount = sheet.getPhysicalNumberOfRows();

			for (int r = 1; r < rowCount; r++) { // row
				id = sheet.getRow(r).getCell(0).toString();
				if (id.isEmpty())
					break;
				sqlR = "select count(*) from " + tableName + " where id = '" + id + "'";
				System.out.println("sqlR = " + sqlR);
				rs = st.executeQuery(sqlR);

				if (rs.next() && rs.getInt(1) > 0) {

					updateSet = "";
					for (int c = 0; c < sheet.getRow(0).getPhysicalNumberOfCells(); c++) {
						updateSet += sheet.getRow(0).getCell(c) + "='"
								+ sheet.getRow(r).getCell(c).toString().replace("'", "''") + "',";
					}
					sqlU = "update " + tableName + " set " + comm.delFinalWord(updateSet) + " where id = '" + id + "'";
					System.out.println("sqlU = " + sqlU);
					st.executeUpdate(sqlU);

				} else {

					insKey = "";
					insVal = "";
					for (int c = 0; c < sheet.getRow(0).getPhysicalNumberOfCells(); c++) {
						insKey += sheet.getRow(0).getCell(c) + ",";
						insVal += "'" + sheet.getRow(r).getCell(c).toString().replace("'", "''") + "',";
					}
					sqlC = "insert into " + tableName + " (" + comm.delFinalWord(insKey) + ")" + " values("
							+ comm.delFinalWord(insVal) + ")";
					System.out.println("sqlC = " + sqlC);
					st.executeUpdate(sqlC);

				}
			}
		}
		conn.close();
		st.close();
		fis.close();
		workbook.close();

	}

	public void exp(String file, List<String> list) throws ClassNotFoundException, SQLException, IOException {

		workbook = new XSSFWorkbook();
		Row row = null;
		Cell cell = null;

		for (String str : list) {
			sqlR = "select * from " + str;
			conn = DBConn.getConn();
			st = conn.createStatement();
			rs = st.executeQuery(sqlR);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			sheet = workbook.createSheet(str);

			sheet.createFreezePane(1, 1); // 凍結窗格，第一列與第一欄
			Font font = workbook.createFont();
			font.setColor(XSSFFont.COLOR_RED); // 文字色

			CellStyle csTitle = workbook.createCellStyle();
			csTitle.setFont(font);
			csTitle.setFillForegroundColor(IndexedColors.YELLOW.getIndex()); // 背景色
			csTitle.setFillPattern((short) 1); // 加這行背景色才會出來
			csTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平置中
			csTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直置中

			int r = 0;
			row = sheet.createRow(r++);
			// 寫入欄位名稱
			for (int c = 0; c < columnCount; c++) {
				cell = row.createCell(c);
				cell.setCellValue(rsmd.getColumnName(c + 1));
				cell.setCellStyle(csTitle);
			}

			// 寫入內容
			while (rs.next()) {
				row = sheet.createRow(r++);
				for (int c = 0; c < columnCount; c++) {
					row.createCell(c).setCellValue(rs.getString(c + 1));
				}
			}

			// 自動調整欄寬
			for (int c = 0; c < columnCount; c++) {
				sheet.autoSizeColumn(c); // 自動調整欄寬
				int columnWidth = sheet.getColumnWidth(c);
				int maxWidth = 10000; // 最大欄寬
				int extraWidth = 500; // 多出寬度，避免覺得太擠
				if (columnWidth > maxWidth) {
					sheet.setColumnWidth(c, maxWidth);
				} else {
					sheet.setColumnWidth(c, columnWidth + extraWidth);
				}
			}
		}
		
		fos = new FileOutputStream(new File(file));
		workbook.write(fos);
		fos.flush();
		fos.close();

		conn.close();
		st.close();
		workbook.close();
		
	}

}