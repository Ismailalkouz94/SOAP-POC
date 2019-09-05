package com.poc.soap;

import com.poc.soap.wsdl.Add;
import com.poc.soap.wsdl.AddResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@SpringBootApplication
public class SoapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoapApplication.class, args);
        try {
            WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
            Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();

            Add request = new Add();
            request.setIntA(5);
            request.setIntB(10);
            jaxb2Marshaller.setContextPath("com.poc.soap.wsdl");
            webServiceTemplate.setMarshaller(jaxb2Marshaller);
            webServiceTemplate.afterPropertiesSet();
            webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
            webServiceTemplate.afterPropertiesSet();
            AddResponse responseObj = (AddResponse) webServiceTemplate.marshalSendAndReceive("http://www.dneonline.com/calculator.asmx?WSDL", request, new SoapActionCallback("http://tempuri.org/Add"));
            System.out.println(responseObj.getAddResult());
        } catch (WebServiceClientException e) {
            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
