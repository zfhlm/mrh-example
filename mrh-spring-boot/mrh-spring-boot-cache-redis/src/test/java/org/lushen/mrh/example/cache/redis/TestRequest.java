package org.lushen.mrh.example.cache.redis;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import org.springframework.util.StreamUtils;

public class TestRequest {

	public static void main(String[] args) throws Exception {
		
		for(int i=0; i<5; i++) {
			Thread thread = new Thread(() -> {
				try {
					URL url = new URL("http://localhost:8888/sync");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					InputStream stream = connection.getInputStream();
					System.out.println(StreamUtils.copyToString(stream, StandardCharsets.UTF_8));
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			thread.start();
		}
		
		new CountDownLatch(1).await();
		
	}

}
