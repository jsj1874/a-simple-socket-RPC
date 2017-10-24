package com.djr.test;

import com.djr.bean.User;
import com.djr.bean.UserImpl;
import com.djr.client.CglibRPCProxy;
import com.djr.client.RPCProxy;

public class Test {
	public static void main(String[] args) {
		UserImpl user = new User();
		user = RPCProxy.create(user,"localhost",20000);
		System.out.println(user.name("rpc"));
		/*CglibRPCProxy cglibRPCProxy = new CglibRPCProxy("localhost",20000);
		UserImpl result =  (User) cglibRPCProxy.getProxy(User.class);
		System.out.println(result.name("1874"));*/
		
		/*User result = (User)CglibRPCProxy.getProxy(User.class, "localhost", 20000);
		System.out.println(result.name("1874"));*/
		User result = (User) CglibRPCProxy.create(User.class,"localhost", 20000);
		System.out.println(result.name("1874"));
	}
}
