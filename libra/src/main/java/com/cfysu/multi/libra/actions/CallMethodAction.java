package com.cfysu.multi.libra.actions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cfysu.multi.libra.models.MethodInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 调用方法
 * 
 * @author weichao
 *
 */
@Service
public class CallMethodAction extends Action {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String methodId = request.getParameter("methodId");
        String paramJson = request.getParameter("paramJson");
        String beanName = request.getParameter("beanName");
        try {
            response.getWriter().write(getJsonResult(methodId, paramJson, beanName));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取方法调用结果并转成json字符串
     * 
     * @param methodId
     * @param
     * @return
     */
    private String getJsonResult(String methodId, String inputJson, String beanName)
            throws Exception {
        MethodInfo methodInfo = getMethodInfo(methodId, beanName);
        if (null == methodInfo) {
            return "methodId:[" + methodId + "] not exist!";
        }
        Method method = methodInfo.getMethod();
        Type[] ts = method.getGenericParameterTypes();
        Object bean = methodInfo.getBean();
        Object resultObj = null;
        if (ts.length == 1) {
            inputJson = inputJson.trim();
            String json = inputJson;
            if (inputJson.startsWith("[")) {
                json = inputJson.substring(1);
            }
            if (json.endsWith("]")) {
                json = json.substring(0, json.length() - 1);
            }
            Object obj = JSON.parseObject(json, ts[0]);
            resultObj = method.invoke(bean, obj);
        } else if (ts.length > 1) {

            List<String> list = JSON.parseObject(inputJson,
                    new TypeReference<List<String>>() {
                    });
            Object[] param = new Object[ts.length];

            for (int i = 0; i < ts.length; ++i) {
                if (ts[i] == String.class) {
                    String para = list.get(i);
                    Object paraObj = para;
                    if (para.equals("true")) {
                        paraObj = true;
                    }else if (para.equals("false")) {
                        paraObj = false;
                    }
                    param[i] = paraObj;
                }
                // 日期另外处理
                else if (ts[i] == Date.class) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    param[i] = dateFormat.parse(list.get(i));
                } else {
                    param[i] = JSON.parseObject(list.get(i), ts[i]);
                }
            }
            resultObj = method.invoke(bean, param);

        } else {
            resultObj = method.invoke(bean);
        }
        return getJsonFormatString(resultObj);
    }

}
