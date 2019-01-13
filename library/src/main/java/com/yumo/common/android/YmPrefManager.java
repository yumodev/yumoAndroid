package com.yumo.common.android;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * SharePreferences操作的单例类
 * yumodev
 * 2014-12-3
 */
public class YmPrefManager {
	private final static String LOG_TAG = "YmPrefManager";

	/**
	 * 默认的Context。
	 */
	private Context mContext = null;


	private volatile static YmPrefManager mInstance = null;

	public void initialize(Context context) {
		mContext = context;
	}

	public static YmPrefManager getInstance() {
		if (mInstance == null){
			synchronized (YmPrefManager.class){
				if (mInstance == null){
					mInstance = new YmPrefManager();
				}
			}
		}

		return mInstance;
	}

	/**
	 * 获取默认的Preferences
	 * @return
	 */
	public SharedPreferences getPreferences() {
		SharedPreferences shared = null;
		if (mContext != null) {
			shared = PreferenceManager.getDefaultSharedPreferences(mContext);
		}
		return shared;
	}

	/**
	 * 获取制定文件的Shareference对象
	 * @param fileName
	 * @return
	 */
	public SharedPreferences getPreferences(String fileName) {
		SharedPreferences shared = null;
		if (mContext != null) {
			shared = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		}
		return shared;
	}


	/**
	 * 根据传入Context的不同获取制定文件的Shareference对象
	 * @param fileName
	 * @param context
	 * @return
	 */
	public SharedPreferences getPreferences(Context context, String fileName) {
		SharedPreferences shared = null;
		if (context != null && !TextUtils.isEmpty(fileName)) {
			shared = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		}else{
			shared = PreferenceManager.getDefaultSharedPreferences(context);
		}
		return shared;
	}


	/**
	 * 获取默认文件的所有的key
	 * @return
	 */
	public Map<String, ?> getAllData() {
		SharedPreferences pref = getPreferences();
		if (pref != null) {
			return pref.getAll();
		}
		return null;
	}

	/**
	 * 获取指定文件的所有key
	 * @param fileName
	 * @return
	 */
	public Map<String, ?> getAllData(String fileName) {
		SharedPreferences pref = getPreferences(fileName);
		if (pref != null) {
			return pref.getAll();
		}
		return null;
	}

	/**
	 * 根据传入的Context和fileName获取所有的key。
	 * @param context
	 * @param fileName
	 * @return
	 */
	public Map<String, ?> getAllData(Context context, String fileName) {
		SharedPreferences pref = getPreferences(fileName);
		if (pref != null) {
			return pref.getAll();
		}
		return null;
	}

	/**
	 * 从默认文件中删除一个key。
	 * @param key
	 * @return
	 */
	public boolean removeData(String key, boolean isApply) {
		return removeData(null, key, isApply);
	}

	/**
	 * 从指定文件中删除一个key
	 * @param fileName
	 * @param key
	 * @param isApply
	 * @return
	 */
	public boolean removeData(String fileName, String key, boolean isApply) {
		return removeData(mContext, fileName, key, isApply);
	}

	/**
	 * 根据传入的context从指定文件删除一个KEY。
	 * @param context
	 * @param fileName
	 * @param key
	 * @param isApply
	 * @return
	 */
	public boolean removeData(Context context, String fileName, String key, boolean isApply) {
		if (TextUtils.isEmpty(key)){
			return false;
		}

		boolean result = false;
		Editor editor = getEditor(context, fileName);
		if (editor != null) {
			editor.remove(key);
			if (isApply){
				editor.apply();
				result = true;
			}else{
				result = editor.commit();
			}
		}

		return result;
	}


	/**
	 * 获取key类型的字符串的值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getData(String key, String defaultValue) {
		return getValue(null, key, defaultValue);
	}

	/**
	 * 获取字符串
	 * @param fileName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getValue(String fileName, String key, String defaultValue) {
		return getValue(mContext, fileName, key, defaultValue);
	}

	/**
	 * 获取字符串
	 * @param context
	 * @param fileName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getValue(Context context, String fileName, String key, String defaultValue) {
		if (TextUtils.isEmpty(key)){
			return defaultValue;
		}

		String result = "";
		SharedPreferences pref = getPreferences(context, fileName);
		if (pref != null && pref.contains(key)) {
			result = pref.getString(key, defaultValue);
		}

		return result;
	}

	public boolean setValue(String key, String data, boolean isApply) {
		return setValue(null, key, data, isApply);
	}

	public boolean setValue(String fileName, String key, String data, boolean isApply) {
		return setValue(mContext, fileName, key, data, isApply);
	}

	public boolean setValue(Context context, String fileName, String key, String data, boolean isApply) {
		if (TextUtils.isEmpty(key)){
			return false;
		}

		boolean result = false;
		Editor editor = getEditor(context, fileName);
		if (editor != null) {
			editor.putString(key, data);
			if (isApply){
				editor.apply();
				result = true;
			}else{
				result = editor.commit();
			}
		}

		return result;
	}


	/**
	 * 设置boolean类型的值。
	 * @param key
	 * @param data
	 * @param isApply
	 * @return
	 */
	public boolean setValue(String key, boolean data, boolean isApply) {
		return setValue(null, key, data, isApply);
	}

	/**
	 * 设置boolean类型的值。
	 * @param key
	 * @param data
	 * @param isApply
	 * @return
	 */
	public boolean setValue(String fileName, String key, boolean data, boolean isApply) {
		return setValue(mContext, fileName, key, data, isApply);
	}

