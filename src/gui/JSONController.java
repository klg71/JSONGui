package gui;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class JSONController {

	private String host;

	public JSONController(String host) {
		this.host = host;
	}

	public JSONController() {
		this.host = ("http://127.0.0.1:8001");
	}

	private JSONObject sendRequest(JSONObject data) throws IOException {
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(this.host);

		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("json", data.toString()));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		// Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
		StringBuilder result = new StringBuilder();
		in.lines().forEach(new Consumer<String>() {

			@Override
			public void accept(String t) {
				result.append(t);
			}
		});
		System.out.println(result.toString());
		return new JSONObject(result.toString());
	}

	public JSONObject getStartPage() throws IOException {
		URL url = null;
		try {
			url = new URL(this.host);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URLConnection yc = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		StringBuilder result = new StringBuilder();
		in.lines().forEach(new Consumer<String>() {

			@Override
			public void accept(String t) {
				result.append(t);
			}
		});
		return new JSONObject(result.toString());
	}

	public JSONObject navigate(JSONObject navigation) throws IOException {
		System.out.println(navigation);
		return sendRequest(navigation);
	}

	public JSONObject act(JSONObject action) throws IOException {
		return sendRequest(action);
	}
}
