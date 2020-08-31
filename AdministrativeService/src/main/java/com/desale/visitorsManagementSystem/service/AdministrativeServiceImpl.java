package com.desale.visitorsManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desale.visitorsManagementSystem.service.req.ResponseUser;
import com.desale.visitorsManagementSystem.util.UserCallerUtil;

@Service
//@Transactional
public class AdministrativeServiceImpl implements AdministrativeService {
	@Autowired
	UserCallerUtil userCallerUtil;
	@Override
	public List<ResponseUser> getUsers() {
		return (List<ResponseUser>)userCallerUtil.getUsers();
	}

}
