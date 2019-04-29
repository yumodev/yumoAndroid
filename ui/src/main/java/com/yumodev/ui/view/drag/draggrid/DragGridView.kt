package com.yumodev.ui.view.drag.draggrid

import android.content.ClipData
import android.content.Context
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.yumo.common.android.YmDisplayUtil
import com.yumo.common.android.YmResUtil
import com.yumo.common.util.YmUtil
import com.yumodev.ui.R
import com.yumodev.ui.recyclerview.Define
import com.yumodev.ui.recyclerview.DragRecyclerView.DragItemBean
import com.yumodev.ui.recyclerview.DragRecyclerView.DragManager
import com.yumodev.ui.recyclerview.decoration.GridSpaceItemDecoration
import com.yumodev.ui.recyclerview.touchlist.ItemTouchHelperAdapter
import java.util.*

/**
 * Created by yumodev on 18/2/11.
 */

class DragGridView(context : Context) : FrameLayout(context) {
    private val LOG_TAG = Define.LOG_TAG+"GridDragView"

    private var mDataList: MutableList<DragItemBean>? = null
    private lateinit var  mListView: RecyclerView
    private lateinit var mAdapter: ItemAdapter
    private lateinit var mItemSpaceDecoration: GridSpaceItemDecoration
    private lateinit var mGestureDetectorCompat : GestureDetectorCompat
    private var mDragView: View? = null

    private var mLastMotionY: Float = 0.toFloat()
    private var mLastMotionX: Float = 0.toFloat()
    private var mInitialMotionX: Float = 0.toFloat()
    private var mInitialMotionY: Float = 0.toFloat()
    private var mSelectedStartX: Int = 0
    private var mSelectedStartY: Int = 0
    private var mDx: Float = 0.toFloat()
    private var mDy: Float = 0.toFloat()

    init {
        initView()
    }

    private fun initView(){
        DragManager.getInstance().init(context.applicationContext)
        initDragSettings()
        updateDragSize()
        initTestData()
        mListView = RecyclerView(context)
        mListView.layoutManager = GridLayoutManager(context, DragManager.getInstance().column)

        mItemSpaceDecoration = GridSpaceItemDecoration(DragManager.getInstance().itemHorSpace, DragManager.getInstance().column)
        mItemSpaceDecoration.setHorMargin(DragManager.getInstance().itemHorMargin)
        mListView.addItemDecoration(mItemSpaceDecoration)

        mAdapter = ItemAdapter(context)
        mListView.adapter = mAdapter

        addView(mListView)

        initLongListener()
    }


    /**
     * 初始化测试数据
     */
    private fun initTestData() {
        if (mDataList == null) {
            mDataList = ArrayList()
        }

        for (i in 0..9) {
            val item = DragItemBean()
            item.mId = YmUtil.createUUID()
            item.mPosition = i
            item.mTitle = i.toString() + ""
            if (i == 0) {
                item.mType = DragItemBean.TYPE_FOLDER
            } else {
                item.mType = DragItemBean.TYPE_ITEM
            }

            mDataList?.add(item)
        }
    }

    /**
     * 初始化设置的参数
     */
    private fun initDragSettings() {
        DragManager.getInstance().setColumn(4, 6)
        DragManager.getInstance().setFolderColumn(4, 6)
        DragManager.getInstance().itemHorSpace = resources.getDimensionPixelOffset(R.dimen.drag_item_hor_space)
        DragManager.getInstance().itemHorMargin = resources.getDimensionPixelOffset(R.dimen.drag_item_hor_margin)

        //folderIcon
        DragManager.getInstance().folderDrawIconPadding = YmResUtil.dipToPx(context, 5f).toInt()
        DragManager.getInstance().folderDrawIconMargin = YmResUtil.dipToPx(context, 5f).toInt()
        DragManager.getInstance().folderIconColumnCount = 2
        DragManager.getInstance().folderIconRowCount = 2
    }

    /**
     * 计算并更新DragItem 的宽高
     */
    private fun updateDragSize() {
        // 获取屏幕宽度
        val width = YmDisplayUtil.getScreenWidth(context)
        val horMargin = DragManager.getInstance().itemHorMargin
        val itemSpace = DragManager.getInstance().itemHorSpace
        val column = DragManager.getInstance().column
        val itemWidth = (width - horMargin * 2 - itemSpace * (column - 1)) / column
        val itemHeight = (itemWidth * 1.2).toInt()
        val iconWidth = itemWidth - context.resources.getDimensionPixelSize(R.dimen.drag_icon_margin) * 2
        DragManager.getInstance().itemWidth = itemWidth
        DragManager.getInstance().itemHeight = itemHeight
        DragManager.getInstance().itemIconWidth = iconWidth
        DragManager.getInstance().itemIconHeight = iconWidth
    }


