package com.cfysu.multi.libra.actions;

import com.alibaba.fastjson.JSONObject;
import com.cfysu.multi.libra.models.MethodInfo;
import com.cfysu.multi.libra.models.MethodSimpleInfo;
import com.cfysu.multi.libra.utils.LibraSpringContextUtil;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取一个bean中的方法
 * @author weichao
 *
 */
@Service("getMethodAction")
public class GetMethodAction extends Action {

    public static String ACTION_NAME = "getMethod";
    

    @Override
    public void handle(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String beanName = request.getParameter("beanName");
        String isStatic = request.getParameter("isStatic");
        if (isStatic == null) {
            isStatic = "0";
        }
        String className = request.getParameter("className");
        //获取bean中的所有方法
        PrintWriter writer = response.getWriter();
        try {
            JSONObject jsonObject = new JSONObject();
            List<MethodInfo> allMethods = null;
            if (isStatic.equals("0")) {//非静态
                Object bean = LibraSpringContextUtil.getBean(beanName);
                if (bean == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "bean not exist!");
                    writer.write(jsonObject.toJSONString());
                }else {
                    allMethods = getAllMethodInfoList(bean);
                }
            }else {//静态
                allMethods = getAllStaticMethodInfoList(className);
            }
            jsonObject.put("code", 0);
            jsonObject.put("methodList", getMethodSimpleInfoList(allMethods));
            writer.write(jsonObject.toJSONString());
            //返回结果
        } catch (NoSuchBeanDefinitionException e) {
            //bean不存在
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 1);
            jsonObject.put("msg", "bean not exist!");
            writer.write(jsonObject.toJSONString());
        }
    }
    
    private List<MethodSimpleInfo> getMethodSimpleInfoList(List<MethodInfo> methodInfoList) {
        List<MethodSimpleInfo> resultList = new ArrayList<MethodSimpleInfo>();
        for(MethodInfo methodInfo: methodInfoList) {
            MethodSimpleInfo simpleInfo = new MethodSimpleInfo();
            simpleInfo.setMethodId(methodInfo.getMethodId());
            simpleInfo.setMethodName(methodInfo.getMethodName());
            resultList.add(simpleInfo);
        }
        return resultList;
    }
    
}
