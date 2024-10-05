package com.iquest.pojo;

import java.util.List; 

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@XmlRootElement
@Component
public class SimpleUserList {
	private List<SimpleUser> userList;

	public List<SimpleUser> getUserList() {
		return userList;
	}

	@XmlElement
	public void setUserList(List<SimpleUser> userList) {
		this.userList = userList;
	}
}
