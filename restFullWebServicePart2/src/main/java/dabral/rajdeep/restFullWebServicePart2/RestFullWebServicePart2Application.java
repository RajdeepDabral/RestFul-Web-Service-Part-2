package dabral.rajdeep.restFullWebServicePart2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Locale;

@SpringBootApplication
//@Configuration
@EnableSwagger2
public class RestFullWebServicePart2Application {
	public static final ApiInfo API_INFO =  new ApiInfo(
			"User Management" , "Perform CURD Operations related to User" , "1.0",
			"urn:tos" , "Rajdeep Dabral","Apache" , "www.apache.org");

	public static void main(String[] args) {
		SpringApplication.run(RestFullWebServicePart2Application.class, args);
	}

	@Bean
	public LocaleResolver localeResolver(){
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(API_INFO );
	}
}
