/**
 * LockView.java
 * yumo
 * 2015-2-2
 * TODO 解锁自定义控件
 */
package com.yumo.android.common.widget;

import java.util.ArrayList;
import java.util.List;

import com.yumo.android.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class LockView extends View {
	
	private static final String TAG = LockView.class.getSimpleName();
	
	/**
	 * 是否第一次画图
	 */
	private boolean mIsInit = true;
	
	
	/**
	 * 画笔
	 */
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private Paint mPressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private Paint mErrorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private Matrix mMatrix = new Matrix();
	
	/**
	 * 坐标点
	 */
	private Point[][] mPoints = new Point[3][3];
	
	/**
	 * 选中的点
	 */
	private List<Point> mSelPoints = new ArrayList<Point>();
	
	//位置
	private float mHeight, mWidth, mOffsetX, mOffsetY, mCircleR;
	
	//位图资源
	private Bitmap mCirNormalBmp = null;
	private Bitmap mCirPressedBmp = null;
	private Bitmap mCirErrorBmp = null;
	private Bitmap mLinePressedBmp = null;
	private Bitmap mLineErrorBmp = null;
	
	//是否选中一个点
	private boolean mIsSelect = false;
	
	//是否结束
	private boolean mIsFinish = false;
	
	//移动但是没有选中点
	private boolean mMoveNoPoint = false;
	
	/**
	 * 移动中的坐标点
	 */
	private float mMoveX , mMoveY;

	/**
	 * @param context
	 */
	public LockView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		//Log.d(TAG, "begin draw");
		if(mIsInit)
		{
			initPoints();
			mIsInit = false;
		}
		
		pointsToCanvas(canvas);
		
		if(mSelPoints.size() > 0)
		{
			Point a  = mSelPoints.get(0);
			
			for(int i = 1; i < mSelPoints.size(); i++)
			{
				linesToCanvas(canvas, a, mSelPoints.get(i));
				a = mSelPoints.get(i);
			}
			
			if(mMoveNoPoint)
			{
				Log.d(TAG,"onDrawable mMoveNoPoint");
				linesToCanvas(canvas, a, new Point(mMoveX, mMoveY));
			}
		}
		
		//Log.d(TAG, "end draw");
	}
	
	private void initPoints()
	{
		int width = getWidth();
		int height = getHeight();
		Log.d(TAG,"init points: width:"+width + " height:"+height);
		//计算偏移量
		if(width < height)
		{
			//竖屏
			mOffsetY = (height - width) / 2;
			mHeight = mWidth = width;
		}
		else
		{
			//横屏
			mOffsetX = (width - height) / 2;
			mHeight = mWidth = height;
		}
		
		//计算位置坐标
		mPoints[0][0] = new Point(mOffsetX+mWidth/4, mOffsetY+mHeight/4);
		mPoints[0][1] = new Point(mOffsetX+mWidth/2, mOffsetY+mHeight/4);
		mPoints[0][2] = new Point(mOffsetX+mWidth - mWidth/4, mOffsetY+mHeight/4);
		
		mPoints[1][0] = new Point(mOffsetX+mWidth/4, mOffsetY+mHeight/2);
		mPoints[1][1] = new Point(mOffsetX+mWidth/2, mOffsetY+mHeight/2);
		mPoints[1][2] = new Point(mOffsetX+mWidth - mWidth/4, mOffsetY+mHeight/2);
		
		mPoints[2][0] = new Point(mOffsetX+mWidth/4, mOffsetY+mHeight - mHeight/4);
		mPoints[2][1] = new Point(mOffsetX+mWidth/2, mOffsetY+mHeight - mHeight/4);
		mPoints[2][2] = new Point(mOffsetX+mWidth - mWidth/4, mOffsetY+mHeight - mHeight/4);
		
		//圆形图片
		mCirNormalBmp = BitmapFactory.decodeResource(getResources(), R.drawable.circle_normal);
		mCirPressedBmp = BitmapFactory.decodeResource(getResources(), R.drawable.circle_pressed);
		mCirErrorBmp = BitmapFactory.decodeResource(getResources(), R.drawable.circle_error);
		mLineErrorBmp = BitmapFactory.decodeResource(getResources(), R.drawable.line_error);
		mLinePressedBmp = BitmapFactory.decodeResource(getResources(), R.drawable.line_pressed);
		
		//半径
		this.mCircleR = mCirNormalBmp.getWidth() / 2;
		
		// 定义画笔
		mPressPaint.setColor(Color.YELLOW);
		mPressPaint.setStyle(Paint.Style.STROKE);
		mPressPaint.setStrokeWidth(8);
		
		mErrorPaint.setColor(Color.RED);
		mErrorPaint.setStyle(Paint.Style.STROKE);
		mErrorPaint.setStrokeWidth(8);
		
	}
	
	private void pointsToCanvas(Canvas canvas)
	{
		for(int i  = 0; i < mPoints.length; i++)
		{
			for(int j = 0; j < mPoints[i].length; j++)
			{
				Point point = mPoints[i][j];
				if(point.mState == Point.STATE_NORMAL)
				{
					canvas.drawBitmap(mCirNormalBmp, point.x - mCircleR, point.y-mCircleR, mPaint);
				}
				else if(point.mState == Point.STATE_PRESSED)
				{
					canvas.drawBitmap(mCirPressedBmp, point.x - mCircleR, point.y-mCircleR, mPaint);
				}
				else if(point.mState == Point.STATE_ERROR)
				{
					canvas.drawBitmap(mCirErrorBmp, point.x - mCircleR, point.y-mCircleR, mPaint);
				}
			}
		}
	}
	
	private void linesToCanvas(Canvas canvas, Point pointX, Point pointY)
	{
		if(pointX.mState == Point.STATE_PRESSED)
		{
			canvas.drawLine(pointX.x,pointX.y, pointY.x, pointY.y, mPressPaint);
		}
		else
		{
			canvas.drawLine(pointX.x,pointX.y, pointY.x, pointY.y, mErrorPaint);
		}
	}
	
	/**
	 * TODO 画直线
	 * yumo
	 * @param canvas
	 * @param pointX
	 * @param pointY
	 * void
	 * 2015-2-3
	 */
	private void linesToCanvas1(Canvas canvas, Point pointX, Point pointY)
	{
		float distance = (float)distancePoints(pointX, pointY);
		float degrees = (float)getDegrees(pointX, pointY);
		canvas.rotate(degrees, pointX.x, pointX.y);
		Log.d(TAG,"lines, distance:"+distance+" degress:"+degrees);
		if(pointX.mState == Point.STATE_PRESSED)
		{
			Log.d(TAG,"lines: distance / mLinePressedBmp.getWidth():"+distance / mLinePressedBmp.getWidth());
			mMatrix.setScale(distance / mLinePressedBmp.getWidth(), 1);
			mMatrix.setTranslate(pointX.x - mLinePressedBmp.getWidth()/2, pointX.y - mLinePressedBmp.getHeight()/2);
			canvas.drawBitmap(mLinePressedBmp, mMatrix, mPaint);
		}
		else if(pointX.mState == Point.STATE_ERROR)
		{
			mMatrix.setScale(distance / mLineErrorBmp.getWidth(), 1);
			mMatrix.setTranslate(pointX.x - mLineErrorBmp.getWidth()/2, pointX.y - mLineErrorBmp.getHeight()/2);
			canvas.drawBitmap(mLineErrorBmp, mMatrix, mPaint);
		}
		canvas.rotate(-degrees, pointX.x, pointX.y);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		mMoveX = event.getX();
		mMoveY = event.getY();
		
		Point point = null;
		mMoveNoPoint = false;
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				mIsFinish = false;
				mIsSelect = false;
				resetSelectPoint();
				point = checkSelectPoint(mMoveX, mMoveY);
				if(point != null)
				{
					mIsSelect = true;
				}
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				if (mIsSelect ){
					point = checkSelectPoint(mMoveX, mMoveY);
					if(point == null)
					{
						mMoveNoPoint = true;
					}
				} 
				break;
			}
			
			case MotionEvent.ACTION_UP:
			{
				mIsFinish = true;
				mIsSelect = false;
				
				break;
			}
			
		}
		
		do
		{
			if(mIsFinish) break;
			
			if(!mIsSelect) break;
			

			if(point != null)
			{
				if(!addSelect(point)) mMoveNoPoint = true;
			}
			
		}while(false);
		
		if(mIsFinish)
		{
			if(checkRightLock())
			{
				Log.d(TAG,"ACTION_UP rightLock");
			}
			else
			{
				Log.d(TAG,"ACTION_UP errorLock");
			}
		}
		
		this.postInvalidate();
		return true;
	}
	
	/**
	 * TODO 加入选中节点。
	 * yumo
	 * @param point 
	 * @return 第一成功加入返回true，重复添加返回false
	 * boolean
	 * 2015-2-3
	 */
	private boolean addSelect(Point point)
	{
		if(point == null) return false;
		if(!mSelPoints.contains(point))
		{
			point.mState = Point.STATE_PRESSED;
			return mSelPoints.add(point);
		}
		
		return false;
	}
	
	/**
	 * TODO 判断结果是否正确
	 * yumo
	 * @return
	 * boolean
	 * 2015-2-3
	 */
	private boolean checkRightLock()
	{
		boolean bRight = false;
		do{

			if(mSelPoints == null) break;
			
			if(mSelPoints.size() == 1)
			{
				resetSelectPoint();
			}
			
			if(mSelPoints.size() < 5) break;
			
			bRight = true;
		}while(false);
		
		if(!bRight) setSelPointsState(Point.STATE_ERROR);
		
		return bRight;
	}
	
	/**
	 * TODO 设置状态
	 * yumo
	 * @param state
	 * void
	 * 2015-2-3
	 */
	private void setSelPointsState(int state)
	{
		for(int n = 0; n < mSelPoints.size(); n++)
		{
			mSelPoints.get(n).mState = state;
		}
	}
	
	/**
	 * TODO 重置选中的图标
	 * yumo
	 * void
	 * 2015-2-2
	 */
	private void resetSelectPoint()
	{
		setSelPointsState(Point.STATE_NORMAL);
		this.mSelPoints.clear();
	}
	
	/**
	 * TODO 检测移动到的点是不是 圆形点
	 * yumo
	 * @param moveX
	 * @param moveY
	 * @return
	 * Point
	 * 2015-2-3
	 */
	private Point checkSelectPoint(float moveX, float moveY)
	{
		for(int i = 0; i < mPoints.length; i++)
		{
			for(int j = 0; j < mPoints[i].length; j++)
			{
				if(isEqualsPoint(mPoints[i][j].x, mPoints[i][j].y, moveX, moveY, mCircleR))
				{
					//Point point = new Point(mPoints[i][j].x, mPoints[i][j].y);
					return mPoints[i][j];
				}
			}
		}
		
		return null;
	}
	
	/**
	 * TODO 检测坐标点是否在圆形坐标的范围里面。
	 * yumo
	 * @param pointX
	 * @param pointY
	 * @param moveX
	 * @param moveY
	 * @param r
	 * @return
	 * boolean
	 * 2015-2-3
	 */
	private boolean isEqualsPoint(float pointX, float pointY, float moveX, float moveY, float r)
	{
		return Math.sqrt((pointX - moveX)*(pointX - moveX) + (pointY - moveY)*(pointY - moveY)) < r;
	}
	
	private double distancePoints(Point a, Point b)
	{
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}
	
	private float getDegrees1(Point a, Point b)
	{
		float  x=Math.abs(a.x-b.x);
	    float  y=Math.abs(a.y-b.y);
	    double z=Math.sqrt(x*x+y*y);
	    return Math.round((float)(Math.asin(y/z)/Math.PI*180));
	}
	
	/**
	 * TODO 取两个坐标点之间的角度
	 * yumo
	 * @param a
	 * @param b
	 * @return
	 * float
	 * 2015-2-3
	 */
	private double getDegrees(Point a, Point b)
	{
		double degrees = 0;
		double tan = (b.x - a.x) / (a.y - b.y);
		do{

			if(a.x == b.x)
			{
				if(b.y < a.y) { degrees = 90;}
				else if(b.y < a.y) degrees = 270;
			}
			else if(a.y == b.y)
			{
				if(b.x < a.x) { degrees = 0;}
				else if(b.x < a.x) degrees = 180;
			}
			else if(a.x < b.x && a.y > b.y)
			{
				degrees = Math.atan(tan);
				degrees = degrees * 180 / Math.PI;
			}
			else if( a.x > b.x && a.y != b.y)
			{
				degrees = Math.atan(tan);
				degrees = 180 + degrees * 180 / Math.PI;
			}
			else if(a.x < b.x && a.y < b.y )
			{
				degrees = Math.atan(tan);
				degrees = 360 + degrees * 180 / Math.PI;
			}
		}while(false);
		
		return degrees;
	}
	/**
	 * yumo
	 * 自定义坐标点
	 */
	class Point{
		
		/**
		 * 正常状态
		 */
		public static final int STATE_NORMAL = 0;
		
		/**
		 * 按下状态
		 */
		public static final int STATE_PRESSED = 1;
		
		/**
		 * 错误状态
		 */
		public static final int STATE_ERROR = 2;
		
		public float x, y;
		
		public int mIndex = 0;
		
		
		/**
		 * 状态
		 */
		public int mState = STATE_NORMAL;
		
		public Point()
		{
			
		}
		
		public Point(float x, float y)
		{
			this.x = x;
			this.y = y;
		}
		
	}

}
