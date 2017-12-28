package com.cfysu.multi.libra.actions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import com.cfysu.multi.libra.models.MethodInfo;
import com.cfysu.multi.libra.utils.CommonUtil;
import com.cfysu.multi.libra.utils.LibraSpringContextUtil;

import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class Action {
    private static SerializeConfig mapping = new SerializeConfig();
    private static String dateFormat;

    static {
        dateFormat = "yyyy-MM-dd HH:mm:ss";
    }

    private static Map<String, Action> actionMap = new HashMap<String, Action>();
    /**
     * methodId-->methodInfo
     */
    private static Map<String, MethodInfo> methodMap = new HashMap<String, MethodInfo>();

    /**
     * 将methodInfoList放入methodMap
     *
     * @param methodInfoList
     */
    public static void putMethodInfo(List<MethodInfo> methodInfoList) {
        if (methodInfoList == null || methodInfoList.size() == 0) {
            return;
        }
        for (MethodInfo methodInfo : methodInfoList) {
            String methodId = methodInfo.getMethodId();
            if (methodMap.containsKey(methodId)) {
                continue;
            }
            methodMap.put(methodId, methodInfo);
        }
    }

    /**
     * 根据methodId获取methodInfo
     *
     * @param methodId
     * @return
     */
    public MethodInfo getMethodInfo(String methodId, String beanName) {
        if (!methodMap.containsKey(methodId)) {
            //重新把这个方法对应的类中的方法
//            String beanName = CommonUtil.getBeanNameByMethodId(methodId);
            getAllMethodInfoList(LibraSpringContextUtil.getBean(beanName));
            if (!methodMap.containsKey(methodId)) {
                return null;
            }
            return methodMap.get(methodId);
        }
        return methodMap.get(methodId);
    }

    public static Action getAction(String act) {
        String realActionName = act + "Action";
        synchronized (Action.class) {
            if (actionMap.containsKey(realActionName)) {
                return actionMap.get(realActionName);
            }
            Object bean = LibraSpringContextUtil.getBean(realActionName);
            if (bean instanceof Action) {
                Action action = (Action) bean;
                actionMap.put(realActionName, action);
                return action;
            }
            return null;
        }
    }

    /**
     * 根据type创建一个对象实例
     *
     * @param type
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    protected Object createSimpleObject(Type type) throws Exception {
        Class<?> clazz = (Class<?>) type;

        String canonicalName = clazz.getCanonicalName();
        if (canonicalName.equals("int")
                || canonicalName.equals("java.lang.Integer") || clazz.isEnum()) {
            return Integer.valueOf(0);
        } else if (canonicalName.equals("java.lang.String")) {
            return "";
        } else if (canonicalName.equals("boolean")
                || canonicalName.equals("java.lang.Boolean")) {
            return Boolean.FALSE;
        } else if (canonicalName.equals("long")
                || canonicalName.equals("java.lang.Long")) {
            return Long.valueOf(0);
        } else if (canonicalName.equals("java.util.List")) {
            return new ArrayList();
        } else if (canonicalName.equals("java.util.Map")) {
            return new HashMap();
        } else {
            //判断此对象中的field，如果为List，则new一个出来
            return clazz.newInstance();
        }

    }

    /**
     * 根据type迭代的获取对象实例；如果涉及到泛型，则需要迭代初始化
     *
     * @param type
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object createInstance(Type type) throws Exception {
        Object obj = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type rawType = pType.getRawType();
            obj = createSimpleObject(rawType);
            Type[] ts = pType.getActualTypeArguments();
            if (ts.length == 1) {
                Type trueType = ts[0];
                Object obj1 = createInstance(trueType);
                ((List) obj).add(obj1);
            } else if (ts.length == 2) {
                Type keyType = ts[0];
                Type valType = ts[1];
                Object key = createInstance(keyType);
                Object val = createInstance(valType);
                ((Map) obj).put(key, val);
            }
        } else {
            obj = createSimpleObject(type);
        }
        return obj;
    }

    /**
     * <p>获取json字符串
     * <p>如果是null，也展示出来；采用格式化的多行展示
     * <p>说明: fastjson部分情况下解析不出来, 这种情况下采用gson进行解析
     *
     * @param object
     * @return
     */
    public static String getJsonFormatString(Object object) {
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
        try {
            return JSON.toJSONString(object, mapping, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);
        } catch (Exception e) {
            return new GsonBuilder().setPrettyPrinting().create().toJson(object);
        }

    }

    /**
     * 获取一个对象对应的类中的所有方法，并放入静态map中
     *
     * @param bean
     * @return
     */
    public List<MethodInfo> getAllMethodInfoList(Object bean) {
        List<MethodInfo> allMethods = CommonUtil.getAllMethods(bean);
        putMethodInfo(allMethods);
        Collections.sort(allMethods, new Comparator<MethodInfo>() {
            @Override
            public int compare(MethodInfo o1, MethodInfo o2) {
                return o1.getMethodName().compareToIgnoreCase(o2.getMethodName());
            }
        });
        return allMethods;
    }

    /**
     * 获取静态方法列表
     *
     * @param className
     * @return
     */
    public List<MethodInfo> getAllStaticMethodInfoList(String className) {
        List<MethodInfo> allMethods = CommonUtil.getAllStaticMethods(className);
        putMethodInfo(allMethods);
        Collections.sort(allMethods, new Comparator<MethodInfo>() {
            @Override
            public int compare(MethodInfo o1, MethodInfo o2) {
                return o1.getMethodName().compareToIgnoreCase(o2.getMethodName());
            }
        });
        return allMethods;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
}
