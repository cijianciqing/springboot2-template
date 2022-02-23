
package cj.springboot.template.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IpAddressSearchWebServiceHttpPost", targetNamespace = "http://WebXml.com.cn/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IpAddressSearchWebServiceHttpPost {


    /**
     * <br /><h3>通过输入IP地址查询国家、城市、所有者等信息。没有注明国家的为中国</h3><p>输入参数：IP地址（自动替换 " 。" 为 "."），返回数据： 一个一维字符串数组String(1)，String(0) = IP地址；String(1) = 查询结果或提示信息</p><br />
     * 
     * @param theIpAddress
     * @return
     *     returns cj.springboot.template.ws.ArrayOfString
     */
    @WebMethod
    @WebResult(name = "ArrayOfString", targetNamespace = "http://WebXml.com.cn/", partName = "Body")
    public ArrayOfString getCountryCityByIp(
        @WebParam(name = "string", targetNamespace = "http://www.w3.org/2001/XMLSchema", partName = "theIpAddress")
        String theIpAddress);

    /**
     * <br /><h3>获得您的IP地址和地址信息</h3><p>输入参数：无，返回数据： 一个一维字符串数组String(1)，String(0) = IP地址；String(1) = 地址信息</p><br />
     * 
     * @return
     *     returns cj.springboot.template.ws.ArrayOfString
     */
    @WebMethod
    @WebResult(name = "ArrayOfString", targetNamespace = "http://WebXml.com.cn/", partName = "Body")
    public ArrayOfString getGeoIPContext();

    /**
     * <br /><h3>获得本IP地址搜索 WEB 服务的数据库版本更新时间</h3><p>输入参数：无，输出参数 String</p><br />
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "string", targetNamespace = "http://WebXml.com.cn/", partName = "Body")
    public String getVersionTime();

}
