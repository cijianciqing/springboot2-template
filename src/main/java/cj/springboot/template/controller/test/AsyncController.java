package cj.springboot.template.controller.test;

import cj.springboot.template.service.test.CJAsyncService;
import cn.com.ns.cj.cjuniversalspringbootstarter.returnData.CJAjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@Slf4j
@RestController
@RequestMapping(value = "/cjasync")
public class AsyncController {

    @Autowired
    private CJAsyncService cjAsyncService;

    /*
    * 异步调用
    * */
    @GetMapping("/test2")
    public String test2() throws InterruptedException {
        log.info(Thread.currentThread().getName()+"====start=====");
        cjAsyncService.test2();
        log.info(Thread.currentThread().getName()+"====end2=====");
        return "cj.springboot.template.controller.test.AsyncController.test2";
    }
    /*
    * 异步请求--DeferredResult
    * 缺点：不支持 (for example, @RequestBody, @RequestPart, and others)
    * quotes() 和 test3()是测试用的配套方法
    * */
//    @Async
    @GetMapping("/deferredTest/{cjid}")
    public DeferredResult<CJAjaxResult> quotes(@PathVariable(value = "cjid") String id) throws InterruptedException {
//        Thread.currentThread().setName("Controller Thread");
        log.info(Thread.currentThread().getName()+"====start=====");
        DeferredResult<CJAjaxResult> deferredResult = new DeferredResult();
        cjAsyncService.test(id, deferredResult);
        log.info(Thread.currentThread().getName()+"====end2=====");
        return deferredResult;

    }
    @GetMapping("/getDefferredRequest/{cjid}")
    public CJAjaxResult test3(@PathVariable(value = "cjid") String id) throws InterruptedException {
        log.info(Thread.currentThread().getName()+"====start=====");
        CJAjaxResult cjAjaxResult = cjAsyncService.test3(id);
        log.info(Thread.currentThread().getName()+"====end2=====");
        return cjAjaxResult;
    }


    /*
    * 异步请求--callable
    * */
    @GetMapping("/callableTest")
    public Callable<String> processUpload() {
        log.info(Thread.currentThread().getName()+"====start=====");
        Callable<String> callable =  new Callable() {
            public String call() throws Exception {
                log.info(Thread.currentThread().getName()+"====start=====");
                Thread.sleep(3000);
                log.info(Thread.currentThread().getName()+"====end=====");
                return "someView";
            }
        };
        log.info(Thread.currentThread().getName()+"====end=====");
        return callable;
    }




}
