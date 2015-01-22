package edu.pge_gis.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.io.IOUtils;

public class ClienteHttp {

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
