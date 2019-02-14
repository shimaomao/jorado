package com.jorado.ik.admin;

import com.jorado.ik.admin.util.HttpUtils;
import com.jorado.logger.EventClient;
import com.jorado.logger.util.JsonUtils;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 配置之定义json转换器，譬如换成fastjson
     * 此处不输出空值
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
//        converters.add(jacksonConverter);
    }

    /**
     * 统一异常处理
     *
     * @param exceptionResolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((request, response, handler, e) -> {
            Result result;
            if (e instanceof NoHandlerFoundException) {
                result = new Result(ResultStatus.NOT_FOUND, "接口 [" + request.getRequestURI() + "] 不存在");
            } else {
                result = new Result(ResultStatus.ERROR, "接口 [" + request.getRequestURI() + "] 错误");
                String message;
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                            request.getRequestURI(),
                            handlerMethod.getBean().getClass().getName(),
                            handlerMethod.getMethod().getName(),
                            e.getMessage());
                } else {
                    message = e.getMessage();
                }
                EventClient.getDefault().createException(message, e).addData("request.url", request.getRequestURI()).addData("request.params", HttpUtils.getParamString(request)).asyncSubmit();
            }
            responseResult(response, result);
            return new ModelAndView();
        });
    }

    /**
     * 解决跨域问题
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // **代表所有路径
                .allowedOrigins("*") // allowOrigin指可以通过的ip，*代表所有，可以使用指定的ip，多个的话可以用逗号分隔，默认为*
                .allowedMethods("GET", "POST", "HEAD", "PUT", "DELETE") // 指请求方式 默认为*
                .allowCredentials(false) // 支持证书，默认为true
                .maxAge(3600) // 最大过期时间，默认为-1
                .allowedHeaders("*");
    }

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
                                    @Override
                                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                                             Object handler) {
                                        //TODO:验证参数合法性

                                        return true;
                                    }
                                }
        ).addPathPatterns("/position/**");
    }


    private void responseResult(HttpServletResponse response, Result result) {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(response.getStatus());
        try {
            response.getWriter().write(JsonUtils.toJson(result));
        } catch (IOException ex) {
            EventClient.getDefault().submitException(ex.getMessage(), ex);
        }
    }
}