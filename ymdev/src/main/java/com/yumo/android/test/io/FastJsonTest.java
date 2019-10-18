package com.yumo.android.test.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;

public class FastJsonTest extends YmTestFragment {
  public final String LOG_TAG = "FastJsonTest";
  public void testFormat(){
    String jsonString = "{\"_index\":\"book_shop\",\"_type\":\"it_book\",\"_id\":\"1\",\"_score\":1.0,\"_source\":{\"name\": \"Java编程思想（第4版）\"," +
        "\"author\": \"[美] Bruce Eckel\",\"category\": \"编程语言\",\"price\":  109.0,\"publisher\": \"机械工业出版社\",\"date\": \"2007-06-01\"," +
        "\"tags\": [ \"Java\", \"编程语言\" ]}}";
    JSONObject object = JSONObject.parseObject(jsonString);
    String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat,
        SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);

   Log.i(LOG_TAG, "testFormat:"+pretty);
  }
}
