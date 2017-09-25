package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Practice11PieChartView extends View {

    private static final String TAG = "Practice11PieChartView";
    //circle
    private RectF mInnerOvalShape;//内圆弧外轮廓矩形区域。
    private RectF mOutterOvalShape;//外圆的外轮廓矩形区域。

    private Rect mRect = new Rect();

    //circle attr
    private int mCircleLeft = 100;//左上角的横坐标
    private int mCircleTop = 50;//左上角的纵坐标
    private int mCircleRadius = 200;//圆的半径
    private int[] mCircleDegreeRange = {0, 2, 7, 7, 30, 129, 120, 58};//圆的各个角度的范围

    //paint
    private Paint mPaintPurple = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintGray = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintGreen = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintBlue = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintRed = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintYellow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //line
    private Paint mPaintPath = new Paint(Paint.ANTI_ALIAS_FLAG);
    //text
    private Paint mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);

    //paints集合
    private List<Paint> mPaints = new ArrayList<>();
    //paths集合
    private List<Path> mPaths = new ArrayList<>();
    //points集合
    private List<Point> mPoints = new ArrayList<>();

    //文本内容
    private String[] mStrings = {"Froyo", "Gingerbread", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow"};



    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

        //初始化圆
        initCircle();
        //绘制圆
        drawPie(canvas);
        //初始化连线及文本
        initLineAndText();
        //绘制连线及文本
        drawLineAndText(canvas);
    }

    /**
     * 绘制连线及文本
     * @param canvas
     */
    private void drawLineAndText(Canvas canvas) {
        for (int i = 0; i < 7; i++) {
            canvas.drawPath(mPaths.get(i), mPaintPath);
            if (i == 4 || i == 5) {
                mPaintText.getTextBounds(mStrings[i], 0, mStrings[i].length(), mRect);
                canvas.drawText(mStrings[i], mPoints.get(i).x - mRect.width() - 5, mPoints.get(i).y, mPaintText);
                continue;
            }
            canvas.drawText(mStrings[i], mPoints.get(i).x, mPoints.get(i).y, mPaintText);
        }
    }

    /**
     * 绘制文本参数
     */
    private void initLineAndText() {
        mPaintPath.setColor(Color.argb(120, 232, 232, 232));
        mPaintPath.setStyle(Paint.Style.STROKE);
        mPaintPath.setStrokeWidth(1.5f);

        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(15);


        int[] degree = new int[7];//获取7块区域的角度
        int start = -1;
        for (int i = 0; i < 7; i++) {
            start += mCircleDegreeRange[i] + 1;
            degree[i] = mCircleDegreeRange[i + 1] / 2 + start;
        }

        //7条划线的路径（path）及point的坐标
        int left = 0;
        for (int i = 0; i < 7; i++) {
            Path path = new Path();
            Point p = getPoint(degree[i]);
            path.moveTo(p.x, p.y);

            if (i == 0) {
                path.lineTo(p.x + 70, p.y);
                left = p.x += 75;
            }

            if (i == 1 || i == 2) {
                path.lineTo(p.x + 25, p.y);
                path.rLineTo(20, 20);
                path.rLineTo(25, 0);
                p.x = left;
                p.y += 20;
            }

            if (i == 3) {
                path.lineTo(p.x + 25, p.y + 25);
                path.lineTo(left - 5, p.y + 25);
                p.x = left;
                p.y += 25;
            }

            if (i == 4) {
                path.lineTo(p.x - 25, p.y + 25);
                path.rLineTo(-70, 0);
                p.x = p.x - 95;
                p.y += 25;
            }

            if (i == 5) {
                path.moveTo(p.x - 10, p.y -10);
                path.lineTo(p.x - 20, p.y - 20);
                path.rLineTo(-80, 0);
                p.x = p.x - 100;
                p.y -= 20;
            }

            if (i == 6) {
                path.lineTo(p.x + 25, p.y - 25);
                path.lineTo(left - 5, p.y - 25);
                p.x = left;
                p.y -= 25;
            }
            mPaths.add(path);
            mPoints.add(p);
        }
    }

    /**
     * 初始化绘制圆参数
     */
    private void initCircle() {

        mInnerOvalShape = new RectF(100, 50, 500, 450);//内圆，左上角坐标（100,50），右下角（500,450），圆半径为200
        mOutterOvalShape = new RectF(90, 40, 490, 440);//外圆，左上角坐标（90,40），右下角（490,440），圆半径为200

        //设置paint的颜色
        mPaints.add(mPaint);
        mPaintPurple.setColor(Color.argb(190, 115, 15, 189));
        mPaints.add(mPaintPurple);
        mPaintGray.setColor(Color.argb(190, 144, 138, 143));
        mPaints.add(mPaintGray);
        mPaintGreen.setColor(Color.argb(190, 138, 216, 91));
        mPaints.add(mPaintGreen);
        mPaintBlue.setColor(Color.argb(190, 97, 176, 237));
        mPaints.add(mPaintBlue);
        mPaintRed.setColor(Color.argb(190, 216, 44, 37));
        mPaints.add(mPaintRed);
        mPaintYellow.setColor(Color.argb(190, 228, 227, 17));
        mPaints.add(mPaintYellow);

    }

    private void drawPie(Canvas canvas) {
        int start = -1;

        for (int i = 0; i < 7; i ++) {
            //绘制第6部分，需要使用外圆
            if (i == 5) {
                canvas.drawArc(mOutterOvalShape, start += mCircleDegreeRange[i] + 1, mCircleDegreeRange[i + 1], true, mPaints.get(i));
                continue;
            }
            canvas.drawArc(mInnerOvalShape, start += mCircleDegreeRange[i] + 1, mCircleDegreeRange[i + 1], true, mPaints.get(i));
            Log.i(TAG, "pie: start :" + start + " degreeRange:" + mCircleDegreeRange[i + 1]);
        }
    }


    /**
     * 获取圆上划线点的坐标
     * @param degree
     * @return
     */
    private Point getPoint(int degree) {
        Point point = new Point();
        point.x = (int) (mCircleLeft + mCircleRadius + mCircleRadius * Math.cos(degree * Math.PI / 180));
        point.y = (int) (mCircleTop + mCircleRadius + mCircleRadius * Math.sin(degree * Math.PI / 180));
        return point;
    }


}
