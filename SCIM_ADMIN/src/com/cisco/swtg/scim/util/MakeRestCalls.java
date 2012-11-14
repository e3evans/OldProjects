package com.cisco.swtg.scim.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

public class MakeRestCalls {
	
X509TrustManager trustManager = new X509TrustManager() {
		
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			
		}
		
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			
		}
	};
	TrustManager[] trustAllCerts = new TrustManager[] {trustManager};
	
	
	static String obssoCookie =
			"ObSSOCookie=grPcb%2BGawkXOdm8UCpZNVY64QgvhkRhRjM%2FyY16XcoMfHab0tIqNtEDgVN501n%2FfrrsBMGlvj8mRg8wJf%2FCiyuJTaGe3ki72HvDoI%2BxQJ1Lpt7w0veLVr4q9Ws4Jf9Wt5B9bVJ6z5J0UkfNbeyEJJxmeB8l8bqAWC221YB1V3CUBFggSxBg4WtLVRRpi77Il6CY1ngiTUtdOM%2B%2FjPA51%2BEx%2BDplBthXnUQ9NxUNhg7Ys1cBJDXaiIlfRVmApPrGCUcNDSqTep1CR5onyJBMgMBZcmACfz5svoyEeujcRfhLBlioHSNvH2ucWaMJhkv3OyuJaLN06ghcO%2FbzYB4H8MakVFTur4caZd67yOgRvEko%3D"; 
	static String baseUrl =
			"https://wsgx-dev.cisco.com"; 
