package util;

public class CommonUtil {
	
	public String getString(Object param) {
		String str;
		if (isBlank(param)) {
			str = "";
		} else {
			str = param.toString().trim();
		}
		return str;
	}

	public int StringToInt(Object param) {
		int i;
		if (isBlank(param)) {
			i = 0;
		} else {
			i = Integer.parseInt(param.toString().trim());
		}
		return i;
	}
	
	public int StringToDoubleToInt(Object param) {
		int i;
		if (isBlank(param)) {
			i = 0;
		} else {
			i = (int) Double.parseDouble(param.toString().trim());
		}
		return i;
	}
	public Double StringToDouble(Object param) {
		Double i;
		if (isBlank(param)) {
			i = 0.0;
		} else {
			i = Double.parseDouble(param.toString().trim());
		}
		return i;
	}
	public boolean isBlank(Object param) {
		if (param == null || param.toString().trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
