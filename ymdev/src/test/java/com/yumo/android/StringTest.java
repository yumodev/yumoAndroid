package com.yumo.android;

import org.junit.Test;

public class StringTest {

  @Test
  public void testSubString(){
    String str = "1.0.0_10";
    int lastIndex = str.lastIndexOf("_");
    String numStr = str.substring(lastIndex+1);
    System.out.println(numStr);
    int num = Integer.valueOf(numStr);
    System.out.println(num);
  }
}
