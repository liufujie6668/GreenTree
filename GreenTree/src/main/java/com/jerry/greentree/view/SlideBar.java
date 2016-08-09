package com.jerry.greentree.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.jerry.greentree.R;

/**
 * Created by Administrator on 16-1-6.
 *
 * 1, 绘制侧边字母对应控件
 *
 * 2，按下，选择字母，字母要变颜色，控件背景改变
 *    左边城市列表选择对应的关键字置顶
 *
 * 3，在按下，或者移动的时候，显示中间的关键字提示
 */
public class SlideBar extends View
{
    // 要现实的字母
    private static String[] chars =
            { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                    "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    // 定义画笔
    private Paint paint = new Paint();

    public SlideBar(Context context)
    {
        super(context);
    }

    public SlideBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // 获取控件高度
        int height = getHeight();

        // 获取控件的宽度
        int widht = getWidth();

        // 定义每一个字母的高度
        int codeHeight = height / chars.length;

        float xpos,ypos;

        // 循环绘制字母
        for (int i = 0; i < chars.length; i++)
        {
            paint.setAntiAlias(true);

            paint.setTextSize(30);

            paint.setColor(Color.rgb(33, 65, 98));

            // 加粗字体
            paint.setTypeface(Typeface.DEFAULT_BOLD);

            if (currentPosition == i)
            {
                // paint.setColor(<Context>.getResources().getCololr(android.R.color.red))
                // paint.setColor(Color.GRAY)
                // paint.setColor(Color.rgb(50,50,50))
                // paint.setColor(Color.parseColor("#ff3399ff"));

                paint.setColor(Color.parseColor("#3399ff"));
            }

            // 字母对应的横坐标位置
            xpos = (widht - paint.measureText(chars[i])) / 2;

            // 字母对应纵坐标位置
            ypos = codeHeight * (i+1);

            // 绘制字母
            canvas.drawText(chars[i],xpos,ypos,paint);

            paint.reset();
        }
    }

    // 定义中间显示的关键字提示控件
    private TextView mTvTip;
    public void setTipTextView(TextView tv)
    {
        this.mTvTip = tv;
    }

    // 设置由侧边字母选择控件，返回选择字母的位置给HotelCitySelectActivity
    private ChangeCityCategoryListener listener;
    public void setChangeCityCategoryListener(ChangeCityCategoryListener listener)
    {
        this.listener = listener;
    }
    public interface ChangeCityCategoryListener
    {
        public void changed(String hitString);
    }

    // 保存选择的位置
    int currentPosition = - 1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        // 按下或者移动时，相对于控件的偏移
        float yoffset = event.getY();

        // 获取当前点击位置
        currentPosition = (int)(yoffset / getHeight() * chars.length);

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                // 改变控件背景颜色
                setBackgroundResource(R.drawable.shape_slide_bar);

                // 改变字体颜色，需要去重绘 onDraw

                // 找到现在点击是那个字母
                String hitChar = chars[currentPosition];

                // 显示中间关键字提示
                mTvTip.setVisibility(View.VISIBLE);
                mTvTip.setText(hitChar);

                // 改变左边列表中的城市选择
                listener.changed(hitChar);

                break;

            case MotionEvent.ACTION_UP:

                // 恢复控件原来背景颜色
                setBackgroundResource(android.R.color.transparent);

                // 取消中间中间关键字提示
                mTvTip.setVisibility(View.GONE);

                break;
        }

        // 重绘
        invalidate();

        return true;
    }
}
