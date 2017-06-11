package com.platform.security.action;

import java.io.PrintWriter;

import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.platform.security.util.RSAUtils;

public class PasswordEncrypt extends ActionSupport {
	private final Logger logger = Logger.getLogger(PasswordEncrypt.class);

    public String getPublicKey() throws Exception {      
        ServletResponse response = ServletActionContext.getResponse();
        PrintWriter writer = response.getWriter();
        String publicKey = RSAUtils.generateBase64PublicKey();
        logger.debug(publicKey);
        writer.write(publicKey);        
        //�����������ﲻ��Ҫ����ҳ�棬������ֱ�ӷ���null
        return null;
    }
}
