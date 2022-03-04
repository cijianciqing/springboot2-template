package cj.springboot.template.config.async;

import cn.com.ns.cj.cjuniversalspringbootstarter.returnData.CJAjaxResult;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptor;


public class CJDeferredResultProcessInterceptor implements DeferredResultProcessingInterceptor {
    @Override
    public <T> boolean handleTimeout(NativeWebRequest request, DeferredResult<T> result) throws Exception {
        result.setErrorResult(CJAjaxResult.error("CJDeferredTimeout","default is zero"));
        return false;
    }
}
