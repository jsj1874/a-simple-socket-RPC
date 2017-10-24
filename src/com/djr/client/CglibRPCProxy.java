package com.djr.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibRPCProxy<T> {

  /*@Override
	public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("调用前");
		Object result = methodProxy.invokeSuper(target, args);
		
		System.out.println("调用后");
		
		return result;
	}
	*/
	
	/*public Object getProxy(Class clazz){
		Enhancer enhancer  = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	private String ip = "";
	private int port = 0;
	
	public CglibRPCProxy(String ip,int port) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.port = port;
	}
	*/
	
	@SuppressWarnings("unchecked")  
	public static <T> T create(Class calzz,String ip,int port){	
		
		Callback callBack = new MethodInterceptor() {
			
			@Override
			public Object intercept(Object target, Method method, Object[] args, MethodProxy methodPorxy) throws Throwable {
				// TODO Auto-generated method stub
                Socket socket = new Socket(ip, port);  
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                try {    
                    output.writeUTF(calzz.getName()); 
                    output.writeUTF(method.getName());    
                    output.writeObject(method.getParameterTypes());    
                    output.writeObject(args);
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());    
                    try {    
                        Object result = input.readObject();    
                        if (result instanceof Throwable) {    
                            throw (Throwable) result;    
                        }
                        return result;    
                    } finally {    
                        input.close();    
                    }    
                } finally {    
                    output.close();    
                    socket.close();  
                }    
			}
		};
		Enhancer enhancer  = new Enhancer();
		enhancer.setSuperclass(calzz);
		enhancer.setCallback(callBack);
		return (T)enhancer.create();
	}
	
	/*public static Object getProxy(Class clazz,String ip,int port){
		Enhancer enhancer  = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(create(clazz, ip, port));
		return enhancer.create();
	}*/
	
/*
@Override
public Object intercept(Object target, Method method, Object[] args, MethodProxy methodPorxy) throws Throwable {
	// TODO Auto-generated method stub
	 Socket socket = new Socket(ip, port);  
     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
     try {    
         output.writeUTF(target.getClass().getSuperclass().getName()); 
         output.writeUTF(method.getName());    
         output.writeObject(method.getParameterTypes());    
         output.writeObject(args);
         System.out.println("target class name:"+target.getClass().getName() + "\tmethodName:"+method.getName() +"params Type:"+method.getParameterTypes() +"\targs:"+args);
         ObjectInputStream input = new ObjectInputStream(socket.getInputStream());    
         try {    
             Object result = input.readObject();    
             if (result instanceof Throwable) {    
                 throw (Throwable) result;    
             }    
             return result;    
         } finally {    
             input.close();    
         }    
     } finally {    
         output.close();    
         socket.close();  
     }    
	}
	*/

}
