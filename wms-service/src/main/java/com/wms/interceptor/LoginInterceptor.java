package com.wms.interceptor;

import com.wms.mybatis.dao.SfcUserLoginMybatisDao;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.SfcUserLoginQuery;
import com.wms.utils.RandomUtil;
import com.wms.utils.ResourceUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.annotation.RestWebservice;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 登入驗證攔截器
 *
 * @author OwenHuang
 * @Date 2012/6/14
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger("");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");

    @Autowired
    private SfcUserLoginMybatisDao sfcUserLoginMybatisDao;

    private long startTime = 0;

    /**
     * 在controller之前攔截
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTime = System.currentTimeMillis();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Login login = handlerMethod.getMethodAnnotation(Login.class);
        if (null == login) {//沒有Login註解，直接放行
            return true;
        } else {
            HttpSession session = request.getSession(false);
            if (session == null) {
                request.setAttribute("msg", "闲置太久，系统自动登出，请重新登入！");
            } else {
                SfcUserLogin sessionUserLogin = (SfcUserLogin) session.getAttribute(ResourceUtil.getUserInfo());
                if (sessionUserLogin == null) {
                    request.setAttribute("msg", "查无登入资讯，请重新登入！");
                    session.invalidate();
                } else {
                    SfcUserLoginQuery sfcUserLoginQuery = new SfcUserLoginQuery();
                    sfcUserLoginQuery.setId(sessionUserLogin.getId());
                    sfcUserLoginQuery.setWarehouseId(sessionUserLogin.getWarehouse().getId());
                    SfcUserLogin dbUserLogin = sfcUserLoginMybatisDao.queryById(sfcUserLoginQuery);
                    if (dbUserLogin.getEnable().intValue() == 0 || !dbUserLogin.getSessionId().equals(session.getId())) {
                        if (dbUserLogin.getEnable().intValue() == 0) {
                            request.setAttribute("msg", "帐号已被停止只使用！");
                        } else {
                            request.setAttribute("msg", "帐号已在其他地方登入！");
                        }
                        session.invalidate();
                    } else {
                        session.setAttribute(ResourceUtil.getUserInfo(), dbUserLogin);
                        return true;
                    }
                }
            }
            request.setAttribute("isShowLoginDialog", true);
            request.getRequestDispatcher("/reLogin.html").forward(request, response);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {

		/*
		 * 报错才打印日志
		 */
		if (null != exception) {

            StringBuilder sb = new StringBuilder();
            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Login login = handlerMethod.getMethodAnnotation(Login.class);
            SfcUserLogin userLogin = SfcUserLoginUtil.getLoginUser();
            RestWebservice restWebservice = handlerMethod.getMethodAnnotation(RestWebservice.class);

            if (userLogin != null) {

                sb.append("使用者：")
                        .append(userLogin.getId())
                        .append("，成功调用Controller，执行ID：Controller-");
            } else if (restWebservice != null) {

                sb.append("使用者：")
                        .append(request.getParameter(ResourceUtil.getRESTLoginName()))
                        .append("，成功调用Webservice，执行ID：Webservice-");
            } else {

                sb.append("成功调用Controller，执行ID：Controller-");
            }

            sb.append(RandomUtil.getUUID());
            logger.error(sb.toString());
            sb.setLength(0);
            sb.append("执行類別：")
                    .append(handlerMethod.getBeanType().getName())
                    .append("，执行方法：")
                    .append(handlerMethod.getMethod().getName());
            logger.error(sb.toString());
            sb.setLength(0);
            sb.append("执行完成時間：")
                    .append(sdf.format(new Date()))
                    .append("，执行总耗时间：")
                    .append(System.currentTimeMillis() - startTime)
                    .append("ms");
            logger.error(sb.toString());
            sb.setLength(0);
            sb.append("执行错误日志：")
					.append(exception.toString());
            logger.error(sb.toString());
			sb.setLength(0);
            sb.append("==================================================================================================");
            logger.error(sb.toString());
            sb.setLength(0);
        }
    }
}
