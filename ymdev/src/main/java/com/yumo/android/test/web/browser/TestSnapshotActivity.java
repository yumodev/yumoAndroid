package com.yumo.android.test.web.browser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yumo.android.R;
import com.yumo.common.io.YmAdFileUtil;
import com.yumo.common.media.YmImageUtil;
import com.yumo.common.media.YmViewSnapshotUtil;

@SuppressLint("SetJavaScriptEnabled")
public class TestSnapshotActivity extends Activity {

	private Button leftStepBtn,nextStepBtn;
	private ImageView imageView;
	private WebView webView;
	private Bitmap bm,bw;
	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0; 
	private AlertDialog showImageDialog;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 0, 0, "相册");
		menu.add(0, 1, 0, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
			getAlbum.setType(IMAGE_TYPE);
			startActivityForResult(getAlbum, IMAGE_CODE);
			break;
		case 1:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// imageView = new ImageView(this);
		setContentView(R.layout.test_webview_snapview);
		webView = (WebView) findViewById(R.id.WebView);
		webView.getSettings().setJavaScriptEnabled(true);
		
		imageView = (ImageView) findViewById(R.id.imageView);
//		bm = BitmapFactory.decodeResource(this.getResources(),
//				R.drawable.ic_launcher);
//		imageView.setImageBitmap(bm);
		
		webView.setWebChromeClient(new WebChromeClient() {
			@SuppressWarnings("deprecation")
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					Log.d("SnapShotActivity", newProgress+"");
//					Picture picture = webView.capturePicture();
//					int width = picture.getWidth();
//					int height = picture.getHeight();
//					if (width > 0 && height > 0) {
//						bw = Bitmap.createBitmap(width, height,
//								Bitmap.Config.RGB_565);
//						Canvas canvas = new Canvas(bw);
//						picture.draw(canvas);
//						imageView.setBackgroundDrawable(new BitmapDrawable(bw));
//					} else {
//						webView.loadUrl("http://www.baidu.com");
//						webView.setDrawingCacheEnabled(true);
//					}
					//snapshotWebView(webView);
				}
			}
		});
		//webView.loadUrl("http://news.sina.cn/gn/2016-05-09/detail-ifxryhhi8536395.d.html?vt=4&wm=9212_0001&sid=172021");
		webView.loadUrl("http://m.baidu.com");
		webView.setDrawingCacheEnabled(true);

		leftStepBtn = (Button) findViewById(R.id.leftStepBtn);
		leftStepBtn.setVisibility(View.VISIBLE);
		leftStepBtn.setText("截屏");
		leftStepBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �õ���ǰview����view�ṹ�еĸ�view
				View vv = v.getRootView();
				// ��������
				vv.setDrawingCacheEnabled(true);
				// ȡ��λͼ
				bm = vv.getDrawingCache();
				showConfirmDialog(1).show();
			}
		});
		
		nextStepBtn = (Button) findViewById(R.id.nextStepBtn);
		nextStepBtn.setVisibility(View.VISIBLE);
		nextStepBtn.setText("截图");
		nextStepBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//showConfirmDialog(2).show();
				snapshotWebView(webView);
			}
		});

		// webView.loadUrl("http://www.google.com");
	}

	private void snapshotWebView(WebView webView){
		String savePath = null;
		//Bitmap bitmap = YmViewSnapshotUtil.getWebViewShopshat(webView);
		Bitmap bitmap = YmViewSnapshotUtil.getWebViewShotshatByPicture(webView);

		if (bitmap != null) {
			savePath = YmAdFileUtil.getFileCache(getApplicationContext(), "snapshot") + UUID.randomUUID().toString() +".jpg";
			try {
				YmImageUtil.saveBitmapToFile(bitmap, savePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!TextUtils.isEmpty(savePath)) {
			YmImageUtil.showImageInView(getApplicationContext(), savePath);
		}
	}

	@SuppressLint("SdCardPath")
	public void saveBitmap(Bitmap bitmap, String bitName) throws IOException {
		File folder = new File("/mnt/sdcard/dcim/Camera/");
		if (!folder.exists()) {
			folder.mkdir();
		}
		File file = new File("/mnt/sdcard/dcim/Camera/" + bitName + ".jpg");
		Toast.makeText(TestSnapshotActivity.this, "保存图片中", Toast.LENGTH_SHORT).show();
		FileOutputStream out;
		if (!file.exists()) {

			try {
				out = new FileOutputStream(file);
				if (bitmap.compress(Bitmap.CompressFormat.PNG, 70, out)) {
					Toast.makeText(TestSnapshotActivity.this, "成功存入相册",
							Toast.LENGTH_SHORT).show();
					out.flush();
					out.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ȷ������ AlertDialog
	 * @param i 
	 */
	public AlertDialog showConfirmDialog(final int i) {
		return new AlertDialog.Builder(TestSnapshotActivity.this)
				.setIcon(android.R.drawable.progress_horizontal)
				.setTitle("确认保存").setMessage("请确认是否保存图片!")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						try {
							if(i == 1){
								String num = getRandomCode();
								saveBitmap(bm, "imagePic" + num);
							}else if(i == 2){
								String num = getRandomCode();
								saveBitmap(bw, "imagePic" + num);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).setNegativeButton("取消", null).create();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) { // �˴��� RESULT_OK ��ϵͳ�Զ����һ������
			return;
		} else if (resultCode == RESULT_OK) {
			// ���ĳ������ContentProvider���ṩ���� ����ͨ��ContentResolver�ӿ�
			ContentResolver resolver = this.getContentResolver();
			// �˴��������жϽ��յ�Activity�ǲ�������Ҫ���Ǹ�
			if (requestCode == IMAGE_CODE) {
				try {
					Uri originalUri = data.getData(); // ���ͼƬ��uri
					// Bitmap bm = BitmapFactory.decodeStream(resolver
					// .openInputStream(originalUri));
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = false;
					options.inSampleSize = 2; // width��hight��Ϊԭ���Ķ���һ
					Bitmap bitmap = BitmapFactory.decodeStream(
							resolver.openInputStream(originalUri), null,
							options);
					ImageView imageView = new ImageView(this);
					imageView.setImageBitmap(bitmap);
					showImageDialog = new AlertDialog.Builder(this)
							.setView(imageView).create();
					showImageDialog.show();
				} catch (IOException e) {
					Log.e("Exception", e.getMessage(), e);
				}
			}
		}
	}

	private String getRandomCode() {
		String num = "";
		for (int i = 0; i < 10; i++) {
			int f = (int) (Math.random() * 10);
			num += f;
		}
		return num;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return false;
	}

}