package walmart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class WalmartConnection {

	public SearchResults createWalmartConnection(String search)
			throws IOException {

		// connect to walmart online
	
		URL url = new URL(
				"http://api.walmartlabs.com/v1/search?apiKey=rfdvz63d3vvqwcwpc2tvh6zr&lsPublisherId"
						+ "=AhuvaFoxman&numItems=25&query=" + search);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// request
		InputStream in = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		Gson gson = new Gson();
		 SearchResults items = gson.fromJson(reader, SearchResults.class);
		
		return items;
	
	}
}