//	http://tools-dev.cisco.com/swtg/pyout2/SnoopServlet
	
	static boolean[] json = new boolean[]{false, true};
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		String fileName = null;
		
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		StringBuilder stringBuilder = new StringBuilder();
		String str = null;
		while ((str = reader.readLine()) != null) {
			stringBuilder.append(str + "\n");
		}
		
		
		
		String _urls ="/collaboration/sc/communities/v1/threads/2014";
		
		String[][] urlParameters = {{}};
		
		String[][] urlHeaders = {{}};
		
	}
	
	
	
	public static String makeGetCall(String url, String[][] headers, String[][] param) throws IOException,
	ClientProtocolException {
		DefaultHttpClient httpclient = createHttpClient();
	
		String additionalUri = "";
		
		StringBuilder _curlScriptBuilder = new StringBuilder("curl -k -v --user PortKeyTest.gen:p0rtk3y5  --compressed ");

		if(param != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for(String[] _param:param) {
				nvps.add(new BasicNameValuePair(_param[0], _param[1]));

			}
			additionalUri += "?" + URLEncodedUtils.format(nvps, "UTF-8");
		}
		
		

		HttpGet httppost = new HttpGet(url + additionalUri);
		
		for(String[] _header:headers) {
			httppost.addHeader(_header[0], _header[1]);
			_curlScriptBuilder.append("-H \"" + _header[0] + ": " + _header[1] + "\"  ");
		}
		
		_curlScriptBuilder.append(" --request GET  " + url + additionalUri);
		
//		System.out.println(_curlScriptBuilder);
//		httppost..setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
//        httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
//		 ResponseHandler<String> responseHandler = new BasicResponseHandler();
////         HttpResponse response = httpclient.execute(httppost);
//// 		Header[] headers = response.getAllHeaders();
//// 		for(Header header: headers) {
//// 			System.out.println(header.getName() + ":" + header.getValue());
//// 		}
// 		String responseBody = httpclient.execute(httppost, responseHandler);
         
////         responseHandler.
//         System.out.println("----------------------------------------");
//         System.out.println(responseBody);
//         System.out.println("----------------------------------------");
		
		// Execute the request
		 HttpResponse response = httpclient.execute(httppost);
		 
		 // Get hold of the response entity
		 HttpEntity entity = response.getEntity();
		 
		 StringBuilder responseTextBu = new StringBuilder();
		 
		 if (entity != null) {
		     InputStream instream = entity.getContent();
		     try {

		         BufferedReader reader = new BufferedReader(
		                 new InputStreamReader(instream));
		         // do something useful with the response
//		         System.out.println(reader.readLine());
		         
		        String aLine = null;
		 		while((aLine = reader.readLine()) != null) {
		 			responseTextBu.append(aLine + "\n");
		 		}

		     } catch (IOException ex) {

		         // In case of an IOException the connection will be released
		         // back to the connection manager automatically
		         throw ex;

		     } catch (RuntimeException ex) {

		         // In case of an unexpected exception you may want to abort
		         // the HTTP request in order to shut down the underlying
		         // connection and release it back to the connection manager.
		    	 httppost.abort();
		         throw ex;

		     } finally {

		         // Closing the input stream will trigger connection release
		         instream.close();

		     }

		     // When HttpClient instance is no longer needed,
		     // shut down the connection manager to ensure
		     // immediate deallocation of all system resources
		     httpclient.getConnectionManager().shutdown();
		 }
//         
// 		System.out.println(responseTextBu);
		return responseTextBu.toString();
// 		return _curlScriptBuilder.toString();
	}
	
	
	@SuppressWarnings("unused")
	public static String makeDeleteCall(String url, String[][] headers, String[][] param, String content) throws IOException,
	ClientProtocolException {
		
		DefaultHttpClient httpclient = createHttpClient();
		HttpDelete httppost = new HttpDelete(url);
		StringBuilder _curlScriptBuilder = new StringBuilder("curl -k -v ");

		
		for(String[] _header:headers) {
			httppost.addHeader(_header[0], _header[1]);
			_curlScriptBuilder.append("-H \"" + _header[0] + ": " + _header[1] + "\"  ");
		}
		
		_curlScriptBuilder.append(" --request DELETE  " + url);
//        httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		 ResponseHandler<String> responseHandler = new BasicResponseHandler();
//         HttpResponse response = httpclient.execute(httppost);


		// Execute the request
				 HttpResponse response = httpclient.execute(httppost);
				 
				 // Get hold of the response entity
				 HttpEntity entity = response.getEntity();
				 
				 StringBuilder responseTextBu = new StringBuilder();
				 
				 if (entity != null) {
				     InputStream instream = entity.getContent();
				     try {

				         BufferedReader reader = new BufferedReader(
				                 new InputStreamReader(instream));
				         // do something useful with the response
//				         System.out.println(reader.readLine());
				         
				        String aLine = null;
				 		while((aLine = reader.readLine()) != null) {
				 			responseTextBu.append(aLine + "\n");
				 		}

				     } catch (IOException ex) {

				         // In case of an IOException the connection will be released
				         // back to the connection manager automatically
				         throw ex;

				     } catch (RuntimeException ex) {

				         // In case of an unexpected exception you may want to abort
				         // the HTTP request in order to shut down the underlying
				         // connection and release it back to the connection manager.
				    	 httppost.abort();
				         throw ex;

				     } finally {

				         // Closing the input stream will trigger connection release
				         instream.close();

				     }

				     // When HttpClient instance is no longer needed,
				     // shut down the connection manager to ensure
				     // immediate deallocation of all system resources
				     httpclient.getConnectionManager().shutdown();
				 }
//		         
//		 		System.out.println(responseTextBu);
				return responseTextBu.toString();
	}
	
	
	@SuppressWarnings("unused")
	public static String makePostCall(String url, String[][] headers, String[][] param, String requestContent) throws IOException,
	ClientProtocolException {
		
		DefaultHttpClient httpclient = createHttpClient();
		StringBuilder _curlScriptBuilder = new StringBuilder("curl -k -v --compressed ");

		
		HttpPost httppost = new HttpPost(url);
		for(String[] _header:headers) {
			httppost.addHeader(_header[0], _header[1]);
			_curlScriptBuilder.append("-H \"" + _header[0] + ": " + _header[1] + "\"  ");
		}
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(param != null) {
			for(String[] _param:param) {
				nvps.add(new BasicNameValuePair(_param[0], _param[1]));
			}
		}
		
		if(nvps.size() > 0) {
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		}

		if(requestContent != null) {
			httppost.setEntity(new StringEntity(requestContent, HTTP.UTF_8));
			_curlScriptBuilder.append("--data  \"" + requestContent + "\"  ");
		}
		
		
		_curlScriptBuilder.append(" --request POST  " + url);
		 ResponseHandler<String> responseHandler = new BasicResponseHandler();
		// Execute the request
				 HttpResponse response = httpclient.execute(httppost);
				 
				 // Get hold of the response entity
				 HttpEntity entity = response.getEntity();
				 
				 StringBuilder responseTextBu = new StringBuilder();
				 
				 if (entity != null) {
				     InputStream instream = entity.getContent();
				     try {

				         BufferedReader reader = new BufferedReader(
				                 new InputStreamReader(instream));
				         // do something useful with the response
//				         System.out.println(reader.readLine());
				         
				        String aLine = null;
				 		while((aLine = reader.readLine()) != null) {
				 			responseTextBu.append(aLine + "\n");
				 		}

				     } catch (IOException ex) {

				         // In case of an IOException the connection will be released
				         // back to the connection manager automatically
				         throw ex;

				     } catch (RuntimeException ex) {

				         // In case of an unexpected exception you may want to abort
				         // the HTTP request in order to shut down the underlying
				         // connection and release it back to the connection manager.
				    	 httppost.abort();
				         throw ex;

				     } finally {

				         // Closing the input stream will trigger connection release
				         instream.close();

				     }

				     // When HttpClient instance is no longer needed,
				     // shut down the connection manager to ensure
				     // immediate deallocation of all system resources
				     httpclient.getConnectionManager().shutdown();
				 }
//		         
//		 		System.out.println(responseTextBu);
				return responseTextBu.toString();
 		
// 		return _curlScriptBuilder.toString();
         
	}



	private static DefaultHttpClient createHttpClient() {
		DefaultHttpClient httpclient = new ContentEncodingHttpClient();
		 

		httpclient.addRequestInterceptor(new HttpRequestInterceptor() {

            public void process(
                    final HttpRequest request,
                    final HttpContext context) throws HttpException, IOException {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                    request.addHeader("","");
                }
            }

        });
		
