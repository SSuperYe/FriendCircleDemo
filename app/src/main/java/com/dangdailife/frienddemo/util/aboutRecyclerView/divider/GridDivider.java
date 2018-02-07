package com.dangdailife.frienddemo.util.aboutRecyclerView.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.dangdailife.frienddemo.util.DisplayUtil;

/**
 * @author Joy Wong
 * @description 网格状RecyclerView的分割线
 * @datetime 2016/08/13 14:13
 * @email wjb18814888154@gmail.com
 */
public class GridDivider extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[] { android.R.attr.listDivider };
    private Drawable mDivider;
    private Paint mPaint;
    /**
     * 分割线高度，默认为1px
     */
    private int dividerHeight=1;
    /**
     * 分割线宽度，默认为1px
     */
    private int dividerWidth=1;

    /**
     * 使用系统的listDivider
     * @param context
     */
    public GridDivider(Context context)
    {
        this(context,0);
    }

    /**
     * 指定drawable
     * @param context
     * @param drawableId
     */
    public GridDivider(Context context, int drawableId){
        if (drawableId==0){
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }else {
            mDivider= ContextCompat.getDrawable(context,drawableId);
        }
        dividerHeight=mDivider.getIntrinsicHeight();
        dividerWidth=mDivider.getIntrinsicWidth();
    }

    /**
     *
     * @param context
     * @param color 分割线颜色
     * @param dividerHeight 分割线高度
     * @param dividerWidth 分割线宽度
     */
    public GridDivider(Context context, int color, int dividerHeight, int dividerWidth){
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        this.dividerHeight=dividerHeight;
        this.dividerWidth=dividerWidth;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state)
    {

        drawHorizontal(c, parent);
        drawVertical(c, parent);

    }

    private int getSpanCount(RecyclerView parent)
    {
        // 列数
        int spanCount = -1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent)
    {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + dividerWidth;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + dividerHeight;
            if (mDivider!=null){
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
                continue;
            }
            if (mPaint!=null){
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent)
    {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + dividerWidth;
            if (mDivider!=null){
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
                continue;
            }

            if (mPaint!=null){
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }

    private boolean isLastColumn(View view){
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view
                .getLayoutParams();
        return view.getRight() + params.rightMargin >= DisplayUtil.getScreenWidthInPx();
    }

    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount,
                                 int childCount)
    {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else
            {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount)
    {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        int itemPosition=parent.getChildLayoutPosition(view);
        int spanCount = getSpanCount(parent);
        //如果该item占据了一整行，则不对其设置分割线
        int spanSize=((GridLayoutManager)parent.getLayoutManager()).getSpanSizeLookup().getSpanSize(itemPosition);
        if (spanSize==spanCount){
            return;
        }

        int childCount = parent.getAdapter().getItemCount();
        if (isLastRaw(parent, itemPosition, spanCount, childCount))// 如果是最后一行，则不需要绘制底部
        {
            outRect.set(0, 0, dividerWidth, 0);
        }
//        else if (isLastColumn(parent, itemPosition, spanCount, childCount))// 如果是最后一列，则不需要绘制右边
//        {
//            outRect.set(0, 0, 0, dividerHeight);
//        }
        else if (isLastColumn(view))// 如果是最后一列，则不需要绘制右边
        {
            outRect.set(0, 0, 0, dividerHeight);
        }
        else
        {
            outRect.set(0, 0, dividerWidth,
                    dividerHeight);
        }
    }

}
