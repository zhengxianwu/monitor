package com.monitor.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class main {
	public static void main(String[] args) {
		String uri = "http://localhost:9200/metricbeat-6.5.0-2019.01.14/_search?size=1";
//		try {
//			String request = "";
//			String response = getData(url, request);
//			System.out.println(response);
//
//			JSONObject root = new JSONObject();
//
//			JSONObject jsonObject = root.getJSONObject("data");
//
//			String token = jsonObject.getString("access_token");
//
//			System.out.println("token=" + token);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String s = "[{usersid:322,LevelID:'',permission:'1,2'},{usersid:324,LevelID:5,permission:'2,5,9'}]";
//		String httpRequest = JsonTest(uri, "GET");
//		System.out.println(httpRequest);
//		JSONArray fromObject = JSONArray.fromObject(s);
//		
//		for(int i = 0 ; i < fromObject.size();i++) {
//			JSONObject jsonObject = fromObject.getJSONObject(i);
//			System.out.println(jsonObject.toString());
//		}

//		System.out.println(object.toString());
//		System.out.println(httpRequest);

//		  // 启动一个本地节点，并加入子网内的ES集群
//        Node node = nodeBuilder()
//                    .clusterName("elasticsearch") // 要加入的集群名为elasticsearch
//                    // .client(true) //如果设置为true，则该节点不会保存数据
//                    .data(true) // 本嵌入式节点可以保存数据
//                    .node(); // 构建并启动本节点
// 
//        // 获得一个Client对象，该对象可以对子网内的“elasticsearch”集群进行相关操作。
//        Client client = node.client();
//
//		Settings settings = ImmutableSettings.settingsBuilder()
//				// 指定集群名称
//				.put("cluster.name", "elasticsearch").build();
//				// 探测集群中机器状态
////				.put("client.transport.sniff", true).build();
//
//		TransportClient client = new TransportClient(settings)
//				.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
//		
//		
//		System.out.println(client.toString());
//
//		String index = "blogs";
//		String type = "log";
//		 GetResponse getResponse = client
//                 .prepareGet()   // 准备进行get操作，此时还有真正地执行get操作。（与直接get的区别）
//                 .setIndex(index)  // 要查询的
//                 .setType(type)
//                 .setId("1")
//                 .get();
//		 String sourceAsString = getResponse.getSourceAsString();
//		 System.out.println(sourceAsString);


	}


//	public static String JsonTest(String requestUrl, String requestMethod) {
//		JSONObject jsonObject = null;
//		StringBuffer buffer = new StringBuffer();
//		try {
//
//			URL url = new URL(requestUrl);
//			// http协议传输
//			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//
//			httpUrlConn.setDoOutput(true);
//			httpUrlConn.setDoInput(true);
//			httpUrlConn.setUseCaches(false);
//			// 设置请求方式（GET/POST）
//			httpUrlConn.setRequestMethod(requestMethod);
//
//			if ("GET".equalsIgnoreCase(requestMethod))
//				httpUrlConn.connect();
//			// 将返回的输入流转换成字符串
//			InputStream inputStream = httpUrlConn.getInputStream();
//			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//			String str = null;
//			while ((str = bufferedReader.readLine()) != null) {
//				buffer.append(str);
//			}
//			bufferedReader.close();
//			inputStreamReader.close();
//			// 释放资源
//			inputStream.close();
//			inputStream = null;
//			httpUrlConn.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return buffer.toString();
//	}
//
//	public static JSONObject httpRequest(String requestUrl, String requestMethod) {
//		JSONObject jsonObject = null;
//		StringBuffer buffer = new StringBuffer();
//		try {
//
//			URL url = new URL(requestUrl);
//			// http协议传输
//			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//
//			httpUrlConn.setDoOutput(true);
//			httpUrlConn.setDoInput(true);
//			httpUrlConn.setUseCaches(false);
//			// 设置请求方式（GET/POST）
//			httpUrlConn.setRequestMethod(requestMethod);
//
//			if ("GET".equalsIgnoreCase(requestMethod))
//				httpUrlConn.connect();
//			// 将返回的输入流转换成字符串
//			InputStream inputStream = httpUrlConn.getInputStream();
//			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//			String str = null;
//			while ((str = bufferedReader.readLine()) != null) {
//				buffer.append(str);
//			}
//			bufferedReader.close();
//			inputStreamReader.close();
//			// 释放资源
//			inputStream.close();
//			inputStream = null;
//			httpUrlConn.disconnect();
//			jsonObject = JSONObject.fromObject(buffer.toString());
//			System.out.println(buffer.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return jsonObject;
//	}
//
//	public static String getData(String url, String request) throws UnsupportedEncodingException, IOException {
//		String charset = "UTF-8";
//		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//		HttpURLConnection connect = (HttpURLConnection) (new URL(url).openConnection());
//		connect.setRequestMethod("GET");
//		connect.setDoOutput(true);
//		connect.setConnectTimeout(1000 * 10);
//		connect.setReadTimeout(1000 * 80);
//		connect.setRequestProperty("ContentType", "application/json"); // 采用通用的URL百分号编码的数据MIME类型来传参和设置请求头
//		connect.setDoInput(true);
//		// 连接
//		connect.connect();
//		// 发送数据
//		connect.getOutputStream().write(request.getBytes(charset));
//		// 接收数据
//		int responseCode = connect.getResponseCode();
//		if (responseCode == HttpURLConnection.HTTP_OK) {
//			InputStream in = connect.getInputStream();
//			byte[] data = new byte[1024];
//			int len = 0;
//			while ((len = in.read(data, 0, data.length)) != -1) {
//				outStream.write(data, 0, len);
//			}
//			in.close();
//		}
//		// 关闭连接
//		connect.disconnect();
//		String response = outStream.toString("UTF-8");
//		return response;
//	}

}
