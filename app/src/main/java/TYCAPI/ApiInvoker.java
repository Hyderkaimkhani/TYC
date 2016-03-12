package TYCAPI;


import android.app.Activity;
import android.os.Looper;
import android.util.Log;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;


@SuppressWarnings("ALL")
public class ApiInvoker extends Activity {
	private static final ApiInvoker INSTANCE = new ApiInvoker();
	public static String msg;
	public static String responseString = null;
	private final Map<String, String> defaultHeaderMap = new HashMap<>();
	String tableurl = "http://www.luton.co:80/api/v2/live/_table/CfgDrvApp";

	public static String session_id = null;
	public static String response ;
	JSONObject object = null;


	public interface OnJSONResponseCallback {
		public void onJSONSuccessResponse(boolean success, JSONObject response) throws JSONException, ApiException;
		public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error);
	}


	public static Object deserialize(String json, String containerType, Class cls) throws ApiException {
		try{
			if("List".equals("")) {
				JavaType typeInfo = JsonUtil.getJsonMapper().getTypeFactory().constructCollectionType(List.class, cls);
				return (List<?>) JsonUtil.getJsonMapper().readValue(json, typeInfo);
			}
			else if(String.class.equals(cls)) {
				if(json != null && json.startsWith("\"") && json.endsWith("\"") && json.length() > 1)
					return json.substring(1, json.length() - 2);
				else
					return json;
			}
			else {
				return JsonUtil.getJsonMapper().readValue(json, cls);
			}
		}
		catch (IOException e) {
			throw new ApiException(500, e.getMessage());
		}
	}


	public Header[] getRequestHeaders(String headersRaw) {
		List<Header> headers = getRequestHeadersList(headersRaw);
		return headers.toArray(new Header[headers.size()]);
	}

	public List<Header> getRequestHeadersList(String headersRaw) {
		List<Header> headers = new ArrayList<Header>();
		//  String headersRaw = headersEditText.getText() == null ? null : headersEditText.getText().toString();

		if (headersRaw != null && headersRaw.length() > 3) {
			String[] lines = headersRaw.split("\\r?\\n");
			for (String line : lines) {
				try {
					int equalSignPos = line.indexOf('=');
					if (1 > equalSignPos) {
						throw new IllegalArgumentException("Wrong header format, may be 'Key=Value' only");
					}

					String headerName = line.substring(0, equalSignPos).trim();
					String headerValue = line.substring(1 + equalSignPos).trim();
					//Log.d(LOG_TAG, String.format("Added header: [%s:%s]", headerName, headerValue));

					headers.add(new BasicHeader(headerName, headerValue));
					// headers.add(new BasicHeader("X-DreamFactory-Application-Name","busybeeapi"));
				} catch (Throwable t) {
					//     Log.e(LOG_TAG, "Not a valid header line: " + line, t);
				}
			}
		}
		return headers;
	}

	public String jsonError(String res) throws JSONException {
		JSONObject jsonObj = new JSONObject(res);
		JSONObject jso = (JSONObject) jsonObj;
		//JSONObject company = (JSONObject) jso.get("error");
		JSONArray jsonArray = (JSONArray) jso.get("error");
		JSONObject val = (JSONObject) jsonArray.get(0);
		res = val.get("message").toString();
		return res;
	}

	public String Post(String url, String header, StringEntity entity, String contentType, final OnJSONResponseCallback callback) throws InterruptedException {


		AsyncHttpClient client = new AsyncHttpClient();
		client.post(this, url, getRequestHeaders(header), entity, contentType, new AsyncHttpResponseHandler(Looper.getMainLooper()) {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

				try {
					object = new JSONObject(new String(responseBody));
					response = new String(responseBody);
					Log.w("Response : ", new String(responseBody));

					callback.onJSONSuccessResponse(true, object);

					if (session_id == null) {
						session_id = object.get("session_id").toString();
					}


				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ApiException e) {
					e.printStackTrace();
				}


			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

				try {
					if (responseBody != null) {

						object = new JSONObject(new String(responseBody));
						response = new String(responseBody);

						callback.onJSONFailureResponse(false, object, statusCode, error);
					} else {
						callback.onJSONFailureResponse(false, object, statusCode, error);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});

		return response;

	}

	public void getResponse (String url, String header, RequestParams params, final OnJSONResponseCallback callback)
	{

		AsyncHttpClient client = new AsyncHttpClient();

		client.get(this, url, getRequestHeaders(header), params, new AsyncHttpResponseHandler(Looper.getMainLooper()) {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {

					object = new JSONObject(new String(responseBody));
					response = new String(responseBody);

				//	Log.w("Response : ", new String(responseBody));

					callback.onJSONSuccessResponse(true, object);


				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ApiException e) {
					e.printStackTrace();
				}
				//	Log.w("Response : ", new String(responseBody));
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				try {
					if (responseBody != null) {
						object = new JSONObject(new String(responseBody));
						response = new String(responseBody);
						Log.w("Response : ", new String(responseBody));

						callback.onJSONFailureResponse(false, object, statusCode, error);
					} else {
						callback.onJSONFailureResponse(false, object, statusCode, error);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void patch(String url, String header, StringEntity entity, String contentType, final OnJSONResponseCallback callback){

		AsyncHttpClient client = new AsyncHttpClient();

		client.patch(this, url, getRequestHeaders(header), entity, contentType, new AsyncHttpResponseHandler(Looper.getMainLooper()) {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					object = new JSONObject(new String(responseBody));
					response = new String(responseBody);
					Log.w("Response : ", new String(responseBody));
					callback.onJSONSuccessResponse(true, object);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ApiException e) {
					e.printStackTrace();
				}

			}


			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				try {
					if (responseBody != null) {
						object = new JSONObject(new String(responseBody));
						response = new String(responseBody);
						Log.w("Response : ", new String(responseBody));
						callback.onJSONFailureResponse(false, object, statusCode, error);
					}
					else {
						return;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

		});
	}

	public void delete(String url, String header, final OnJSONResponseCallback callback){

		AsyncHttpClient client = new AsyncHttpClient();

		client.delete(this, url, getRequestHeaders(header), new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					object = new JSONObject(new String(responseBody));
					response = new String(responseBody);
					Log.w("Response : ", new String(responseBody));
					callback.onJSONSuccessResponse(true, object);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ApiException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				try {
					object = new JSONObject(new String(responseBody));
					response = new String(responseBody);
					Log.w("Response : ", new String(responseBody));
					callback.onJSONFailureResponse(false, object, statusCode, error);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public boolean getUseSynchronousMode() {
				return false;
			}

		});

	}


}

