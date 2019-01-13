/**
 * NetOne.java
 * yumo
 * 2015-1-12
 * TODO  java网络学习操作类
 */
package com.yumodev.java.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * yumo
 *
 */
public class NetOne {

	/**
	 * 
	 */
	public NetOne() {
		// TODO Auto-generated constructor stub
	}
	
	public void Test()
	{
		InetAddressTest();
	}
	
	public void InetAddressTest()
	{
		try {
				InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
				System.out.println(inetAddress);
			
				inetAddress = InetAddress.getByName("61.135.169.125");
				System.out.println(inetAddress);
				
				String strHostName = inetAddress.getHostName();
				System.out.println(inetAddress);
				System.out.println(strHostName);
				
				inetAddress = InetAddress.getLocalHost();
				System.out.println(inetAddress);
				
				byte[] address = {127, 0, (byte)216, (byte)196};
				InetAddress lessWrong = InetAddress.getByAddress(address);
				InetAddress lessWrongWithName = InetAddress.getByAddress("www.baidu.com", address);
				System.out.println(lessWrong);
				System.out.println(lessWrongWithName);
				
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
