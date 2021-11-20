# mrh-netty-http-server

	使用 netty 实现的一个简单的 http server
	
	springboot方式启动：
	
		①，在application.properties中添加配置：
		
			org.lushen.mrh.http.server.port=8080
			org.lushen.mrh.http.server.acceptors=2
			org.lushen.mrh.http.server.workers=100
		
		②，启动springboot应用：
		
			@SpringBootApplication
			@ComponentScan(basePackageClasses=HttpServerRunner.class)
			public class HttpServerRunner {
			
				public static void main(String[] args) throws Exception {
					SpringApplication application = new SpringApplication(HttpServerRunner.class);
					application.setWebApplicationType(WebApplicationType.NONE);
					application.run(args);
					new CountDownLatch(1).await();
				}
			
			}
	
	纯Java方式启动：
	
		public static void main(String[] args) throws Exception {
	
			List<HttpFilter> filters = new ArrayList<HttpFilter>();
			filters.add(new HttpDebugFilter());
			filters.add(new HttpAllowMethodFilter(new HttpMethod[]{HttpMethod.POST}));
	
			HttpServerConfig serverConfig = new HttpServerConfig();
			serverConfig.setPort(8080);
	
			HttpServer httpServer = new HttpServer(serverConfig, filters, new DemoBusinessHandler());
			httpServer.run();
			httpServer.close();
	
		}


