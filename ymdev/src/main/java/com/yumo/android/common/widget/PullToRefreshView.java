package com.yumo.android.common.widget;

import com.yumo.android.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;


public class PullToRefreshView extends LinearLayout {
    private final String TAG = PullToRefreshView.class.getSimpleName() +"  ";
	// refresh states
	private static final int PULL_TO_REFRESH = 2;
	private static final int RELEASE_TO_REFRESH = 3;
	private static final int REFRESHING = 4;
	// pull state
	private static final int PULL_UP_STATE = 0;
	private static final int PULL_DOWN_STATE = 1;
	/**
	 * last y
	 */
	private int mLastMotionY;
	/**
	 * lock
	 */
//	private boolean mLock;
	/**
	 * header view
	 */
	private View mHeaderView;
	/**
	 * footer view
	 */
	private View mFooterView;
	/**
	 * list or grid
	 */
	private AdapterView<?> mAdapterView;
	/**
	 * scrollview
	 */
	private ScrollView mScrollView;
	/**
	 * header view height
	 */
	private int mHeaderViewHeight;
	/**
	 * footer view height
	 */
	private int mFooterViewHeight;
	/**
	 * header view image
	 */
	private ImageView mHeaderImageView;
	/**
	 * footer view image
	 */
	private ImageView mFooterImageView;
	/**
	 * header tip text
	 */
	private TextView mHeaderTextView;
	/**
	 * footer tip text
	 */
	private TextView mFooterTextView;
	/**
	 * header refresh time
	 */
	private TextView mHeaderUpdateTextView;
	/**
	 * footer refresh time
	 */
	// private TextView mFooterUpdateTextView;
	/**
	 * header progress bar
	 */
	private ProgressBar mHeaderProgressBar;
	/**
	 * footer progress bar
	 */
	private ProgressBar mFooterProgressBar;
	/**
	 * layout inflater
	 */
	private LayoutInflater mInflater;
	/**
	 * header view current state
	 */
	private int mHeaderState;
	/**
	 * footer view current state
	 */
	private int mFooterState;
	/**
	 * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
	 */
	private int mPullState;
	/**
	 * 
	 */
	private RotateAnimation mFlipAnimation;
	/**
	 * 
	 */
	private RotateAnimation mReverseFlipAnimation;
	/**
	 * footer refresh listener
	 */
	private OnFooterRefreshListener mOnFooterRefreshListener;
	/**
	 * footer refresh listener
	 */
	private OnHeaderRefreshListener mOnHeaderRefreshListener;
	/**
	 * last update time
	 */
//	private String mLastUpdateTime;
	
	private int mPageIndex = 1;
	
	/**
	 * 开启上拉刷新
	 */
	private boolean mIsPullUp = false;

	/**
	 * 是否开启上拉刷新。
	 */
	public void setPullUp(boolean pullUp){
		Log.d(TAG, "setPullUp() start pullup = " + pullUp);
		mIsPullUp = pullUp;
		Log.d(TAG, "setPullUp() end.");
	}
	
	public void setPageIndex(int pageIndex)
	{
		mPageIndex = pageIndex;
	}

	public PullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PullToRefreshView(Context context) {
		super(context);
		init();
	}

	/**
	 * init
	 * 
	 * @param context
	 */
	private void init() {
		setOrientation(LinearLayout.VERTICAL);
		// Load all of the animations we need in code rather than through XML
		mFlipAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mFlipAnimation.setInterpolator(new LinearInterpolator());
		mFlipAnimation.setDuration(250);
		mFlipAnimation.setFillAfter(true);
		
		mReverseFlipAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipAnimation.setDuration(250);
		mReverseFlipAnimation.setFillAfter(true);

		mInflater = LayoutInflater.from(getContext());
		addHeaderView();
	}

	private void addHeaderView() {
		// header view
		mHeaderView = mInflater.inflate(R.layout.refresh_header, this, false);

		mHeaderImageView = (ImageView) mHeaderView
				.findViewById(R.id.pull_to_refresh_image);
		mHeaderTextView = (TextView) mHeaderView
				.findViewById(R.id.pull_to_refresh_text);
		mHeaderUpdateTextView = (TextView) mHeaderView
				.findViewById(R.id.pull_to_refresh_updated_at);
		mHeaderProgressBar = (ProgressBar) mHeaderView
				.findViewById(R.id.pull_to_refresh_progress);
		// header layout
		measureView(mHeaderView);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mHeaderViewHeight);
		params.topMargin = -(mHeaderViewHeight);
		
