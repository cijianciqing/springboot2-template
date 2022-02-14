package cj.springboot.template.controller.test;

import cj.springboot.template.util.exceptionHandler.entity.CJErrorEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//import javax.validation.Valid;

//import javax.validation.Valid;

@Slf4j
@RestController
public class TestController {
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