//		httpclient.addResponseInterceptor(new ResponseContent());
		 
		 httpclient.addResponseInterceptor(new HttpResponseInterceptor() {

             public void process(
                     final HttpResponse response,
                     final HttpContext context) throws HttpException, IOException {
                 HttpEntity entity = response.getEntity();
                 Header ceheader = entity.getContentEncoding();
                 if (ceheader != null) {
                     HeaderElement[] codecs = ceheader.getElements();
                     for (int i = 0; i < codecs.length; i++) {
                    	 System.out.println(codecs[i].getName());
                         if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                             response.setEntity(
                                     new GzipDecompressingEntity(response.getEntity()));
                             return;
                         }
                     }
                 }
             }

         });
		return httpclient;
	}
	
	
	@SuppressWarnings("unused")
	public static String makePutCall( String url, String[][] headers, String requestContent) throws IOException,
	ClientProtocolException {
		
		DefaultHttpClient httpclient = createHttpClient();
		 
		 
		StringBuilder _curlScriptBuilder = new StringBuilder("curl -k -v --compressed ");

		HttpPut httppost = new HttpPut(url);
		for(String[] _header:headers) {
			httppost.addHeader(_header[0], _header[1]);
			_curlScriptBuilder.append("-H \"" + _header[0] + ": " + _header[1] + "\"  ");
		}
		
		if(requestContent != null) {
			httppost.setEntity(new StringEntity(requestContent, HTTP.UTF_8));
			_curlScriptBuilder.append("--data  \"" + requestContent + "\"  ");
		}
		 ResponseHandler<String> responseHandler = new BasicResponseHandler();
		 
		 _curlScriptBuilder.append(" --request PUT  " + url);
//         HttpResponse response = httpclient.execute(httppost);
		// Execute the request
				 HttpResponse response = httpclient.execute(httppost);
				 
				 // Get hold of the response entity
				 HttpEntity entity = response.getEntity();
				 
				 StringBuilder responseTextBu = new StringBuilder();
				 
				 System.out.println("Status code: " + response.getStatusLine().getStatusCode());
				 
				 
				 
				 if (entity != null) {
				     InputStream instream = entity.getContent();
				     try {

				         BufferedReader reader = new BufferedReader(
				                 new InputStreamReader(instream));
				         // do something useful with the response
//				         System.out.println(reader.readLine());
				         
				        String aLine = null;
				 		while((aLine = reader.readLine()) != null) {
				 			responseTextBu.append(aLine + "\n");
				 		}

				     } catch (IOException ex) {

				         // In case of an IOException the connection will be released
				         // back to the connection manager automatically
				         throw ex;

				     } catch (RuntimeException ex) {

				         // In case of an unexpected exception you may want to abort
				         // the HTTP request in order to shut down the underlying
				         // connection and release it back to the connection manager.
				    	 httppost.abort();
				         throw ex;

				     } finally {

				         // Closing the input stream will trigger connection release
				         instream.close();

				     }

				     // When HttpClient instance is no longer needed,
				     // shut down the connection manager to ensure
				     // immediate deallocation of all system resources
				     httpclient.getConnectionManager().shutdown();
				 }
//		         
//		 		System.out.println(responseTextBu);
				return responseTextBu.toString();
	}
	
	static class GzipDecompressingEntity extends HttpEntityWrapper {

        public GzipDecompressingEntity(final HttpEntity entity) {
            super(entity);
        }

        @Override
        public InputStream getContent()
            throws IOException, IllegalStateException {

            // the wrapped entity's getContent() decides about repeatability
            InputStream wrappedin = wrappedEntity.getContent();

            return new GZIPInputStream(wrappedin);
        }

        @Override
        public long getContentLength() {
            // length of ungzipped content is not known
            return -1;
        }

    }
	
	

}
