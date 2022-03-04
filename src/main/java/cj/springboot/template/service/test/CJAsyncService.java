package cj.springboot.template.service.test;

import cn.com.ns.cj.cjuniversalspringbootstarter.returnData.CJAjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CJAsyncService {

    private Map<String, DeferredResult<CJAjaxResult>> deferredResultMap = new HashMap<>();


    @Async
    public void test2()  throws InterruptedException {
        log.info(Thread.currentThread().getName()+"====start=====");
        Thread.sleep(3000);
        log.info(Thread.currentThread().getName()+"====end1=====");
    }

    public void test(String id, DeferredResult<CJAjaxResult> deferredResult) throws InterruptedException {
        // From some other thread...
//        Thread.currentThread().setName("Defferred Thread");
        log.info(Thread.currentThread().getName()+"====start=====");
        Thread.sleep(3000);

        deferredResultMap.put(id, deferredResult);
        log.info(Thread.currentThread().getName()+"====end=====");
    }

    public CJAjaxResult test3(String id) {
        DeferredResult<CJAjaxResult> cjAjaxResultDeferredResult = deferredResultMap.get(id);
        CJAjaxResult success = CJAjaxResult.success("asyncTest", "CJdefferredResult");
        cjAjaxResultDeferredResult.setResult(success);
        return success;
    }
}