	/**
	 * 设置boolean类型的值。
	 * @param key
	 * @param data
	 * @param isApply
	 * @return
	 */
	public boolean setValue(Context context, String fileName, String key, boolean data, boolean isApply) {
		if (TextUtils.isEmpty(key)){
			return false;
		}

		boolean result = false;
		Editor editor = getEditor(context, fileName);
		if (editor != null) {
			editor.putBoolean(key, data);
			if (isApply){
				editor.apply();
				result = true;
			}else{
				result = editor.commit();
			}
		}

		return result;
	}


	/**
	 * 获取类型为boolean的值
	 * @param fileName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public boolean getValue(String fileName, String key, boolean defaultValue) {
		return getValue(mContext, fileName, key, defaultValue);
	}

	/**
	 * 获取类型为boolean的值
	 * @param context
	 * @param fileName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Boolean getValue(Context context, String fileName, String key, boolean defaultValue) {
		if (TextUtils.isEmpty(key)){
			return defaultValue;
		}

		boolean result = false;
		SharedPreferences pref = getPreferences(context, fileName);
		if (pref != null && pref.contains(key)) {
			result = pref.getBoolean(key, defaultValue);
		}

		return result;
	}

	/**
	 * 设置int类型的值。
	 * @param key
	 * @param data
	 * @param isApply
	 * @return
	 */
	public boolean setValue(String key, int data, boolean isApply) {
		return setValue(null, key, data, isApply);
	}

	/**
	 * 设置int类型的值。
	 * @param key
	 * @param data
	 * @param isApply
	 * @return
	 */
	public boolean setValue(String fileName, String key, int data, boolean isApply) {
		return setValue(mContext, fileName, key, data, isApply);
	}

	/**
	 * 设置int类型的值。
	 * @param key
	 * @param data
	 * @param isApply
	 * @return
	 */
	public boolean setValue(Context context, String fileName, String key, int data, boolean isApply) {
		if (TextUtils.isEmpty(key)){
			return false;
		}

		boolean result = false;
		Editor editor = getEditor(context, fileName);
		if (editor != null) {
			editor.putInt(key, data);
			if (isApply){
				editor.apply();
				result = true;
			}else{
				result = editor.commit();
			}
		}

		return result;
	}

	/**
	 * 获取类型为boolean的值
	 * @param key
	 * @return
	 */
	public Boolean getValue(String key, boolean defaultValue) {
		return getValue(null, key, defaultValue);
	}

	/**
	 * 获取类型为int的值
	 * @param key
	 * @return
	 */
	public int getValue(String key, int defaultValue) {
		return getValue(null, key, defaultValue);
	}

	/**
	 * 获取类型为String的值
	 * @param key
	 * @return
	 */
	public String getValue(String key, String defaultValue) {
		return getValue(null, key, defaultValue);
	}

	/**
	 * 获取类型为Int的值
	 * @param fileName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getValue(String fileName, String key, int defaultValue) {
		return getValue(mContext, fileName, key, defaultValue);
	}

	/**
	 * 获取类型为int的值
	 * @param context
	 * @param fileName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getValue(Context context, String fileName, String key, int defaultValue) {
		if (TextUtils.isEmpty(key)){
			return defaultValue;
		}

		int result = defaultValue;
		SharedPreferences pref = getPreferences(context, fileName);
		if (pref != null && pref.contains(key)) {
			result = pref.getInt(key, defaultValue);
		}

		return result;
	}




	/**
	 * 设置long类型的值。
	 * @param key
	 * @param data
	 * @param isApply
	 * @return
	 */
	public boolean setValue(String key, long data, boolean isApply) {
		return setValue(null, key, data, isApply);
	}

	/**
	 * 设置long类型的值。
	 * @param key
	 * @param data
	 * @param isApply
	 * @return
	 */
	public boolean setValue(String fileName, String key, long data, boolean isApply) {
		return setValue(mContext, fileName, key, data, isApply);
	}

	/**
	 * 设置long类型的值。
	 * @param key
	 * @param data
	 * @param isApply
	 * @return
	 */
	public boolean setValue(Context context, String fileName, String key, long data, boolean isApply) {
		if (TextUtils.isEmpty(key)){
			return false;
		}

		boolean result = false;
		Editor editor = getEditor(context, fileName);
		if (editor != null) {
			editor.putLong(key, data);
			if (isApply){
				editor.apply();
				result = true;
			}else{
				result = editor.commit();
			}
		}

		return result;
	}

	/**
	 * 获取类型为int的值
	 * @param key
	 * @return
	 */
	public long getValue(String key, long defaultValue) {
		return getValue(null, key, defaultValue);
	}

	/**
	 * 获取类型为Int的值
	 * @param fileName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public long getValue(String fileName, String key, long defaultValue) {
		return getValue(mContext, fileName, key, defaultValue);
	}

	/**
	 * 获取类型为long的值
	 * @param context
	 * @param fileName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public long getValue(Context context, String fileName, String key, long defaultValue) {
		if (TextUtils.isEmpty(key)){
			return defaultValue;
		}

		long result = defaultValue;
		SharedPreferences pref = getPreferences(context, fileName);
		if (pref != null && pref.contains(key)) {
			result = pref.getLong(key, defaultValue);
		}

		return result;
	}

	private Editor getEditor(){
		return getEditor(mContext, null);
	}

	private Editor getEditor(Context context, String fileName) {
		Editor editor = null;
		SharedPreferences pref = getPreferences(context, fileName);
		if (pref != null) {
			editor = pref.edit();
		}
		return editor;
	}

}
