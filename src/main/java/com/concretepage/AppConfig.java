package com.concretepage;

import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
@ComponentScan("com.concretepage") 
public class AppConfig extends WsConfigurerAdapter {

	@Bean(name = "students")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema studentsSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("StudentsPort");
		wsdl11Definition.setLocationUri("/soapws");
		wsdl11Definition.setTargetNamespace("http://concretepage.com/soap");
		wsdl11Definition.setSchema(studentsSchema);
		return wsdl11Definition;
	}
	@Bean
	public XsdSchema studentsSchema() {
		return new SimpleXsdSchema(new ClassPathResource("students.xsd"));
	}
	
	
	
	   @Bean
	    public SimplePasswordValidationCallbackHandler securityCallbackHandler(){
	        SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
	        Properties users = new Properties();
	        users.setProperty("admin", "secret");
	        callbackHandler.setUsers(users);
	        return callbackHandler;
	    }

	    @Bean
	    public Wss4jSecurityInterceptor securityInterceptor(){
	        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
	        securityInterceptor.setValidationActions("Timestamp UsernameToken");
	        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());
	        return securityInterceptor;
	    }

	    @Override
	    public void addInterceptors(List<EndpointInterceptor> interceptors) {
	    	//enable username and password authentication
	        interceptors.add(securityInterceptor());
	    }

}
