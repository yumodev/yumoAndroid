/**
 * VideoInfo.java 视频的信息
 * yumodev
 * 2015-1-26
 */
package com.yumo.android.common.data;

/**
 * yumodev
 *
 */
public class VideoInfo {

	public String mId = "";
	
	public String mFilePath = "";
	
	public String mThumbPath = "";
	
	public String mMimeType = "";
	
	public String mTitle = "";
	
	public int mDuration = 0;
	
	public String toString()
	{
		return mId+ " " + mFilePath + " "+mThumbPath + " "
			   + mMimeType + " " + mTitle + " " + mDuration;
 	}

}
