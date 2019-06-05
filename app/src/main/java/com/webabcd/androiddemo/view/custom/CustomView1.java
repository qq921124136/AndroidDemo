/**
 * 继承 View 实现一个自定义的 View（用于演示 measure, layout, draw）
 *
 *
 *
 * 一个 view 要显示出来，需要经过如下 3 个过程
 * 1、measure - 测量过程，用于确定 view 的宽高
 * 2、layout - 布局过程，用于确定 view 的位置
 * 3、draw - 绘制过程，用于绘制出 view
 *
 *
 *
 * 一、对于 measure 过程的说明：
 * 通过调用 public final void measure(int widthMeasureSpec, int heightMeasureSpec) 来测量 view（外部调用）
 * 通过重写 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 来实现 view 的测量（自己在 measure() 中调用）
 * widthMeasureSpec, heightMeasureSpec 均为 32 位的 int 值，其用于父容器告知子 view 该如何被测量，它由以下两部分组成
 * 1、SpecMode - 高 2 位代表测量模式，可以通过 MeasureSpec.getMode() 获取
 *        MeasureSpec.UNSPECIFIED - 未定义模式。父容器对子 view 不做任何大小限制
 *        MeasureSpec.EXACTLY - 精确模式。父容器告诉子 view 你的大小必须是 SpecSize
 *        MeasureSpec.AT_MOST - 最大值模式。父容器告诉子 view 你的大小不能超过 SpecSize
 * 2、SpecSize - 低 30 位代表测量尺寸，可以通过 MeasureSpec.getSize() 获取
 *
 * 父容器是根据自己的 MeasureSpec 和子 view 的 LayoutParams 来创建子 view 的 MeasureSpec 的，其规则如下
 * 1、父的 MeasureSpec 是 MeasureSpec.EXACTLY
 *        子的 LayoutParams 是确定值，则子的 MeasureSpec 是 MeasureSpec.EXACTLY
 *        子的 LayoutParams 是 match_parent，则子的 MeasureSpec 是 MeasureSpec.EXACTLY
 *        子的 LayoutParams 是 wrap_content，则子的 MeasureSpec 是 MeasureSpec.AT_MOST
 * 2、父的 MeasureSpec 是 MeasureSpec.AT_MOST
 *        子的 LayoutParams 是确定值，则子的 MeasureSpec 是 MeasureSpec.EXACTLY
 *        子的 LayoutParams 是 match_parent，则子的 MeasureSpec 是 MeasureSpec.EXACTLY
 *        子的 LayoutParams 是 wrap_content，则子的 MeasureSpec 是 MeasureSpec.AT_MOST
 * 3、父的 MeasureSpec 是 MeasureSpec.UNSPECIFIED
 *        子的 LayoutParams 是确定值，则子的 MeasureSpec 是 MeasureSpec.EXACTLY
 *        子的 LayoutParams 是 match_parent，则子的 MeasureSpec 是 MeasureSpec.UNSPECIFIED
 *        子的 LayoutParams 是 wrap_content，则子的 MeasureSpec 是 MeasureSpec.UNSPECIFIED
 *
 * 注：
 * 1、父容器调用子 view 的 measure() 方法来测量子 view 的宽高，然后子 view 调用自己的 onMeasure() 方法（注：measure() 之后可以通过 getMeasuredHeight() 和 getMeasuredWidth() 来获得测量结果）
 * 1、在重写 onMeasure() 时要记住，测量完成后一定要使用 setMeasuredDimension(int measuredWidth, int measuredHeight) 方法来保存测量的结果
 *
 *
 *
 * 二、对于 layout 过程的说明：
 * 通过调用 public void layout(int l, int t, int r, int b) 来布局 view（外部调用）
 * 通过重写 protected void onLayout(boolean changed, int l, int t, int r, int b) 来实现 view 的布局（自己在 layout() 中调用）
 * changed - 当前 view 的位置和上次比较，是否发生改变
 * l, t, r, b - left, top, right, bottom 相对于父容器的位置
 *
 * 注：
 * 1、父 layout 在自己的 onLayout() 方法中对子 view 布局，然后调用子 view 的 layout() 方法
 * 2、子 view 在自己的 layout() 方法中使用 setFrame() 方法将位置应用到视图上，然后调用自己的 onLayout() 方法
 *
 *
 *
 * 三、对于 draw 过程的说明：
 * 通过调用 public void draw(Canvas canvas) 来绘制 view（外部调用）
 * 通过重写 protected void onDraw(Canvas canvas) 来实现 view 的绘制（自己在 draw() 中调用）
 * 父调用子的 draw()，然后子在自己的 draw() 里调用自己的 onDraw(), 然后子在自己的 onDraw() 里调用子子的 draw() ......
 */

package com.webabcd.androiddemo.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.webabcd.androiddemo.R;

public class CustomView1 extends View {
    private String titleText = "";
    private int titleColor = Color.BLACK;
    private int titleBackgroundColor = Color.WHITE;
    private int titleSize = 16;

    private Paint mPaint;
    private Rect mBound;

    public CustomView1(Context context) {
        this(context, null);
    }

    public CustomView1(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 属性定义在 res/values/attrs.xml
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomView1, 0, 0);
        if (a != null) {
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.CustomView1_titleColor:
                        titleColor = a.getColor(attr, Color.BLACK);
                        break;
                    case R.styleable.CustomView1_titleSize:
                        titleSize = a.getDimensionPixelSize(attr, titleSize);
                        break;
                    case R.styleable.CustomView1_titleText:
                        titleText = a.getString(attr);
                        break;
                    case R.styleable.CustomView1_titleBackgroundColor:
                        titleBackgroundColor = a.getColor(attr, Color.WHITE);
                        break;
                }
            }
            a.recycle();

            init();
        }
    }

    private void init() {
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 指定画笔绘制文字的尺寸
        mPaint.setTextSize(titleSize);

        // 获取被绘制的文字的宽和高
        mBound = new Rect();
        mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        // 测量出宽
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        // 测量出高
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = width;
        }

        // 保存测量出的宽和高，以便父容器通过 getMeasuredHeight() 和 getMeasuredWidth() 获取
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 没啥可布局的，默认就好（如果需要自定义布局，可以参见 view/custom/CustomView2.java）
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制背景
        mPaint.setColor(titleBackgroundColor);
        canvas.drawCircle(getWidth() / 2f, getWidth() / 2f, getWidth() / 2f, mPaint);

        // 绘制文字
        mPaint.setColor(titleColor);
        canvas.drawText(titleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }
}
