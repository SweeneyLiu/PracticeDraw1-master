package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class Practice10HistogramView extends View {


    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath = new Path();
    private Rect mRect = new Rect();

    private String[] mStrings = {"Froyo", "GB", "ICS", "JB", "KitKat", "L", "M"};
    private static final String TITLE = "直方图";
    private static final int COORDINATES_LEFT = 50;//坐标离左边的位置
    private static final int HISTOGRAM_LEFT = 60;//直方图离左边的位置
    private static final int HISTOGRAM_BOTTOM = 400;//直方图底部位置
    private static final int BOTTOM_TEXT = 420;//坐标下面文字位置
    private static final int ABSCISSA_LENGTH = 650;//横坐标的长度
    private static final int BOTTOM_TITLE = 480;//坐标下面文字位置

    private static final int SPACE = 10;
    private static final int WIDTH = 60;
    private static final int RANDOM_MIN_HEIGHT = 40;

    private int mLeft = HISTOGRAM_LEFT;
    private int mRight = HISTOGRAM_LEFT + WIDTH;

    private int mTop;


    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        //1.绘制横竖坐标
        drawCoordinates(canvas);

        //2.绘制直方图形和文字
        drawHistogramAndText(canvas);

        //3.绘制文字
        drawTextContent(canvas);

    }

    /**
     * 绘制坐标
     *
     * @param canvas
     */
    private void drawCoordinates(Canvas canvas) {

        mPath.moveTo(COORDINATES_LEFT, COORDINATES_LEFT);
        mPath.lineTo(COORDINATES_LEFT, HISTOGRAM_BOTTOM);
        mPath.rLineTo(ABSCISSA_LENGTH, 0);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.WHITE);

        canvas.drawPath(mPath, mPaint);
    }


    /**
     * 绘制直方图
     *
     * @param canvas
     */
    private void drawHistogramAndText(Canvas canvas) {

        mPaintRect.setColor(Color.GREEN);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(15);
        for (int i = 0; i < mStrings.length; i++) {
            int randomTop = new Random().nextInt(HISTOGRAM_BOTTOM - RANDOM_MIN_HEIGHT) + RANDOM_MIN_HEIGHT;
            mPaintRect.getTextBounds(mStrings[i], 0, mStrings[i].length(), mRect);//获取文字的宽度
            int textWidth = mRect.width() / 2;
            canvas.drawRect(mLeft, randomTop, mRight, HISTOGRAM_BOTTOM, mPaintRect);
            canvas.drawText(mStrings[i], (mLeft + mRight) / 2 - textWidth, BOTTOM_TEXT, mPaintText);
            mLeft = mRight + SPACE;
            mRight = mLeft + WIDTH;
        }
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawTextContent(Canvas canvas) {
        mPaintText.setTextSize(30);
        canvas.drawText(TITLE, ABSCISSA_LENGTH / 2, BOTTOM_TITLE, mPaintText);
    }


}
