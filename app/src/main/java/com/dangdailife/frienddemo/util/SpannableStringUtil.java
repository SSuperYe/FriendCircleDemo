package com.dangdailife.frienddemo.util;

import android.content.Context;
import android.graphics.MaskFilter;
import android.support.annotation.ColorInt;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.dangdailife.frienddemo.MyApplication;

/**
 * @autor Mr.Ye
 * @description String文字spannable类
 * @datetime 2017-08-24 17:39
 * @email superrhye@163.com
 */

public class SpannableStringUtil {
    //    WeakReference<SpannableStringBuilder> activityWeakReference;
    private SpannableStringBuilder spannableBuilder = null;
    private static SpannableStringUtil instance;

    private SpannableStringUtil(/*SpannableStringBuilder builder*/) {
//        activityWeakReference = new WeakReference<>(builder);
    }

    /**
     * 是否单例
     *
     * @param isSingle
     * @return
     */
    public static SpannableStringUtil getInstance(boolean... isSingle) {
        if (isSingle != null && isSingle.length > 0 && isSingle[0]) {
            if (instance == null) {
                instance = new SpannableStringUtil();
            }
        } else {
            instance = new SpannableStringUtil();
        }
        return instance;
    }

    public interface SpannableStringCallBack {
        void onClick();
    }

    /**
     * 设置字符串
     *
     * @param str
     * @return
     */
    public SpannableStringUtil setStrRes(String str) {
        if (spannableBuilder == null) {
            spannableBuilder = new SpannableStringBuilder();
        }
        spannableBuilder.clear();
        spannableBuilder.append(str);
        return instance;
    }

    /**
     * 设置字符串资源
     *
     * @param strRes
     */
    public void setStrRes(int strRes) {
        setStrRes(MyApplication.getInstance().getString(strRes));
    }

    /**
     * 文本颜色
     *
     * @param color 待修改成的颜色
     * @param start 开始修改的位置
     * @param end   结束修改的位置
     * @return
     */
    public SpannableStringUtil setForegroundColor(@ColorInt int color, int start, int end) {
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableBuilder.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 文本背景色
     *
     * @param background 待修改成的颜色
     * @param start      开始修改的位置
     * @param end        结束修改的位置
     * @return
     */
    public SpannableStringUtil setBackgroundColor(int background, int start, int end) {
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(background);
        spannableBuilder.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 文本字体
     *
     * @param size  待修改的大小
     * @param start 开始修改的位置
     * @param end   结束修改的位置
     * @return
     */
    public SpannableStringUtil setAbsoluteSize(int size, int start, int end) {
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size, true);
        spannableBuilder.setSpan(sizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 字体样式：粗体、斜体等
     *
     * @param typeface 待修改的样式
     * @param start    开始修改的位置
     * @param end      结束修改的位置
     * @return
     */
    public SpannableStringUtil setStyle(int typeface, int start, int end) {
        StyleSpan styleSpan = new StyleSpan(typeface);
        spannableBuilder.setSpan(styleSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 修饰效果，如模糊(BlurMaskFilter)浮雕
     *
     * @param maskFilter 待修改的样式
     * @param start      开始修改的位置
     * @param end        结束修改的位置
     * @return
     */
    public SpannableStringUtil setMaskFilter(MaskFilter maskFilter, int start, int end) {
        MaskFilterSpan maskFilterSpan = new MaskFilterSpan(maskFilter);
        spannableBuilder.setSpan(maskFilterSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 光栅效果
     *
     * @param rasterizer
     * @param start      开始修改的位置
     * @param end        结束修改的位置
     * @return
     */
//    public SpannableStringUtil setRasterizer(Rasterizer rasterizer, int start, int end) {
//        RasterizerSpan rasterizerSpan = new RasterizerSpan(rasterizer);
//        TextAppearanceSpan
//        spannableBuilder.setSpan(rasterizerSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        return instance;
//    }


    /**
     * 删除线
     *
     * @param start 开始修改的位置
     * @param end   结束修改的位置
     * @return
     */
    public SpannableStringUtil setStrikethrough(int start, int end) {
        spannableBuilder.setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 下划线
     *
     * @param start 开始修改的位置
     * @param end   结束修改的位置
     * @return
     */
    public SpannableStringUtil setUnderline(int start, int end) {
        spannableBuilder.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 下划线
     *
     * @param context   上下文
     * @param resources 资源文件
     * @param start     开始修改的位置
     * @param end       结束修改的位置
     * @return
     */
    public SpannableStringUtil setImage(Context context, int resources, int start, int end) {
        ImageSpan imageSpan = new ImageSpan(context, resources);
        spannableBuilder.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 基于x轴缩放
     *
     * @param scaleX 基于什么位置缩放
     * @param start  开始修改的位置
     * @param end    结束修改的位置
     * @return
     */
    public SpannableStringUtil setScaleX(float scaleX, int start, int end) {
        ScaleXSpan scaleXSpan = new ScaleXSpan(scaleX);
        spannableBuilder.setSpan(scaleXSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 下标（数学公式会用到）
     *
     * @param start 开始修改的位置
     * @param end   结束修改的位置
     * @return
     */
    public SpannableStringUtil setSubscript(int start, int end) {
        spannableBuilder.setSpan(new SubscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 上标（数学公式会用到）
     *
     * @param start 开始修改的位置
     * @param end   结束修改的位置
     * @return
     */
    public SpannableStringUtil setSuperscript(int start, int end) {
        spannableBuilder.setSpan(new SuperscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return instance;
    }

    /**
     * 点击事件
     *
     * @param textView 文本框
     * @param start    开始修改的位置
     * @param end      结束修改的位置
     * @return
     */
    public SpannableStringUtil setClickable(TextView textView, boolean underLine, int start, int end, final SpannableStringCallBack spannableStringCallBack) {
        return this.setClickable(textView, underLine, false, start, end, spannableStringCallBack);
    }

    /**
     * 点击事件
     *
     * @param textView 文本框
     * @param start    开始修改的位置
     * @param end      结束修改的位置
     * @return
     */
    public SpannableStringUtil setClickable(TextView textView, boolean underLine, boolean isDefaultColor, int start, int end, final SpannableStringCallBack spannableStringCallBack) {
        spannableBuilder.setSpan(new Clickable(underLine, isDefaultColor) {
            @Override
            public void onClick(View widget) {
                if (spannableStringCallBack != null) {
                    spannableStringCallBack.onClick();
                }
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(textView.getResources().getColor(android.R.color.transparent));
        return instance;
    }

    private abstract class Clickable extends ClickableSpan {

        private boolean mUnderLine;
        private boolean mIsDefaultColor;

        private Clickable(boolean underLine, boolean isDefaultColor) {
            this.mUnderLine = underLine;
            this.mIsDefaultColor = isDefaultColor;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            if (mIsDefaultColor) {
                ds.setColor(ds.linkColor);
            } else {

            }
            ds.setUnderlineText(mUnderLine);    //去除超链接的下划线
        }
    }

    public void closeSpannable() {
        spannableBuilder = null;
    }

    /**
     * 获取被TextView使用的SpannableStringBuilder样式
     *
     * @return
     */
    public SpannableStringBuilder getSpannableBuilder() {
        return spannableBuilder;
    }
}
