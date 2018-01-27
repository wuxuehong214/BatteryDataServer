package com.green.battery.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		
//System.out.println(session.getId()+"@@@@@@@@@@@@");
		String requesturi = request.getRequestURI();
//System.out.println(requesturi+"#######################################################"+session.getAttribute("user"));
//System.out.println(request.getContextPath()+"/");
		if (session.getAttribute("user") == null
				&&!requesturi.endsWith("/login.jsp")
				&&!requesturi.endsWith("/User_login")
				&&!requesturi.endsWith("/upload")
				&&!requesturi.endsWith("/Lang_change")
				&&!requesturi.endsWith("/Lang_jsp")
				&&!requesturi.endsWith(".gif")
				&&!requesturi.endsWith(".jpg")
				&&!requesturi.endsWith(".ico")
				&&!requesturi.endsWith(".css")
				&&!requesturi.endsWith(".js")
				&&!requesturi.endsWith(".map")
				&&!requesturi.endsWith(".woff")
//				&&!requesturi.endsWith(request.getContextPath()+"/")
				) {
			System.out.println(request.getContextPath()+"/====================");
			response.sendRedirect(request.getContextPath()+"/Lang_jsp?page=login");
			return;
		}else{
			if(requesturi.equals("/")){
				response.sendRedirect(request.getContextPath()+"/User_dashboard");
				return;
			}
		}
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
