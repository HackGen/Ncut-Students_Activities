package idv.PN_Wu;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class Http {

	public static String strServerURL = "http://www.osa.ncut.edu.tw/2004/html/student";

	public static String getSA_List(int intPage) {
		String strResponse = null;

		URI uri;
		try {
			uri = new URI(String.format("%s/%s?%s=%d", strServerURL,
					"post.asp", "PAGE", intPage));
			HttpGet request = new HttpGet(uri);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				strResponse = response.getStatusLine().toString();
				Log.e("PN Wu: Http Error", strResponse);
			}
			strResponse = EntityUtils.toString(response.getEntity());
			// strResponse = new String(strResponse.getBytes("Big5"), "UTF-8");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strResponse;
	}

	public static String getSA_Information(String actid) {
		String strResponse = null;

		URI uri;
		try {
			uri = new URI(String.format("%s/%s?%s=%s", strServerURL,
					"student_newact2.asp", "actid", actid));
			HttpGet request = new HttpGet(uri);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				strResponse = response.getStatusLine().toString();
				Log.e("PN Wu: Http Error", strResponse);
			}
			strResponse = EntityUtils.toString(response.getEntity());
			// strResponse = new String(strResponse.getBytes("Big5"), "UTF-8");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strResponse;
	}
}
