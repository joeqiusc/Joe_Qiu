package util;

import com.google.gson.Gson;

public class GsonUtils {

	private static final Gson GSON = new Gson();
	
	public static final Gson getInstance() {
		return GSON;
	}
}