		addView(mHeaderView, params);
		
		Log.d(TAG, TAG+" heanerView height:%d" + mHeaderViewHeight);
	}

	private void addFooterView() {
		// footer view
		mFooterView = mInflater.inflate(R.layout.refresh_footer, this, false);
		mFooterImageView = (ImageView) mFooterView
				.findViewById(R.id.pull_to_load_image);
		mFooterTextView = (TextView) mFooterView
				.findViewById(R.id.pull_to_load_text);
		mFooterProgressBar = (ProgressBar) mFooterView
				.findViewById(R.id.pull_to_load_progress);
		// footer layout
		measureView(mFooterView);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mFooterViewHeight);
		Log.d(TAG, TAG+" footerViewView height:%d" + mHeaderViewHeight);
		Log.d(TAG, TAG+ " getHeight"+getHeight());
		Log.d(TAG, TAG+ " getMeasuredHeight"+this.getMeasuredHeight());
		mFooterView.setVisibility(View.GONE);
		addView(mFooterView, params);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// footer view 
		Log.d(TAG, TAG+ "onFinishInflate");
		addFooterView();
		initContentAdapterView();
	}

	/**
	 * init AdapterView like ListView,GridView and so on;or init ScrollView
	 * 
	 */
	private void initContentAdapterView() {
		int count = getChildCount();
		if (count < 3) {
			throw new IllegalArgumentException(
					"This layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}
		View view = null;
		for (int i = 0; i < count - 1; ++i) {
			view = getChildAt(i);
			if (view instanceof AdapterView<?>) {
				mAdapterView = (AdapterView<?>) view;
			}
			if (view instanceof ScrollView) {
				// finish later
				mScrollView = (ScrollView) view;
			}
		}
		if (mAdapterView == null && mScrollView == null) {
			throw new IllegalArgumentException(
					"must contain a AdapterView or ScrollView in this layout!");
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int y = (int) e.getRawY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = y - mLastMotionY;
			if (Math.abs(deltaY) > 8 &&  isRefreshViewScroll(deltaY)) {
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return false;
	}

	/*
	 * 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		if (mLock) {
//			return true;
//		}
		int y = (int) event.getRawY();
		//Log.d(TAG, TAG+"touchEvent:"+YmTouchEventUtil.getTouchAction(event.getAction()) + YmTouchEventUtil.getTouchPoint(event)) ;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// onInterceptTouchEvent
			// mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = y - mLastMotionY;
			if (mPullState == PULL_DOWN_STATE) {
				headerPrepareToRefresh(deltaY);
				// setHeaderPadding(-mHeaderViewHeight);
			} else if (mPullState == PULL_UP_STATE) {
				if(mIsPullUp){
					footerPrepareToRefresh(deltaY);
				}
			}
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int topMargin = getHeaderTopMargin();
			if (mPullState == PULL_DOWN_STATE) {
				if (topMargin >= 0) {
					headerRefreshing();
				} else {
					//this.mOnHeaderRefreshListener.onHeaderViewShow(this, mHeaderView);
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			} else if (mPullState == PULL_UP_STATE) {
				if (Math.abs(topMargin) >= mHeaderViewHeight
						+ mFooterViewHeight) {
					footerRefreshing();
				} else {
					//this.mOnFooterRefreshListener.onFooterViewShow(this, mHeaderView);
					mFooterView.setVisibility(View.GONE);
					setHeaderTopMargin(-(mHeaderViewHeight));
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 更新下拉状态。
	 * 
	 * @param deltaY
	 *            , deltaY > 0 
	 * @return
	 */
	private boolean isRefreshViewScroll(int deltaY) {
		if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
			Log.d(TAG, TAG+" isRefreshViewScroll refreshing");
			return false;
		}
		//ListView、GridView
		if (mAdapterView != null) {
			if (deltaY > 0) {
                //下拉刷新
				View child = mAdapterView.getChildAt(0);
				if (child == null) {
					return false;
				}
				//列表的第一项显示的时候，就设置状态为下拉状态。
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& child.getTop() == 0) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& Math.abs(top - padding) <= 8) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}

			} else if (deltaY < 0) {
				View lastChild = mAdapterView.getChildAt(mAdapterView
						.getChildCount() - 1);
				if (lastChild == null) {
					return false;
				}
				// 上拉刷新
				Log.d(TAG, TAG+"getBottom:%d"+lastChild.getBottom() +" view getHeight:"+getHeight());
				if (lastChild.getBottom() <= getHeight()
						&& mAdapterView.getLastVisiblePosition() == mAdapterView
								.getCount() - 1) {
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		//如果包括的为scrollView
		if (mScrollView != null) {
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0) {
				mPullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0
					&& child.getMeasuredHeight() <= getHeight()
							+ mScrollView.getScrollY()) {
				mPullState = PULL_UP_STATE;
				return true;
			}
		}
		return false;
	}

	/**
	 * header 
	 * 
	 * @param deltaY
	 *            
	 */
	private void headerPrepareToRefresh(int deltaY) {
		//Log.d(TAG,TAG+"begin headerPrepareToRefresh:deltay " + deltaY);
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
			Log.d(TAG, TAG+" headerPrepareToRefresh reaease_to_refresh");
			mHeaderTextView.setText(R.string.pull_to_refresh_release_label);
			mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			mHeaderImageView.clearAnimation();
			mHeaderImageView.startAnimation(mFlipAnimation);
			mHeaderState = RELEASE_TO_REFRESH;
			
//			if (mOnHeaderRefreshListener != null) {
//				mOnHeaderRefreshListener.onHeaderPrepareRefresh(this, mHeaderView);
//			}
		} else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {
			Log.d(TAG, TAG+" headerPrepareToRefresh pull_to_refresh");
			mHeaderImageView.clearAnimation();
			mHeaderImageView.startAnimation(mFlipAnimation);
			// mHeaderImageView.
			mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
			//mHeaderTextView.setText(String.format(getString(R.string.page_pull_to_refresh_pull_label),mPageIndex);
			mHeaderState = PULL_TO_REFRESH;
			
		}
		//Log.d(TAG,TAG+"end headerPrepareToRefresh");
	}

	/**
	 * footer 
	 * 
	 * @param deltaY
	 */
	private void footerPrepareToRefresh(int deltaY) {
		//Log.d(TAG,TAG+"begin footerPrepareToRefresh:deltay"+deltaY);
		
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
				&& mFooterState != RELEASE_TO_REFRESH) {
			Log.d(TAG, TAG+" footerPrepareToRefresh reaease_to_refresh");
			mFooterTextView
					.setText(R.string.pull_to_refresh_footer_release_label);
			mFooterImageView.clearAnimation();
			mFooterImageView.startAnimation(mFlipAnimation);
			mFooterState = RELEASE_TO_REFRESH;
			
//			if (mOnFooterRefreshListener != null) {
//				mOnFooterRefreshListener.onFooterPrepareRefresh(this, mFooterView);
//			}
			
		} else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
			Log.d(TAG, TAG+" footerPrepareToRefresh reaease_to_refresh");
			mFooterImageView.clearAnimation();
			mFooterImageView.startAnimation(mFlipAnimation);
			mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
			mFooterState = PULL_TO_REFRESH;
			mFooterView.setVisibility(View.VISIBLE);
		}
		
		//Log.d(TAG,TAG+"end footerPrepareToRefresh");
	}

	/**
	 * 
	 * 
	 * @param deltaY
	 */
	private int changingHeaderViewTopMargin(int deltaY) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.3f;
		
		if( deltaY>0 && mPullState == PULL_UP_STATE && Math.abs(params.topMargin) <= mHeaderViewHeight){
			return params.topMargin;
		}
		
		if(deltaY <0 && mPullState == PULL_DOWN_STATE && Math.abs(params.topMargin) >= mHeaderViewHeight){
			return params.topMargin;
		}
		
		params.topMargin = (int) newTopMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
		return params.topMargin;
	}

	/**
	 * header refreshing
	 * 
	 */
	private void headerRefreshing() {
		Log.d(TAG, "begin headerRefreshing");
		mHeaderState = REFRESHING;
		setHeaderTopMargin(0);
		
		mHeaderImageView.setVisibility(View.GONE);
		mHeaderImageView.clearAnimation();
		mHeaderImageView.setImageDrawable(null);
		mHeaderProgressBar.setVisibility(View.VISIBLE);
		mHeaderTextView.setText(R.string.pull_to_refresh_refreshing_label);
		
		if (mOnHeaderRefreshListener != null) {
			mOnHeaderRefreshListener.onHeaderRefresh(this, mHeaderView);
		}
	}

	/**
	 * footer refreshing
	 * 
	 */
	private void footerRefreshing() {
		Log.d(TAG, "begin footerRefreshing");
		mFooterState = REFRESHING;
		int top = mHeaderViewHeight + mFooterViewHeight;
		setHeaderTopMargin(-top);
		
		mFooterImageView.setVisibility(View.GONE);
		mFooterImageView.clearAnimation();
		mFooterImageView.setImageDrawable(null);
		mFooterProgressBar.setVisibility(View.VISIBLE);
		mFooterTextView.setText(R.string.pull_to_refresh_footer_refreshing_label);
		
		if (mOnFooterRefreshListener != null) {
			mOnFooterRefreshListener.onFooterRefresh(this, mFooterView);
		}
	}

	/**
	 * 璁剧疆header view 
	 * 
	 * @param topMargin
	 *           
	 */
	private void setHeaderTopMargin(int topMargin) {
		Log.d(TAG, TAG+"setHeaderTopMargin :"+topMargin);
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.topMargin = topMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
	}

	/**
	 * header view 下拉更新完成。
	 * 
	 */
	public void onHeaderRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderImageView.setVisibility(View.VISIBLE);
		mHeaderImageView.setImageResource(R.drawable.pulltorefresh_arrow);
		mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
		mHeaderProgressBar.setVisibility(View.GONE);
		// mHeaderUpdateTextView.setText("");
		mHeaderState = PULL_TO_REFRESH;
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void onHeaderRefreshComplete(CharSequence lastUpdated) {
		setLastUpdated(lastUpdated);
		onHeaderRefreshComplete();
	}

	/**
	 * footer view 上拉更新完成。
	 */
	public void onFooterRefreshComplete() {
		mFooterImageView.setVisibility(View.VISIBLE);
		setHeaderTopMargin(-mHeaderViewHeight);
		mFooterImageView.setImageResource(R.drawable.pulltorefresh_arrow_up);
		mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
		mFooterProgressBar.setVisibility(View.GONE);
		mFooterState = PULL_TO_REFRESH;
		mFooterView.setVisibility(View.GONE);
	}
	
	/**
	 * TODO 更新完成。
	 * yumo
	 * void
	 * 2014-12-1
	 */
	public void onRefreshComplete()
	{
		if(mPullState == PULL_UP_STATE)
		{
			onFooterRefreshComplete();
		}
		else
		{
			onHeaderRefreshComplete();
		}
	}

	/**
	 * Set a text to represent when the list was last updated.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void setLastUpdated(CharSequence lastUpdated) {
		if (lastUpdated != null) {
			mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			mHeaderUpdateTextView.setText(lastUpdated);
		} else {
			mHeaderUpdateTextView.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取刷新头部外间距。
	 * 
	 */
	private int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		Log.d(TAG,TAG+"getHeaderTopMargin:"+params.topMargin);
		return params.topMargin;
	}

