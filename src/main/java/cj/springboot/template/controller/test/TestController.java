package cj.springboot.template.controller.test;

import cj.springboot.template.util.exceptionHandler.entity.CJErrorEntity;
import cj.springboot.template.ws.ArrayOfString;
import cj.springboot.template.ws.IpAddressSearchWebService;
import cj.springboot.template.ws.IpAddressSearchWebServiceSoap;
import cn.hutool.system.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.SourceExtractor;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

//import javax.validation.Valid;

//import javax.validation.Valid;

@Slf4j
@RestController
public class TestController {

    /*
    * 测试RestTemplate
    * */
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/tetRestTemplate")
    public String tetRestTemplate(){
        log.info("cj.springboot.template.controller.test.TestController.tetRestTemplate.......");
        String forObject = restTemplate.getForObject("http://172.16.18.25:8083/wiki/tttt", String.class);
        log.info(forObject);
        return "Hello CJ Test .....";
    }

    /*
    * 使用RestTemplate调用webservice接口
    * 但并未实现Obj-->XML , XML --> Obj
    * */
    @RequestMapping("/tetRestTemplate02")
    public String tetRestTemplate02(){
        // 创建一个请求头对象
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("text/xml;charset=UTF-8");
        // 设置请求头对象contentTyp的为text/xml;charset=UTF-8
        headers.setContentType(type);


        // 将请求参数进行封装并进行远程接口服务调用
        // 构造webservice请求参数
        StringBuffer soapRequestData = new StringBuffer("");
        soapRequestData.append(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://WebXml.com.cn/\">");
        soapRequestData.append("<soapenv:Header>");
        soapRequestData.append("</soapenv:Header>");

        soapRequestData.append("<soapenv:Body>");
        soapRequestData.append("<web:getCountryCityByIp>");

        soapRequestData.append("<web:theIpAddress>");
        soapRequestData.append("8.8.8.8");
        soapRequestData.append("</web:theIpAddress>");

        soapRequestData.append("</web:getCountryCityByIp>");
        soapRequestData.append("</soapenv:Body>");
        soapRequestData.append("</soapenv:Envelope>");

        // 创建请求
        HttpEntity<String> request = new HttpEntity<String>(soapRequestData + "", headers);

        // 发送post请求并获取到响应结果
        String str = null;
        // 封装一下返回结果
        Map<String, Object> analyseResult = new HashMap<String, Object>();
        try {
            str = restTemplate.postForObject("http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx", request, String.class);
            log.info("-----------Response content-----------: " + str);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return str;
    }



    /*
    *  此方法中，使用wsdl生成的代码
    * */
    @RequestMapping("/testWebService2")
    public void testWebService2(){
        IpAddressSearchWebService ipAddressSearchWebService = new IpAddressSearchWebService();
        IpAddressSearchWebServiceSoap ipAddressSearchWebServiceSoap = ipAddressSearchWebService.getIpAddressSearchWebServiceSoap();
        ArrayOfString countryCityByIp = ipAddressSearchWebServiceSoap.getCountryCityByIp("8.8.8.8");
        countryCityByIp.getString().forEach(System.out::println);

    }



    @Autowired
    WebServiceTemplate webServiceTemplate;

    @RequestMapping("/testWebService")
    public void testWebService() throws SOAPException, IOException {

        String MESSAGE =   "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://WebXml.com.cn/\">"+
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<web:getCountryCityByIp>" +
                "<web:theIpAddress>8.8.8.8</web:theIpAddress>" +
                "</web:getCountryCityByIp>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";

        //服务端WSDL
        String uri = "http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx?wsdl";
        webServiceTemplate.setDefaultUri(uri);
//
        StreamResult result = new StreamResult(System.out);



        WebServiceMessageCallback webServiceMessageCallback = new WebServiceMessageCallback() {
            @Override
            public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
                ((SoapMessage)message).setSoapAction( "http://WebXml.com.cn/getCountryCityByIp"
                );
            }
        };


//        webServiceTemplate.setUnmarshaller(jaxb2Marshaller2);

//        MessageFactory factory = MessageFactory.newInstance();
//        SOAPMessage message = factory.createMessage();
//        SOAPPart part = message.getSOAPPart();
//        SOAPEnvelope envelope = part.getEnvelope();
//        SOAPBody body = envelope.getBody();
//        QName qname = new QName("http://WebXml.com.cn/", "getCountryCityByIp", "web");
//        SOAPBodyElement element = body.addBodyElement(qname);
//        element.addChildElement("getCountryCityByIp", "web","http://WebXml.com.cn/").setValue("8.8.8.8");

        StreamSource source = new StreamSource(new StringReader(MESSAGE));

        webServiceTemplate.sendSourceAndReceive(source, webServiceMessageCallback, new SourceExtractor<Object>() {

            @Override
            public Object extractData(Source source) throws IOException, TransformerException {
                return null;
            }
        });
//        Object o = webServiceTemplate.marshalSendAndReceive(ipRequest, webServiceMessageCallback);
//        webServiceTemplate.sendSourceAndReceiveToResult(source,webServiceMessageCallback,result);


//        StringBuffer source = new StringBuffer();
//        source.append("<getCountryCityByIp>");//方法名
//        source.append("<theIpAddress>");//参数名
//        source.append("<![CDATA[");
//        source.append("8.8.8.8");
//        source.append("]]>");
//        source.append("</theIpAddress>");
//        source.append("</getCountryCityByIp>");
        //请求数据源
//        Source payload = new StringSource(source.toString());



//响应结果，该结果是整个响应报文，如果需要获取结果，根据报文进行解析。
//        DocumentResult result = new DocumentResult();
//        final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
//        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
//        jaxb2Marshaller.setClassesToBeBound(String.class);
//        webServiceTemplate.setDefaultUri(uri);
//        webServiceTemplate.setMarshaller(jaxb2Marshaller);
//        Object o = webServiceTemplate.marshalSendAndReceive(payload);
//        System.out.println(o);

    }



    @RequestMapping("/testController")
    public String testController(){
        log.info("logback info 成功了");
        log.warn("logback warm 成功了");
        log.error("logback error 成功了");
        return "Hello CJ Test .....";
    }
    @RequestMapping(value = "/testValidate")
    public void testController04(@Validated @RequestBody CJErrorEntity cjErrorEntity){
        System.out.println("cj.springboot.template.controller.test.TestController.testController04....");

        System.out.println(cjErrorEntity);
    }

    @RequestMapping(value = "/testError")
    public void testController03(){
        System.out.println(1/0);
        System.out.println("cj.springboot.template.controller.test.TestController.testController04....");
    }

    @RequestMapping(value = "/testControllerProduce",produces = "text/html")
    public void testController02(){
        System.out.println("cj.springboot.template.controller.test.TestController.testController02....");
    }

    @RequestMapping(value = "/testControllerProduce")
    public void testController01(){
        System.out.println("cj.springboot.template.controller.test.TestController.testController01....");
    }






}
