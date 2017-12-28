package com.cfysu.multi.libra.utils;

import com.cfysu.multi.libra.models.MethodInfo;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;

public class CommonUtil {
    /**
     * 获取所有的静态方法
     * @param className
     * @return
     */
    public static List<MethodInfo> getAllStaticMethods(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return getAllMethodsByClass(clazz, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取方法列表, 如果是静态方法，则bean传null;否则bean要传值
     * @param clazz
     * @param bean
     * @return
     */
    public static List<MethodInfo> getAllMethodsByClass(Class<?> clazz, Object bean) {
        List<MethodInfo> methodInfoList = new ArrayList<MethodInfo>();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method: methods) {
            //过滤掉非public得方法
            int modifiers = method.getModifiers();
            if (!Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {//1-public, 2-private, 4-protected, 8-static, 16-final, 32-SYNCHRONIZED, 多个组合时转成二进取与
                continue;
            }
            MethodInfo methodInfo = new MethodInfo();
            if (bean == null && Modifier.isStatic(modifiers)) {//获取静态方法
                methodInfo.setStatic(true);
            }else if (bean != null && Modifier.isPublic(modifiers)) {//获取普通方法
                methodInfo.setStatic(false);
                methodInfo.setBean(bean);
            }else {
                continue;
            }
            String methodName = method.getName();
            Type[] parameterTypes = method.getGenericParameterTypes();
            List<String> paramNameList = new ArrayList<String>();
            for(Type sinParma: parameterTypes) {
                String[] itemArr = sinParma.toString().split("<");
                String[] arr = itemArr[0].split("\\.");
                paramNameList.add(arr[arr.length - 1]);
            }
            methodInfo.setMethodName(methodName + "(" + StringUtils.join(paramNameList, ",") + ")");
            methodInfo.setMethodId(clazz.getName() + "_" + methodName + "_" + StringUtils.join(paramNameList, "_"));
            methodInfo.setMethod(method);
            methodInfo.setBean(bean);
            methodInfo.setBeanName(clazz.getName());
            methodInfoList.add(methodInfo);
        }
        return methodInfoList;
    }

    /**
     * 获取类中的所有实例方法和静态方法，其他过滤掉
     */
    public static List<MethodInfo> getAllMethods(Object bean) {
        return getAllMethodsByClass(bean.getClass(), bean);
    }
    
    /**
     * 根据methodId获取beanName
     * @param methodId
     * @return
     */
    public static String getBeanNameByMethodId(String methodId) {
        String[] itemArr = methodId.split("_");
        String[] arr = itemArr[0].split("\\.");
        String clazzName = arr[arr.length - 1];
        String beanName = clazzName.substring(0, 1).toLowerCase() + clazzName.substring(1);
        return beanName;
    }
    
    /**
     * 发送GET请求
     * @param url
     * @return
     */
//    public static String getMerchantIdResponse(String url, String sut, Integer merchantType) {
//
//        try {
//            url = url + "?merchantType=" + merchantType + "&sut=" + sut;
//            HttpClient httpClient = new DefaultHttpClient();
//
//            HttpGet httpGet = new HttpGet(url);
//            httpGet.setHeader("Cookie", "sut=" + sut);
//            HttpResponse response = httpClient.execute(httpGet);
//            if(response.getStatusLine().getStatusCode() != 200) {
//                return "";
//            }
//            HttpEntity httpEntity = response.getEntity();
//            InputStream inputStream = httpEntity.getContent();
//
//            //获取返回的数据信息
//            StringBuffer postResult = new StringBuffer();
//            String readLine = null ;
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//            while((readLine = reader.readLine()) != null) {
//                postResult.append(readLine);
//            }
//            httpClient.getConnectionManager().shutdown();
//            return postResult.toString();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//
//    }
    
//    public static void main(String[] args) {
////        System.out.println(JSON.toJSONString(getAllMethods(new Student())));
//        System.out.println(JSON.toJSONString(getMerchantIdResponse("http://wei.yhd.com/store_mohe/mobile/interfaces/validateUt.action", "SE2FUA7KNFKW8XSJFSTDSNWY156N4M1T1V9INL", 6)));
//    }
}
