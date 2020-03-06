package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateUtil u = new DateUtil();
	}

	/**
	 * 取得系統目前日期
	 * 格式: 2020-03-03
	 * 
	 * @return String
	 */
	public String getNowDate() {
		return LocalDate.now().toString();
	}

	/**
	 * 取得系統目前日期與時間
	 * 格式: 2020-03-03T17:42:56.882
	 * 
	 * @return String
	 */
	public String getNowDateTime() {
		return LocalDateTime.now().toString();
	}

	/**
	 * 取得系統目前時間資訊(自定回傳的日期格式) yy 西元年(後兩碼) yyyy 西元年(全四碼) MM 月 dd 日 hh 時(12小時制) HH
	 * 時(24小時制) mm 分 ss 秒 SS 毫秒(看要顯示幾碼就寫幾個S) E 星期幾
	 * 
	 * 其餘請參考:https://docs.oracle.com/javase/8/docs/api/
	 * 
	 * @param format 自定的日期時間格式
	 * @return String
	 */
	public String getNowDateTimeFormat(String format) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 字串轉日期
	 * 
	 * @param dateStr 日期字串
	 * @param symbol 日期字串間的分隔符號
	 * @return LocalDate
	 * @throws Exception
	 */
	public LocalDate chgStrToDate(String dateStr, String symbol) throws Exception {
		String[] str = dateStr.split(symbol);

		if (str.length != 3) {
			throw new Exception("輸入格式錯誤");
		} else {
			int[] i = new int[3];
			for (int n = 0; n < str.length; n++) {
				i[n] = Integer.parseInt(str[n]);
			}
			return LocalDate.of(i[0], i[1], i[2]);
		}
	}

	/**
	 * 兩個日期做比較(大於、小於、等於)
	 * 
	 * @param str1 日期1
	 * @param str2 日期2
	 * @param dateSymbol 日期字串間的分隔符號
	 * @param compareSymbol > (大於)、< (小於)、= (等於)
	 * @return boolean
	 * @throws Exception
	 */
	public boolean compare(String str1, String str2, String dateSymbol , String compareSymbol) throws Exception {

		LocalDate a = chgStrToDate(str1, dateSymbol);
		LocalDate b = chgStrToDate(str1, dateSymbol);
		if (compareSymbol.equals("<")) {
			return a.isBefore(b);
		} else if (compareSymbol.equals(">")) {
			return a.isAfter(b);
		} else if (compareSymbol.equals("=")) {
			return a.isEqual(b);
		} else {
			throw new Exception("compare第四個引數輸入錯誤");
		}
	}
	
	/**
	 * 與目前日期做比較(大於、小於、等於)
	 * 
	 * @param str 日期
	 * @param dateSymbol 日期字串間的分隔符號
	 * @param compareSymbol > (大於)、< (小於)、= (等於)
	 * @return boolean
	 * @throws Exception
	 */
	public boolean compareNow(String str, String dateSymbol ,String compareSymbol) throws Exception {
		LocalDate now = LocalDate.now();
		LocalDate a = chgStrToDate(str, dateSymbol);
		if (compareSymbol.equals("<")) {
			return a.isBefore(now);
		} else if (compareSymbol.equals(">")) {
			return a.isAfter(now);
		} else if (compareSymbol.equals("=")) {
			return a.isEqual(now);
		} else {
			throw new Exception("compare第三個引數輸入錯誤");
		}
	}
}