//	/**
//	 * lock
//	 * 
//	 */
//	private void lock() {
//		mLock = true;
//	}
//
//	/**
//	 * unlock
//	 * 
//	 */
//	private void unlock() {
//		mLock = false;
//	}

	/**
	 * set headerRefreshListener
	 * 
	 * @param headerRefreshListener
	 */
	public void setOnHeaderRefreshListener(
			OnHeaderRefreshListener headerRefreshListener) {
		mOnHeaderRefreshListener = headerRefreshListener;
	}

	public void setOnFooterRefreshListener(
			OnFooterRefreshListener footerRefreshListener) {
		mOnFooterRefreshListener = footerRefreshListener;
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid footer
	 * view should be refreshed.
	 */
	public interface OnFooterRefreshListener {
		public void onFooterRefresh(PullToRefreshView view, View v);
		public void onFooterPrepareRefresh(PullToRefreshView view, View v);
		public void onFooterViewShow(PullToRefreshView view, View v);
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid header
	 * view should be refreshed.
	 */
	public interface OnHeaderRefreshListener {
		public void onHeaderRefresh(PullToRefreshView view, View v);
		public void onHeaderPrepareRefresh(PullToRefreshView view, View v);
		public void onHeaderViewShow(PullToRefreshView view, View v);
	}
}