    private fun initLongListener(){
        mGestureDetectorCompat = GestureDetectorCompat(context,
                object : GestureDetector.SimpleOnGestureListener(){
                    override fun onDown(e: MotionEvent?): Boolean {
                        Log.i(LOG_TAG, "onDown");
                        return false
                    }

                    override fun onLongPress(e: MotionEvent?) {
                        super.onLongPress(e)
                        Log.i(LOG_TAG, "onLongPress")
                        val view = mListView.findChildViewUnder(e!!.x, e!!.y)
                        if (view!= null){
                            mDragView = view
                            mSelectedStartX = mDragView!!.left
                            mSelectedStartY = mDragView!!.top
                            view?.startDrag(ClipData.newPlainText("des", "draggrid"), GridDragShadowBuilder(view), mDragView, 0)
                        }

                    }

                    override fun onSingleTapUp(e: MotionEvent?): Boolean {
                        Log.i(LOG_TAG, "onSingleTapUp")
                        return super.onSingleTapUp(e)
                    }
                }
        )

        mListView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener{
            override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
                Log.i(LOG_TAG, "onTouchEvent:"+e?.actionMasked)
                mGestureDetectorCompat.onTouchEvent(e)
                when(e!!.actionMasked){
                    MotionEvent.ACTION_DOWN ->{
                        mInitialMotionX = e!!.x
                        mInitialMotionY = e!!.y
                    }
                    MotionEvent.ACTION_MOVE ->{

                    }
                    MotionEvent.ACTION_UP ->{

                    }
                }
            }

            override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
                mGestureDetectorCompat.onTouchEvent(e)
                Log.i(LOG_TAG, "onInterceptTouchEvent:"+e?.actionMasked)
                return true
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        })


        mListView.setOnDragListener { v, event ->
            Log.i(LOG_TAG, event?.x.toString()+" "+event?.y.toString()+v!!::class.simpleName)
            var x = event.x
            var y = event.y
            var width = v.width
            var height = v.height
            var centerX = x - width / 2
            var centerY = y - height / 2
            when(event!!.action){
                DragEvent.ACTION_DRAG_STARTED ->{
                    Log.i(LOG_TAG, " DragEvent.ACTION_DRAG_STARTED ")
                    true
                }
                DragEvent.ACTION_DRAG_ENTERED ->{
                    Log.i(LOG_TAG, " DragEvent.ACTION_DRAG_ENTERED ")
                }
                DragEvent.ACTION_DRAG_LOCATION ->{
                    Log.i(LOG_TAG, " DragEvent.ACTION_DRAG_LOCATION ")
                    mDx = x -mInitialMotionX
                    mDy = y - mInitialMotionY
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    Log.i(LOG_TAG, " DragEvent.ACTION_DRAG_ENDED ")
                }
                DragEvent.ACTION_DRAG_EXITED->{
                    Log.i(LOG_TAG, " DragEvent.ACTION_DRAG_EXITED ")
                }
                DragEvent.ACTION_DROP->{
                    Log.i(LOG_TAG, " DragEvent.ACTION_DRAG_DROP ")
                }
            }
            true
        }
    }

    fun moveDragView(){

    }

    private inner class ItemAdapter(context: Context) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), ItemTouchHelperAdapter {
        private val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {
            val view = mInflater.inflate(R.layout.drag_item, viewGroup, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(itemViewHolder: ItemViewHolder, i: Int) {
            val itemBean = mDataList?.get(i)
            itemViewHolder.mTextView.text = itemBean?.mTitle


            itemViewHolder.itemView.setOnClickListener {
                Toast.makeText(context, itemBean?.mTitle + " isClick ", Toast.LENGTH_SHORT).show()
            }
        }

        override fun getItemCount(): Int {
            return mDataList?.size ?: 0
        }

        override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
            Collections.swap(mDataList, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onItemDismiss(position: Int) {

        }

        internal inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var mTextView: TextView
            var mIconView: ImageView

            init {
                mTextView = itemView.findViewById<View>(R.id.drag_title_id) as TextView
                mIconView = itemView.findViewById<View>(R.id.drag_item_icon) as ImageView
                val lp = itemView.layoutParams as GridLayoutManager.LayoutParams
                lp.height = DragManager.getInstance().itemHeight
                itemView.layoutParams = lp

                val iconLp = mIconView.layoutParams as FrameLayout.LayoutParams
                iconLp.width = DragManager.getInstance().itemIconWidth
                iconLp.height = DragManager.getInstance().itemIconHeight
                mIconView.layoutParams = iconLp
            }
        }
    }
}
