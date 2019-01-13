/**
 * YmImageUtil.java
 * yumodev
 * 2015-1-15
 * 图片工具类。
 */
package com.yumo.common.media;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;

import com.yumo.common.io.YmCloseUtil;
import com.yumo.common.io.YmFileUtil;
import com.yumo.common.log.Log;
import com.yumo.common.net.YmOkHttpUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class YmImageUtil {

	/**
	 * 根据颜色生成一张图片
	 * @param color 颜色
	 * @param width 宽
	 * @param height 高
	 * @return
	 */
	public static Bitmap createBitmapFromColor(int color, int width, int height) {
		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		bmp.eraseColor(color);
		return bmp;
	}

	/**
	 * 将drawable转换成一张图片
	 * @param drawable
	 * @param width
	 * @param height
     * @return
     */
	public static Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int width = drawable.getIntrinsicWidth();
		width = width > 0 ? width : 1;
		int height = drawable.getIntrinsicHeight();
		height = height > 0 ? height : 1;

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}



	/**
	 * 圆角图片
	 * @param pBitmap
	 * @param pRoundpx
	 * @return
	 */
	public static Bitmap roundedCornerBitmap(Bitmap pBitmap, float pRoundpx) {
		// 创建图片画布大小
		try {
			Bitmap _NewBitmap = Bitmap.createBitmap(pBitmap.getWidth(),
					pBitmap.getHeight(), Bitmap.Config.ARGB_8888);
			// 创建画布
			Canvas _Canvas = new Canvas(_NewBitmap);
			// 设置画布透明
			_Canvas.drawARGB(0, 0, 0, 0);
			// 创建画笔
			Paint _Paint = new Paint();
			// 抗锯齿
			_Paint.setAntiAlias(true);
			// 画笔颜色透明
			_Paint.setColor(0xff000000);
			// 画与原图片大小一致的圆角矩形
			Rect _Rect = new Rect(0, 0, pBitmap.getWidth(), pBitmap.getHeight());
			RectF _RectF = new RectF(_Rect);
			_Canvas.drawRoundRect(_RectF, pRoundpx, pRoundpx, _Paint);

			// 设置下面张图片与上面张图片的交互模式
			_Paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			// 画原图到画布
			_Canvas.drawBitmap(pBitmap, _Rect, _Rect, _Paint);

			return _NewBitmap;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 往图片中部添加文字
	 * @param bitmap
	 * @param text
	 * @param textSize
	 * @param textColor
	 * @return
	 */
	public static Bitmap drawTextCenterToBitmap(Bitmap bitmap, String text, int textSize, int textColor) {
		android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
		// set default bitmap config if none
		if (bitmapConfig == null) {
			bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
		}
		// resource bitmaps are imutable,
		// so we need to convert it to mutable one
		bitmap = bitmap.copy(bitmapConfig, true);

		Canvas canvas = new Canvas(bitmap);
		// new antialised Paint
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// text color - #3D3D3D
		paint.setColor(textColor);
		// text size in pixels
		paint.setTextSize(textSize);
		// text shadow
		paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

		// draw text to the Canvas center
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		//int x = (bitmap.getWidth() - bounds.width()) / 2;
		//int y = (bitmap.getHeight() + bounds.height()) / 2;
		//draw  text  to the bottom
		int x = (bitmap.getWidth() - bounds.width())/10*5 ;
		int y = (bitmap.getHeight() + bounds.height())/10*5;
		canvas.drawText(text, x , y, paint);

		return bitmap;
	}


	public static BitmapFactory.Options getOptionsFromFile(String filePath){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		return options;
	}

	/**
	 * 直接从URL保存一张图片到Bitmap
	 * @param
	 * @return
	 * String 返回图片路径，如果失败就返回为空
	 */
	public static Bitmap saveImageToBitmap(String urlName){
		InputStream is = null;

		try {
			is = YmOkHttpUtil.getBodyInputStream(urlName);
			return BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			YmCloseUtil.close(is);
		}

		return null;
	}
	
	/**
	 * 下载一张图片,保存到输出流里面
	 * yumo
	 * @param
	 * @return
	 * String 返回图片路径，如果失败就返回为空
	 * 2015-1-15
	 */
	public static boolean saveImageToStream(String urlName, OutputStream outputStream){
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try {
			InputStream is = YmOkHttpUtil.getBodyInputStream(urlName);
			if (is != null){
				in = new BufferedInputStream(is, 8*1024);
				out = new BufferedOutputStream(outputStream, 8*1024);

				int b = 0;
				while((b = in.read())!=-1){
					out.write(b);
				}
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			YmCloseUtil.close(in);
			YmCloseUtil.close(out);
		}
		return false;
	}

	/**
	 * 保存bitmap到文件当中。
	 * yumodev
	 * @param bmp
	 * @param fileName
	 * return boolean
	 * 2015-1-17
	 */
	public static boolean saveBitmapToFile(Bitmap bmp, String fileName) throws IOException{
		if (bmp == null || TextUtils.isEmpty(fileName)){
			return false;
		}

		if (!YmFileUtil.isExistFile(fileName)){
			YmFileUtil.createFile(fileName);
		}else{
			YmFileUtil.deleteFile(fileName);
			YmFileUtil.createFile(fileName);
		}

		File file = new File(fileName);
		FileOutputStream out = new FileOutputStream(file);
		bmp.compress(CompressFormat.PNG, 100, out);
		out.flush();
		YmCloseUtil.close(out);
		return true;
	}


	/**
	 * 打开一张图片
	 * @param context
	 * @param filename
     */
	public static void showImageInView(Context context, String filename) {
		File file = new File(filename);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
			intent.setDataAndType(Uri.fromFile(file), "image/jpeg");
		}else{
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			String providerName = context.getPackageName()+".fileProvider";
			Log.i(Log.LIB_TAG, providerName+" "+file);
			Uri contentUri = FileProvider.getUriForFile(context, providerName, file);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		}

		try {
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 图片转换为Bast64
	 * @param bitmap
	 * @return
     */
	public static String toBase64(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] bytes = baos.toByteArray();

		return Base64.encodeToString(bytes, Base64.NO_WRAP);
	}


	/**
	 * 加载一张资源图片
	 * @param context
	 * @param resId
     * @return
     */
	public static Bitmap decodeResource(Context context, int resId) {
		return BitmapFactory.decodeResource(context.getResources(), resId);
	}
}
