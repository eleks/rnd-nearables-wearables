package com.eleks.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.eleks.model.UserCredentials;
import com.eleks.utils.Constants;

@Service
public class HttpService {
	
	public HttpResponse executeRequest(String url, UserCredentials userCredentials) throws ClientProtocolException, IOException {
    	final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new NTCredentials(userCredentials.getUserName(), userCredentials.getPassword(), null, Constants.DOMAIN));
		final HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
    	
    	final HttpGet httpGet = new HttpGet(url);
		final HttpResponse httpResponse = httpClient.execute(httpGet);
		
		return httpResponse;
	}
	
	public HttpResponse executeRequest(String url, List<BasicNameValuePair> params, UserCredentials userCredentials) throws ClientProtocolException, IOException {
    	return executeRequest(url + URLEncodedUtils.format(params, "utf-8"), userCredentials);
	}
	
}
