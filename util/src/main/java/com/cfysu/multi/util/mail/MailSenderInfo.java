/**
 * MailSenderInfo.java
 * @Author sam wang
 * @date 2014年12月18日
 * Version 1.0
 */
package com.cfysu.multi.util.mail;
/** 
* 发送邮件需要使用的基本信息 
*/ 
public class MailSenderInfo { 
	// 邮件接收者的地址 
	private String toAddress;
	// 是否需要身份验证 
	private boolean validate = false; 
	// 邮件主题 
	private String subject;
	// 邮件的文本内容 
	private String content;
	// 邮件附件的文件名 
	private String[] attachFileNames;
	
	public boolean isValidate() { 
	  return validate; 
	}
	public void setValidate(boolean validate) { 
	  this.validate = validate; 
	}
	public String[] getAttachFileNames() {
	  return attachFileNames; 
	}
	public void setAttachFileNames(String[] fileNames) {
	  this.attachFileNames = fileNames; 
	}
	
	public String getToAddress() {
	  return toAddress; 
	} 
	public void setToAddress(String toAddress) {
	  this.toAddress = toAddress; 
	} 
	
	public String getSubject() {
	  return subject; 
	}
	public void setSubject(String subject) {
	  this.subject = subject; 
	}
	public String getContent() {
	  return content; 
	}
	public void setContent(String textContent) {
	  this.content = textContent; 
	} 
} 