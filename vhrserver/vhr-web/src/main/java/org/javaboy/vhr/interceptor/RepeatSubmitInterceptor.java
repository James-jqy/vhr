package org.javaboy.vhr.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.javaboy.vhr.annotations.RepeatSubmit;
import org.javaboy.vhr.bean.Hr;
import org.javaboy.vhr.exception.RepartSubmitException;
import org.javaboy.vhr.utils.HttpHelper;
import org.javaboy.vhr.utils.RedisCache;
import org.javaboy.vhr.utils.RepeatedlyRequestWrapper;
import org.javaboy.vhr.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交拦截器
 *
 * @author ruoyi
 */
@Component
public class RepeatSubmitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisCache redisCache;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request)) {
                    throw new RepartSubmitException("重复提交！");
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @param request
     * @return false 代表不是重复提交
     *          true 代表重复提交
     * @throws Exception
     */
    public boolean isRepeatSubmit(HttpServletRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Hr principal = (Hr)authentication.getPrincipal();
        // 获取到已经登陆用户的用户id。这个id具有唯一性，就不用自己去生成uuid了。
        Integer id = principal.getId();

        String cache_repeat_key = "repeat_submit:" + id ;

        String cacheObject = (String)redisCache.getCacheObject(cache_repeat_key);

        if (cacheObject != null){
            // 如果不为null，则代表redis中已经有这个key了。可能是之前访问过。
            if (cacheObject.equals(request.getRequestURI())){
                return true;
            }else{
                return false;
            }
        }

        // 如果redis中没有这个key，代表是第一次访问这个方法。直接放行，并存入key到redis中。
        redisCache.setCacheObject(cache_repeat_key,request.getRequestURI(),1, TimeUnit.SECONDS);
        return false;
    }
}
