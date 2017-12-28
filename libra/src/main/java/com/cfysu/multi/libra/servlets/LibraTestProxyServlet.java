package com.cfysu.multi.libra.servlets;

import com.cfysu.multi.libra.actions.Action;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class LibraTestProxyServlet extends HttpServlet {
    private static Set<Long> validMerchantIdSet = new HashSet<Long>();
    static {
        validMerchantIdSet.add(303L);
        validMerchantIdSet.add(11464L);
        validMerchantIdSet.add(80L);
        validMerchantIdSet.add(22L);
    }
    public static String getExceptionStack(Exception e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        StringBuffer buffer = new StringBuffer();
        buffer.append(e.toString()).append("\n");
        for (int index = stackTraceElements.length - 1; index >= 0; --index) {
            buffer.append("at [").append(stackTraceElements[index].getClassName()).append(",");
            buffer.append(stackTraceElements[index].getFileName()).append(",");
            buffer.append(stackTraceElements[index].getMethodName()).append(",");
            buffer.append(stackTraceElements[index].getLineNumber()).append("]\n");
        }
        return buffer.toString();
    }
    
    private String getSut(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for(Cookie cookie: cookies) {
            if (cookie.getName().equals("sut")) {
                return cookie.getValue();
            }
        }
        return null;
    }
    
//    private boolean isValidMerchantLogin(HttpServletRequest request) {
//        String sut = getSut(request);
//        if (null == sut) {
//            return false;
//        }
//        return loginUserValid(sut);
//    }
    
//    private boolean loginUserValid(String sut) {
//        String url = "http://shop.yhd.com/store_mohe/mobile/interfaces/validateUt.action";
//        String merchantIdJson = CommonUtil.getMerchantIdResponse(url, sut, 2);
//        if (StringUtils.isBlank(merchantIdJson)) {
//            return false;
//        }
//        try {
//            JSONObject jsonObject = JSONObject.parseObject(merchantIdJson);
//            Long merchantId = jsonObject.getLong("datas");
//            boolean isValid = validMerchantIdSet.contains(merchantId);
//            if (!isValid) {
//                merchantIdJson = CommonUtil.getMerchantIdResponse(url, sut, 6);
//                return validMerchantIdSet.contains(JSONObject.parseObject(merchantIdJson).getLong("datas"));
//            }
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
//        if (!isValidMerchantLogin(req)) {
//            //线上环境需要判断是否登录了
//            resp.getWriter().append("you need to login http://shangjia.yhd.com by using [merchant(303,11464),brandMerchant(80,22)]!");
//            resp.getWriter().close();
//            return;
//        }
        try {
            resp.setCharacterEncoding("UTF-8");
            String act = req.getParameter("act");
            if (StringUtils.isBlank(act)) {
                act = "init";
            }
            if (act.equals("init")) {//加载页面
                renderHtml(req, resp);
            }else if (act.equals("static")) {//加载静态文件
                handle(req.getParameter("path"), req, resp);
            }else {
                renderAct(act, req, resp);
            }
        } catch (Exception e) {
            resp.getWriter().append(getExceptionStack(e));
            resp.getWriter().close();
            return;
        }
    }
    
    /**
     * 返回一个页面
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void renderHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "/libraPages/libraIndex.jsp";
        handle(path, req, resp);
    }
    
    private void handle(String path, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream in = this.getClass().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }
        String content = builder.toString();
        reader.close();
        resp.getWriter().write(content);;
        resp.getWriter().close();
    }
    /**
     * 处理请求: 获取bean中的方法、获取方法的参数、触发方法
     * @param act
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void renderAct(String act, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Action action = Action.getAction(act);
        action.handle(req, resp);
    }

    
}
