/**
 * StringCommon.java
 * yumo
 * 2015-1-2
 * TODO String String Builder 操作相关类和测试类。
 */
package com.yumodev.java.airthmetic.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class StringCommon {


	public StringCommon() {
		String string = "String";
		Map<String,String> map = new HashMap<String,String>();
	}
	
	public void Test(){
		
//		diffString();
//		
//		reverse();
//		
//		permutation();
		
//		replaceSpaceTest();
		
//		compressStringTest();
		
//		matrixRotateTest();
		
//		matrixSetZerosTest();
	}
	
	/**
	 * TODO 字符串排序
	 * yumo
	 * @param str
	 * @return
	 * String
	 * 2015-1-12
	 */
	public String sort(String str)
	{
		char[] arr = str.toCharArray();
		Arrays.sort(arr);
		return new String(arr);
	}
	
	/*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	 实现一个算法确定一个字符串里面的所有的字符都完全不同。假定不使用额外的字符。
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	public void diffString()
	{
		System.out.println(" \n实现一个算法确定一个字符串里面的所有的字符都完全不同。假定不使用额外的字符");
		String str = "d124f16";
		boolean b = diffString_ArrayAsi2(str);
		System.out.println("diffString_ArrayAsi2:"+b);
		
	}
	
	/**
	 * TODO 假定为ASIII字符集,利用数组的方式取得。
	 * yumo
	 * @param str
	 * @return
	 * boolean
	 * 2015-1-12
	 */
	public boolean diffString_ArrayAsi2(String str)
	{
		if(str == null || str.length() <= 0) return true;
		if(str.length() > 256) return false;
		
		System.out.println(str);
		boolean[] bool = new boolean[256];
		for(int n = 0; n < str.length(); n++)
		{
			int a = str.charAt(n);
			if(bool[a]) return false;
			bool[a] = true;
		}
		return true;
	}
	
	
	/*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	
	
	
	/*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	 翻转一个字符串
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	public void reverse(){
		System.out.println(" \n翻转一个字符串");
		String str = "d124f16";
		reverseOne(str);
		System.out.println("reverseOne:"+str);
	}
	
	public void reverseOne(String str){
		String a = "";
		for(int n = str.length() -1; n >= 0; n --)
		{
			a += str.charAt(n);
		}
		str = a;
		System.out.println("end reverse :"+str);
	}
	/*
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	

	/*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	 给定两个字符串，请确定程序，确定其中一个字符产的字符重新排列后，能否编程另外一个字符串。
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	public void permutation(){
		System.out.println(" \n翻转一个字符串");
		String strOne = "d124f16";
		String strTwo = "d124f62";
		boolean bOne = permutationOne(strOne, strTwo);
		System.out.println("permutation():"+bOne);
		
		boolean bTwo = permutationTwo(strOne, strTwo);
		System.out.println("permutation():"+bTwo);
	}
	
	/**
	 * TODO 利用双双排序的方式，计算判断变位词。
	 * yumo
	 * @param strOne
	 * @param strTwo
	 * @return
	 * boolean
	 * 2015-1-12
	 */
	public boolean permutationOne(String strOne, String strTwo){
		if(strOne.length()  != strTwo.length()) return false;
		
		return sort(strOne).equals(sort(strTwo));
	}
	
	/**
	 * TODO 如果一个字符串变位后相等，那么其每个字符出现的次数一定是相等。
	 * yumo
	 * @param strOne
	 * @param strTwo
	 * @return
	 * boolean
	 * 2015-1-12
	 */
	public boolean permutationTwo(String strOne, String strTwo)
	{
		if(strOne.length()  != strTwo.length()) return false;
		
		int[] a = new int[256];
		for(int n =0; n < strOne.length(); n++)
		{
			a[strOne.charAt(n)] = a[strOne.charAt(n)] + 1;
		}
		
		for(int n = 0; n < strTwo.length(); n++)
		{
			if(--a[strTwo.charAt(n)] < 0)
			{
				return false;
			}
		}
		return true;
	}
	
	
	/*
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	
	/*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	 编写一个方法，将字符串中的空格全部替换为"%20",假定字符串尾部有足够空间存放新增字符，并且知道字符串的真实长度。
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	public void replaceSpaceTest()
	{
		char[] chars = new char[256];
		int length = 0;
//		String str = "a b c d e";
//		chars = str.toCharArray();
//		length = str.length();
		for(int n = 0; n < 20; n++)
		{
			if(n%3 == 0)
			{
				chars[n] = ' ';
			}
			else chars[n] = 'a';
		}
		length = 20;
		replaceSpace(chars,length);
	}
	
	/**
	 * TODO
	 * yumo
	 * @param chars 字符数组
	 * @param length 字符串长度
	 * void
	 * 2015-3-16
	 */
	public void replaceSpace(char[] chars, int length)
	{
		int space_num = 0;
		for(char c : chars)
		{
			if(c == ' '){
				space_num ++;
			}
		}
		System.out.print("数组长度为:"+length+" 数组总长度："+chars.length+" 将空格替换为％20 前的字符数组为:");
		System.out.println(chars);
		System.out.println("空格的个数为："+space_num);
		int newLength = length + space_num *2;
		chars[newLength] = '\n';
		for(int  n = length -1; n >=0; n--)
		{
			if(chars[n] == ' ')
			{
				chars[newLength -1] = '0';
				chars[newLength -2] = '2';
				chars[newLength -3] = '%';
				
				newLength = newLength -3;
			}else{
				chars[newLength -1] = chars[n];
				newLength--;
			}
			
			
		}
		
		System.out.print(" 将空格替换为％20 后的字符数组为:");
		System.out.println(chars);
	}
	
	/*
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	
	/*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	 实现字符串压缩功能。利用字符出现的次数，编写一个方法，实现基本的字符串压缩功能，比如字符串为“aabccccaaa”，压缩后为:"a2b1c3a3".若压缩后的字符串没有变短，则返回原先的字符串。
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	public void compressStringTest()
	{
		String str = "aabccccaaa";
		System.out.println("压缩前字符串为："+str);
		
		str = compressString(str);
		System.out.println("压缩后字符串为："+str);
	}
	
	/**
	 * TODO 压缩字符串，利用StringBuffer实现
	 * yumo
	 * @param str
	 * @return
	 * String
	 * 2015-3-16
	 */
	public String compressString(String str)
	{
		StringBuffer buffer = new StringBuffer();
		int count = 1;
		char last = str.charAt(0);
		for(int n = 1; n < str.length(); n++)
		{
			if(last == str.charAt(n))
			{
				count ++;
			}
			else
			{
				buffer.append(last);
				buffer.append(count);
				count = 1;
				last = str.charAt(n);
			}
		}
		
		buffer.append(last);
		buffer.append(count);
		
		if(buffer.length() >= str.length())
		{
			return str;
		}
		
		return buffer.toString();
	}
	

	/*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	 给定一个4*4矩阵，如果做到不占用额外空间，将矩阵旋转90度
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	public void matrixRotateTest()
	{
		int[][] matrix = {{1,2,3,4}
	                     ,{5,6,7,8}
	                     ,{9,10,11,12}
	                     ,{13,14,15,16}
	                    };
		
		printlnMatrix(matrix,4);
		
		matrixRotate(matrix,4);
		
		/*
		 * 13 9 5  1
		 * 14 10 6  2
		 * 15 11,7 3
		 * 16 12 8 4
		 */
	}
	
	public void matrixRotate(int[][] matrix, int n)
	{
		for(int layer = 0; layer < n/2; layer++)
		{
			int first = layer ;
			int last = n - 1 - layer;
			
			for(int i = first ; i < last ; i++)
			{
				int offset = i-first;
				int top = matrix[first][i];
				
				//左到上
				matrix[first][i] = matrix[last-offset][first];
				
				//下到左
				matrix[last-offset][first] = matrix[last][last-offset];
				//左到下
				matrix[last][last-offset] = matrix[i][last];
				//上到下
				matrix[i][last] = top;
			}
		}
		
		printlnMatrix(matrix,4);
	}
	
	public void printlnMatrix(int[][] matrix, int n)
	{
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				System.out.print(matrix[i][j]+",");
			}
			System.out.println("\r\n");
		}
	}
	
	/*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	 编写一个算法，如果M*N矩阵中一个元素为0，则将其所在的行和列清零
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	public void matrixSetZerosTest()
	{
		int[][] matrix = {{1,2,3,4}
	                     ,{5,6,7,8}
	                     ,{9,0,11,12}
	                     ,{13,14,15,16}
	                    };
		
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[0].length; j++)
			{
				System.out.print(matrix[i][j]+",");
			}
			System.out.println("\r\n");
		}
		
		matrixSetZeros(matrix);
		
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[0].length; j++)
			{
				System.out.print(matrix[i][j]+",");
			}
			System.out.println("\r\n");
		}
		
		
		
		/*
		 * 13 9 5  1
		 * 14 10 6  2
		 * 15 11,7 3
		 * 16 12 8 4
		 */
	}
	
	public void matrixSetZeros(int[][] matrix)
	{
		boolean[] x = new boolean[matrix.length];
		boolean[] y = new boolean[matrix[0].length];
		
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[0].length; j++)
			{
				if(matrix[i][j] == 0)
				{
					x[i] = true;
					y[j] = true;
				}
			}
			
		}
		
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[0].length; j++)
			{
				if(x[i] || y[j])
				{
					matrix[i][j] = 0;
				}
			}
			
		}
	}
	
	/*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	  
	＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊*/
	
	/*如何取得字符串中第一个只出现一次的字符，如果优化算法使遍历次数更少*/

}
