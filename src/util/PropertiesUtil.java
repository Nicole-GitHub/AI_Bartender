package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesUtil {
	
    private String properiesName = "conf/config.properties";

    public PropertiesUtil() {

    }
    
    public PropertiesUtil(String fileName) {
        this.properiesName = fileName;
    }
    
    /**
     * 取得設定檔屬性值
     * @param key
     * @return
     */
    public String readProperty(String key) {
        String value = "";
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName);
            Properties p = new Properties();
            p.load(is);
            value = p.getProperty(key);
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return value;
    }

    /**
     * 取得設定檔
     * @return
     */
    public Properties getProperties() {
        Properties p = new Properties();
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName);
            p.load(is);
        } catch (IOException e) {
			e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
				e.printStackTrace();
            }
        }
        return p;
    }

    /**
     * 寫入屬性檔
     * @param key
     * @param value
     */
    public void writeProperty(String key, String value) {
        InputStream is = null;
        OutputStream os = null;
        Properties p = new Properties();
        try {
            is = new FileInputStream(properiesName);
            p.load(is);
            os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(properiesName).getFile());

            p.setProperty(key, value);
            p.store(os, key);
            os.flush();
            os.close();
        } catch (Exception e) {
			e.printStackTrace();
        } finally {
            try {
                if (null != is) is.close();
                if (null != os) os.close();
            } catch (IOException e) {
				e.printStackTrace();
            }
        }

    }

	
	public static void main(String[] arg) {
		Properties p = new PropertiesUtil().getProperties();
		Enumeration<Object> e = p.keys();
		while(e.hasMoreElements()) {
			String key = (String)e.nextElement();
			System.out.println(key+"="+p.getProperty(key));
		}
	}

}
