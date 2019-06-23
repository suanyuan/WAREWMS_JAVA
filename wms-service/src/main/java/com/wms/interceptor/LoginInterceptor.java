package com.wms.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wms.entity.UserLogin;
import com.wms.mybatis.dao.SfcUserLoginMybatisDao;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.SfcUserLoginQuery;
import com.wms.utils.RandomUtil;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.annotation.RestWebservice;

/**
 * 登入驗證攔截器
 * @Date 2012/6/14
 * @author OwenHuang
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
		}else{
			HttpSession session = request.getSession(false);
			if(session == null){
				request.setAttribute("msg", "闲置太久，系统自动登出，请重新登入！");
			}else{
				SfcUserLogin sessionUserLogin = (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo());
				if(sessionUserLogin == null){
					request.setAttribute("msg", "查无登入资讯，请重新登入！");
					session.invalidate();
				}else{
					SfcUserLoginQuery sfcUserLoginQuery = new SfcUserLoginQuery();
					sfcUserLoginQuery.setId(sessionUserLogin.getId());
					sfcUserLoginQuery.setWarehouseId(sessionUserLogin.getWarehouse().getId());
					SfcUserLogin dbUserLogin = sfcUserLoginMybatisDao.queryById(sfcUserLoginQuery);
					if(dbUserLogin.getEnable().intValue() == 0 || !dbUserLogin.getSessionId().equals(session.getId())){
						if(dbUserLogin.getEnable().intValue() == 0){
							request.setAttribute("msg", "帐号已被停止只使用！");
						}else{
							request.setAttribute("msg", "帐号已在其他地方登入！");
						}
						session.invalidate();
					}else{
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
		StringBuilder sb = new StringBuilder();
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Login login = handlerMethod.getMethodAnnotation(Login.class);
		RestWebservice restWebservice = handlerMethod.getMethodAnnotation(RestWebservice.class);
		if(login != null){
			UserLogin user = (UserLogin)request.getSession().getAttribute(ResourceUtil.getUserInfo());
			sb.append("使用者：")
			  .append(user == null ? "" : user.getId())
			  .append("，成功调用Controller，執行ID：Controller-");
		}else if(restWebservice != null){
			sb.append("使用者：")
			  .append(request.getParameter(ResourceUtil.getRESTLoginName()))
			  .append("，成功调用Webservice，執行ID：Webservice-");
		}else{
			sb.append("成功调用Controller，執行ID：Controller-");
		}
		
		sb.append(RandomUtil.getUUID());
		logger.info(sb.toString());sb.setLength(0);
		sb.append("執行類別：")
		  .append(handlerMethod.getBeanType().getName())
		  .append("，執行方法：")
		  .append(handlerMethod.getMethod().getName());
		logger.info(sb.toString());sb.setLength(0);
		sb.append("執行完成時間：")
		  .append(sdf.format(new Date()))
		  .append("，执行总耗时间：")
		  .append(System.currentTimeMillis() - startTime)
		  .append("ms");
		logger.info(sb.toString());sb.setLength(0);
		sb.append("==================================================================================================");
		logger.info(sb.toString());sb.setLength(0);
	}
}
