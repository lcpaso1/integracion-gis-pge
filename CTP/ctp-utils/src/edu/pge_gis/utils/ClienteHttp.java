package edu.pge_gis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.io.IOUtils;

public class ClienteHttp {
	
	public static String executePost(String url, String postParams) throws IOException{
		return executePostJava(url, postParams); 
	}

	public static String executePostJava(String url, String postParams) throws IOException{
		URL urlobj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) urlobj.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type","application/json");
		//add request header
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		OutputStream os = conn.getOutputStream();
		os.write(postParams.getBytes());
		os.flush();
//			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//			throw new RuntimeException("Failed : HTTP error code : "
//			+ conn.getResponseCode());
//			}
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String output;
		String result = "";
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			result += output;
		}
		 
		conn.disconnect();
		return result;
	}
	
	public static String executePostApache(String url, String postParams) throws IOException{
		
		String xml = null;
		PostMethod method = null;
		try {
			HttpClient httpClient = new HttpClient();
			method = new PostMethod(url);
			method.setRequestEntity(new StringRequestEntity(postParams));
			Header header = new Header("connection", "keep-alive");
			method.addRequestHeader(header);
			header = new Header("cache-control", "no-cache");
			method.addRequestHeader(header);
			header = new Header("content-length", "1935");
			method.addRequestHeader(header);
			header = new Header("origin", "chrome-extension://fdmmgilgnpjigdojojpjoooidkmcomcm");
			method.addRequestHeader(header);
			header = new Header("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");
			method.addRequestHeader(header);
			header = new Header("content-type", "text/plain;charset=UTF-8");
			method.addRequestHeader(header);
			header = new Header("accept", "*/*");
			method.addRequestHeader(header);
			header = new Header("accept-encoding", "gzip, deflate");
			method.addRequestHeader(header);
			header = new Header("accept-language", "es-ES,es;q=0.8");
			method.addRequestHeader(header);
			
			int httpStatusCode = httpClient.executeMethod(method);
			if (httpStatusCode == 200 || httpStatusCode == 204){
				//logHTTPStatusCode(httpStatusCode);
				InputStream response = method.getResponseBodyAsStream();
				System.out.println(" contenido mime:---"+method.getResponseHeaders());
				xml = new String(IOUtils.toByteArray(response),	method.getResponseCharSet());
				return xml;
			}
			else{
				throw new RuntimeException("HTTP code: "+httpStatusCode);
			}
		} catch (HttpException e) {
			e.printStackTrace();
			throw new RuntimeException("Error de http: "+e.getMessage(), e);
		} finally {
			method.releaseConnection();
		}
	}
	
	public static String executeGet(String url) throws IOException {

		String xml = null;
		GetMethod method = null;
		try {
			HttpClient httpClient = new HttpClient();
			method = new GetMethod(url);
			int httpStatusCode = httpClient.executeMethod(method);
			if (httpStatusCode == 200 || httpStatusCode == 204){
				//logHTTPStatusCode(httpStatusCode);
				InputStream response = method.getResponseBodyAsStream();
				System.out.println(" contenido mime:---"+method.getResponseHeaders());
				xml = new String(IOUtils.toByteArray(response),	method.getResponseCharSet());
				return xml;
			}
			else{
				throw new RuntimeException("HTTP code: "+httpStatusCode);
			}
		} catch (HttpException e) {
			e.printStackTrace();
			throw new RuntimeException("Error de http: "+e.getMessage(), e);
		} finally {
			method.releaseConnection();
		}
		
	}

	public static byte[] executeGetBinario(String url) throws IOException  {
		GetMethod method = null;
		try {
			HttpClient httpClient = new HttpClient();
			method = new GetMethod(url);
			int httpStatusCode = httpClient.executeMethod(method);
			if (httpStatusCode == 200 || httpStatusCode == 204){
			//logHTTPStatusCode(httpStatusCode);
				return method.getResponseBody();
			}
			else{
				throw new RuntimeException("HTTP code: "+httpStatusCode);
			}
		} catch (HttpException e) {
			e.printStackTrace();
			throw new RuntimeException("Error de http: "+e.getMessage(), e);
		} finally {
			method.releaseConnection();
		}
	}
	
	public static byte[] executeGetBinario(String url,	HashMap<String, String> parameters) {

		GetMethod method = null;
		try {
			HttpClient httpClient = new HttpClient();
			method = new GetMethod(url);
			if (parameters != null && parameters.size() > 0) {
				NameValuePair[] params = buildQueryParameters(parameters);
				method.setQueryString(params);
			}
			int httpStatusCode = httpClient.executeMethod(method);
			assert (httpStatusCode == 200 || httpStatusCode == 204);
			//logHTTPStatusCode(httpStatusCode);
			if(method.getResponseHeader("Content-Type").getValue().equals("image/png"))
				return method.getResponseBody();
			
			throw new Exception(new String(IOUtils.toByteArray(method.getResponseBodyAsStream()),
					method.getResponseCharSet()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return null;
	}
	
	public static String executeGet(String url,	HashMap<String, String> parameters) {

		String xml = null;
		GetMethod method = null;
		try {
			HttpClient httpClient = new HttpClient();
			method = new GetMethod(url);
			if (parameters != null && parameters.size() > 0) {
				NameValuePair[] params = buildQueryParameters(parameters);
				method.setQueryString(params);
			}
			int httpStatusCode = httpClient.executeMethod(method);
			assert (httpStatusCode == 200 || httpStatusCode == 204);
			//logHTTPStatusCode(httpStatusCode);
			InputStream response = method.getResponseBodyAsStream();
			System.out.println(" contenido mime:---"+method.getResponseHeader("contentType"));
			
			xml = new String(IOUtils.toByteArray(response),
					method.getResponseCharSet());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return xml;
	}

	public static String executeGet(String url,
			HashMap<String, String> parameters, HashMap<String, String> headers) {

		String xml = null;
		GetMethod method = null;
		try {
			HttpClient httpClient = new HttpClient();
			method = new GetMethod(url);
			if (parameters != null && parameters.size() > 0) {
				NameValuePair[] params = buildQueryParameters(parameters);
				method.setQueryString(params);
			}
			if (headers != null && headers.size() > 0) {
				setRequestHeaders(method, headers);
			}
			int httpStatusCode = httpClient.executeMethod(method);
			assert (httpStatusCode == 200 || httpStatusCode == 204);
			//logHTTPStatusCode(httpStatusCode);
			InputStream response = method.getResponseBodyAsStream();
			System.out.println(" contenido mime:---"+method.getResponseHeader("contentType"));
			xml = new String(IOUtils.toByteArray(response),
					method.getResponseCharSet());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return xml;
	}
	
	private static void setRequestHeaders(HttpMethod method,HashMap<String, String> headers) {
		if (headers != null && headers.size() > 0) {
			for (String s : headers.keySet()) {
				method.setRequestHeader(s, headers.get(s));
			}
		}
	}

	private static NameValuePair[] buildQueryParameters(HashMap<String, String> parameters) throws Exception {
		
		NameValuePair[] params = null;
		if (parameters != null) {
			params = new NameValuePair[parameters.size()];
			int i = 0;
			for (String s : parameters.keySet()) {
				params[i] = new NameValuePair(s, URIUtil.encodeQuery(parameters.get(s)));
				i++;
			}
		}
		return params;
	}
}
