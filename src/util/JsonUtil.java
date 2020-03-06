package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

import model.BartenderQuestion;

public class JsonUtil {
	private String filePathName = new PropertiesUtil().readProperty("jsonFilePath");

	private BartenderQuestion readJson() {

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePathName));
			Object data = ois.readObject();
			ois.close();
			return (BartenderQuestion) data;
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void saveJson() {

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePathName));
			BartenderQuestion BQ = new BartenderQuestion();

			List<String> dish = Arrays.asList("牛內", "豬內", "雞肉", "海鮮", "蔬食");
			List<String> purpose = Arrays.asList("長輩送禮", "獨處時光", "升官/加薪/通過考試", "失戀/失業/失意", "Party/情人送禮/紀念日");
			List<String> type = Arrays.asList("紅酒", "白酒", "粉紅酒", "氣泡酒");
			List<String> grape = Arrays.asList("Southern Rhone Red Blend", "Semillon", "Rare Rose Blend",
					"Glera (Prosecco)");
			List<String> place = Arrays.asList("Rhone, France", "New South Wales, Australia", "Rioja, Spain",
					"Prosecco, Veneto, Italy");
			BQ.setDish(dish);
			BQ.setPurpose(purpose);
			BQ.setType(type);
			BQ.setGrape(grape);
			BQ.setPlace(place);

			oos.writeObject(BQ);
			oos.close();
			System.out.println("Product Saved");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JsonUtil ju = new JsonUtil();
		ju.saveJson();
		BartenderQuestion bq = ju.readJson();
		System.out.println("========== dish ==========");
		for(String str : bq.getDish()) {
			System.out.println("dish="+str);
		}

		System.out.println("========== Purpose ==========");
		for(String str : bq.getPurpose()) {
			System.out.println("Purpose="+str);
		}

		System.out.println("========== Type ==========");
		for(String str : bq.getType()) {
			System.out.println("Type="+str);
		}

		System.out.println("========== Grape ==========");
		for(String str : bq.getGrape()) {
			System.out.println("Grape="+str);
		}

		System.out.println("========== Place ==========");
		for(String str : bq.getPlace()) {
			System.out.println("Place="+str);
		}
	}
}
