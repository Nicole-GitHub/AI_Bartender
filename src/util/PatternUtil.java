package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

	/**
	 * 判斷是否為電話號碼
	 * 例:02-2222-1234
	 * @param 市話
	 * @return
	 */
	public static boolean isTelephone(String tel) {
		Pattern patterns = Pattern.compile("\\d{2}?-\\d{4}?-\\d{4}?");
		Matcher matcher = patterns.matcher(tel);
		return matcher.matches();
	}
	
	/**
	 * 判斷是否為手機號碼
	 * 例:0922-123-456
	 * @param tel
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		
		Pattern patterns = Pattern.compile("\\d{4}?-\\d{3}?-\\d{3}?");
		Matcher matcher = patterns.matcher(mobile);
		return matcher.matches();
	}
}
