package com.platform.security.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.platform.organization.pojo.OrgUser;

public class SessionFilter extends HttpServlet implements Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected FilterConfig filterConfig = null;
	private String redirectURL = null;
	private Set<String> notCheckURLList = new HashSet<String>();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getServletPath();
		String contextPath = req.getContextPath();
		HttpSession session = req.getSession();

		if (url.equals("")) {
			url += "/";
		}
		if(url.equals("/login.jsp") || url.equals("/security/login.action") || url.equals("/security/login.do") 
				|| url.equals("/security/passwordEncrypt.action")
				|| url.equals("security/logout.action")){
			//��¼ҳ����¼����ֱ�ӷ���
			chain.doFilter(req, resp);
			return;
		}
		
		OrgUser user = (OrgUser) session.getAttribute("loginUser");
		if (user == null) {// ת���½ҳ��
			System.out.println(url + " ��Ч��¼��ת���¼ҳ��");
			resp.sendRedirect(contextPath + "/login.jsp");
			return;
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
