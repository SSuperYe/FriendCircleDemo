package com.dangdailife.frienddemo.friendcircle;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dangdailife.frienddemo.R;
import com.dangdailife.frienddemo.util.DisplayUtil;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2018/02/02 17:37
 * @email superrhye@163.com
 */

public class CommentPopup {

    public Activity mActivity;
    public PopupWindow mPopupWindow;
    public View contentView;
    public EditText etComments;
    public ImageView imgExpress;
    public TextView tvSend;

    public CommentPopup(Activity activity) {
        this.mActivity = activity;
    }

    public void show(Callback callback, GetYListener getYListener) {
        contentView = View.inflate(mActivity, R.layout.layout_popup_edit_comment, null);
        etComments = (EditText) contentView.findViewById(R.id.et_comment);
        imgExpress = (ImageView) contentView.findViewById(R.id.img_express);
        tvSend = (TextView) contentView.findViewById(R.id.tv_send);

        tvSend.setOnClickListener(v -> {
            if (callback != null) {
                callback.onFinish(
                        TextUtils.isEmpty(etComments.getText().toString()) ? "" : etComments.getText().toString());
            }
            dismiss();
        });

        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mActivity);
            mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow.setHeight(DisplayUtil.dp2px(48));
        }

//        mPopupWindow.setOnDismissListener(() -> DisplayUtil.setBackgroundAlpha(mActivity, 1.0f));

        mPopupWindow.setBackgroundDrawable(mActivity.getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setContentView(contentView);

        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        if (!mPopupWindow.isShowing()) {
//            DisplayUtil.setBackgroundAlpha(mActivity, 0.5f);
            mPopupWindow.showAtLocation(contentView
                    , Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            etComments.postDelayed(() -> {
                etComments.setFocusable(true);
                etComments.setFocusableInTouchMode(true);
                etComments.requestFocus();
                InputMethodManager inputManager =
                        (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etComments, 0);

                if (getYListener != null) {
                    getYListener.getY(getBarHeight());
                }
            }, 100);
        }
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public interface Callback {
        void onFinish(String comment);
    }

    public interface GetYListener {
        void getY(float y);
    }

    public float getBarHeight() {
        float y = mPopupWindow.getHeight();
        return y;
    }

    /**
     * 回复hint
     */
    public void setSendContentHint(String hint) {
        etComments.setHint(hint);
    }
}
