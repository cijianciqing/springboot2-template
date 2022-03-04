package cj.springboot.template.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;


@Configuration
@EnableWebMvc//接管web mvc的配置
//@EnableScheduling
public class CJMvcConfig implements WebMvcConfigurer {


	@Resource
	private ThreadPoolTaskExecutor applicationTaskExecutor;

	//不通过controller,直接跳转
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

/*
* 以下为测试
* */
		registry.addViewController ("/test01").setViewName("/test/test01");


		//		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/demo/ckeditor401").setViewName("demo/ckeditor4Demo01");
		registry.addViewController("/demo/dataTablesDemo").setViewName("demo/dataTableDemo_ServerSide");
		registry.addViewController("/demo/index").setViewName("demo/index");
		registry.addViewController("/imooc-signIn").setViewName("imooc-signIn");

/*
 * 以下为正式环境
 * */
//		首页
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/index").setViewName("index");
//		登录页面
		registry.addViewController("/auth/signIn").setViewName("auth/signIn");
//		session invalid
		registry.addViewController("/session/invalid").setViewName("session/invalid");

		registry.addViewController("/error/404").setViewName("error/404");
	}

	/*静态资源设置*/
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//addResourceHandler     是指你想在url请求的路径
		//addResourceLocations   是图片存放的真实路径,可以是classpath:,也可以是file:
		registry.addResourceHandler("/cjThirdStatic/**").addResourceLocations("classpath:/static/vendor/");
		registry.addResourceHandler("/node_modules/**").addResourceLocations("classpath:/static/node_modules/");
		registry.addResourceHandler("/sb2Static/**").addResourceLocations("classpath:/static/sb2/");
		registry.addResourceHandler("/icon/**").addResourceLocations("classpath:/static/icon/");
		registry.addResourceHandler("/cjStatic/**").addResourceLocations("classpath:/static/cjStatic/");

	}

	/*
	*配置异步请求处理选项
	* 处理Callable请求
	* */
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		//处理 callable超时
		configurer.setDefaultTimeout(60*1000);
		/*
		* 处理{@link Callable} controller method
		 * */
		configurer.setTaskExecutor(applicationTaskExecutor);
//		configurer.registerCallableInterceptors(timeoutCallableProcessingInterceptor());
	}

	/*
	* 全局CORS配置
	*
	* 但是问题是，当你在项目中使用了该方法配置跨域问题后，再使用自定义的拦截器时，跨域问题的相关配置就会失效，请求依然会报跨域问题的错。
	原因是客户端请求经过的先后顺序问题，当服务端接收到一个请求时，该请求会先经过过滤器，然后进入拦截器中，
	* 然后再进入Mapping映射中的路径所指向的资源，所以跨域配置在mapping上并不起作用，返回的头信息中并没有配置的跨域信息，浏览器就会报跨域异常。
	* */
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//				.allowedOriginPatterns("*")
//				.allowedHeaders(CorsConfiguration.ALL)
//				.allowedMethods(CorsConfiguration.ALL)
//				.allowCredentials(true)
//				.maxAge(3600); // 1小时内不需要再预检（发OPTIONS请求）
//
//	}

	/*
	* 为了避免后端long类型【有的long有16为多。。。】传递到前端时，由于格式问题导致显示不正常
	* 进行全局配置
	*
	* 这种方式暂时禁用，因为会导致不长的long也会转换为String
	* 在需要转化的字段上添加： @JsonSerialize(using= ToStringSerializer.class)
	* */
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//		ObjectMapper objectMapper = new ObjectMapper();
//		/**
//		 * 序列换成json时,将所有的long变成string
//		 * 因为js中得数字类型不能包含所有的java long值
//		 */
//		SimpleModule simpleModule = new SimpleModule();
//		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//		simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
//
//		// 反序列化时忽略多余字段
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//		// 注册
//		objectMapper.registerModule(simpleModule);
//		jackson2HttpMessageConverter.setObjectMapper(objectMapper);
//		converters.add(jackson2HttpMessageConverter);
//	}


}
