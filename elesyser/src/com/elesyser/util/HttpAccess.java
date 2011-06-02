package com.elesyser.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class HttpAccess {
	private static HttpAccess http;
	private DefaultHttpClient client;
	
	private HttpAccess() 
	{
		client = new DefaultHttpClient();
	}
	
	public static HttpAccess getInstance() 
	{
		if (http == null) 
		{
			http = new HttpAccess();
		}
		return http;
	}
	
	public void clear() 
	{
		client = new DefaultHttpClient();
	}
	
	public String get(String URL) throws Exception 
	{
		String resultString;
		HttpGet sourceaddr= new HttpGet(URL);
		sourceaddr.addHeader("Content-Type", "text/html;charset=UTF-8");
		try {
			HttpResponse httpResponse = client.execute(sourceaddr);
			if (httpResponse.getStatusLine().getStatusCode()==200) 
			{
				resultString = readstream(httpResponse.getEntity().getContent());
			}
			else {
				throw new Exception("can't connect the network");
			}
			return resultString.toString();
		}catch (Exception e) {
			throw e;
		}
	}
	
	public String post(String URL,List <NameValuePair> params) throws Exception 
	{
		String resultString;
		try {
			HttpPost httpRequest = new HttpPost(URL);
//			httpRequest.setEntity(new UrlEncodedFormEntity(params, "GB2312"));
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpRequest.addHeader("Content-Type", "text/html;charset=UTF-8");
			HttpResponse httpResponse = client.execute(httpRequest);
			Header h = httpResponse.getFirstHeader("Location");
			if(httpResponse.getStatusLine().getStatusCode() == 200)    
			{   
				resultString= readstream(httpResponse.getEntity().getContent());
				//resultString = new String(resultString.getBytes("gb2312"),"gbk");
			}else {
				throw new Exception("can't connect the network");
			}
			return resultString;
		}catch (Exception e) {
			throw e;
		}
	}
	
	public boolean checknetwork(Context context) 
	{
		ConnectivityManager connectivity = (ConnectivityManager) context  
		.getSystemService(Context.CONNECTIVITY_SERVICE);  
		if (connectivity != null) {  
			NetworkInfo info = connectivity.getActiveNetworkInfo();  
			if (info != null) {  
				if (info.getState() == NetworkInfo.State.CONNECTED) {  
					return true;  
				}  
			}  
		}  
		return false;  
	}
	
	private String readstream(InputStream in) 
	{
		StringBuffer resultString = new StringBuffer() ;
		try {
			//BufferedReader inbuff = new BufferedReader(new InputStreamReader(in,"GB2312"));
			BufferedReader inbuff = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String line = "";
			while ((line = inbuff.readLine()) != null){
				resultString.append('\n');
				resultString.append(line);
			}

		}catch (Exception e) {
		}
		return resultString.toString();
	}

}
