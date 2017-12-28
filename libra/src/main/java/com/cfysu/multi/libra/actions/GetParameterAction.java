package com.cfysu.multi.libra.actions;

import com.cfysu.multi.libra.models.MethodInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetParameterAction extends Action {
    public static String ACTION_NAME = "getParameter";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String methodId = request.getParameter("methodId");
        String beanName = request.getParameter("beanName");
        MethodInfo methodInfo = getMethodInfo(methodId, beanName);
        if (null == methodInfo) {
            return;
        }
        Type[] types = methodInfo.getMethod().getGenericParameterTypes();
        List<Object> list = new ArrayList<Object>();
        for (Type type : types) {
            Object sinType = null;
            try {
                sinType = createInstance(type);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            list.add(sinType);
        }
        response.getWriter().write(getJsonFormatString(list));
    }
    
    
}
