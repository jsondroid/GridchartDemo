package demo.swicth.com.gridchartdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wenbaohe on 2017/7/10.
 */

public class SkinGradeView extends View {

    private Context context;

    public SkinGradeView(Context context) {
        super(context);
        initColor(context);
    }

    public SkinGradeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initColor(context);
    }

    public SkinGradeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initColor(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (MeasureSpec.getSize(widthMeasureSpec) < 660 || MeasureSpec.getSize(heightMeasureSpec) < 390) {
            int width = getMySize(660, widthMeasureSpec);
            int height = getMySize(390, heightMeasureSpec);
            if (width < height) {
                height = width;
            } else {
                width = height;
            }
            setMeasuredDimension(width, height);
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }


    /**
     * 色块颜色值
     */
    private String colorArray[][];

    private void initColor(Context context) {
        this.context = context;

        colorArray = new String[][]{
                {"C8AC99", "CBAB99", "CAAB96", "CBAC95", "C9AD95", "C8A995", "C9A996", "C8A994", "C7A992", "C5A991", "C7A896"},
                {"C5A691", "C8A793", "C7A792", "C6A891", "C4A890", "C7A694", "CAA596", "C2A38E", "C3A28F", "C2A38F", "C2A48D"},
                {"C0A48D", "C3A38F", "C5A192", "C6A195", "C6A096", "C6A098", "BE9D86", "BE9C87", "BE9D88", "BC9D87", "BD9F87"},
                {"BF9F8C", "C29C8C", "C39A8D", "C39A8E", "C39990", "BB9881", "BD9983", "BA9982", "B99980", "B89A74", "BB9983"},
                {"BD9584", "C29787", "C19487", "C1958C", "B69279", "B5927B", "B49277", "B39276", "B19175", "B7927B", "B68D78"},
                {"B98D7E", "BC8D80", "BE9186", "B1886C", "AF8870", "B0896E", "AD8B6F", "AA8B6D", "B3886E", "B38672", "B58576"},
                {"B58678", "A87F64", "A98167", "A68266", "A58163", "A38264", "AB8167", "AC7E69", "AE7F6E", "AD7E71", "A0765A"},
                {"A0795F", "9B775D", "99795D", "A2785D", "A5755F", "A57461", "946C51", "986F54", "906F54", "8F7054", "976C52"},
                {"9A6B54", "996A58", "876249", "89634C", "846349", "866048", "8C624E", "895F4D", "775741", "795945", "775642"},
                {"785444", "775445", "654D3E", "644B3C", "634B3F", "664C41", "63483E", "544338", "514239", "53433A", "544239"},
        };

    }

    /**
     * 获取颜色
     */
    private int getColor(int color) {
        return ContextCompat.getColor(context, color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas, R.drawable.coordinate_img);
        draGrid(canvas);
    }

    /**
     * 绘制方格
     */
    private float magin_left = 50;//表格的边距
    private float magin_top = 50;
    private float magin_rigth = 50;
    private float magin_bottom = 50;

    private float grid_Width = 0;//方块区域的宽和高
    private float grid_Higth = 0;

    private float block_Width;//每一个方块的宽和高
    private float block_Higth;

    private float block_hspace = 2;//横向的间隔大小
    private float block_vspace = 1;//纵向的间隔大小
    private int num = 1;//11列
    private int num2 = -1;//10行

    private RectF rectF;

    private void draGrid(Canvas canvas) {

        grid_Width = getWidth() - (magin_rigth + magin_left);
        grid_Higth = getHeight() - (magin_top + magin_bottom);
        block_Width = grid_Width / 11;
        block_Higth = grid_Higth / 10;

        for (int i = 0; i < 10; i++)//行
        {
            for (int j = 0; j < 11; j++)//列
            {
                /**画格子部分*/
                Paint barbgpaint = new Paint();
                barbgpaint.setColor(Color.parseColor("#" + colorArray[i][j]));

                Log.e("第" + i + "行", "" + colorArray[i][j]);
                Log.e("第" + i + "行", "" + (i * j));


                barbgpaint.setAntiAlias(true);
                RectF gridblock = new RectF();
                gridblock.left = j * block_Width + magin_left + block_hspace;
                gridblock.right = (j + 1) * block_Width + magin_left - block_hspace;
                gridblock.top = i * block_Higth + magin_top + block_vspace;
                gridblock.bottom = (i + 1) * block_Higth + magin_top - block_vspace;
                canvas.drawRoundRect(gridblock, 0, 0, barbgpaint);//开始绘制
                int yy = (num - 1) / 11;
                int temp = yy * 11;
                int xx = (num - 1) - temp;
                if ((i == yy) && (j == xx)) {//加黑框(左下角原点坐标计算)
                    barbgpaint.setStyle(Paint.Style.STROKE);
                    barbgpaint.setStrokeWidth(3);
                    barbgpaint.setColor(Color.BLACK);
                    canvas.drawRoundRect(gridblock, 0, 0, barbgpaint);//开始绘制
                    rectF = gridblock;
                }

                /**左边坐标数字*/
                float tv_top = i * block_Higth + magin_top + block_vspace;
                float tv_bottom = (i + 1) * block_Higth + magin_top - block_vspace;
                draText(canvas, (10 - i), 0, coo_left, tv_top, tv_bottom);

                /**下边坐标数字*/
                float tv_left = j * block_Width + magin_left + block_hspace;
                float tv_right = (j + 1) * block_Width + magin_left - block_hspace;
                draText(canvas, (j + 1), tv_left, tv_right, getMeasuredHeight() - coo_bottom, getMeasuredHeight());
            }
        }

        if (rectF != null) {
            drawCursor(canvas, rectF);
        }
    }

    /**
     * 绘制坐标数值
     */
    private void draText(Canvas canvas, int count, float left, float top, float rigth, float bottom) {
        draText(canvas, ""+count, left, top, rigth, bottom);
    }
    private void draText(Canvas canvas, String text, float left, float top, float rigth, float bottom) {
        Paint righttv_piant = new Paint(Paint.ANTI_ALIAS_FLAG);
        righttv_piant.setStrokeWidth(0.1f);
        righttv_piant.setTextSize(DensityUtils.sp2px(context, 8));
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        righttv_piant.setTypeface(font);
        righttv_piant.setFakeBoldText(false);
        righttv_piant.setColor(Color.GRAY);
        righttv_piant.setTextAlign(Paint.Align.CENTER);

        RectF lefttext = new RectF();
        lefttext.left = left;
        lefttext.right = top;
        lefttext.top = rigth;
        lefttext.bottom = bottom;

        Paint.FontMetrics righttv_fontMetrics = righttv_piant.getFontMetrics();
        float righttv_top = righttv_fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float righttv_bottom = righttv_fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        int righttv_baseLineY = (int) (lefttext.centerY() - righttv_top / 2 - righttv_bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText(text, lefttext.centerX(), righttv_baseLineY, righttv_piant);
    }

    /**
     * 绘制背景坐标轴图片
     */
    private int coo_left = 23;//表格的边距
    private int coo_top = 1;
    private int coo_rigth = 1;
    private int coo_bottom = 23;

    private void drawBg(Canvas canvas, int img) {
        Bitmap imag = BitmapFactory.decodeResource(this.getResources(), img);
        // 定义矩阵对象
        Matrix matrix = new Matrix();
        // 缩放原图
//        matrix.postScale(0.5f, 0.5f);
        //imag.getWidth(), imag.getHeight()分别表示缩放后的位图宽高
        Bitmap dstbmp = Bitmap.createBitmap(imag, 0, 0, imag.getWidth(), imag.getHeight(), matrix, true);
        int img_width = dstbmp.getWidth();
        int img_hight = dstbmp.getHeight();
        Rect mSrcRect = new Rect(coo_left, coo_top, getMeasuredWidth() - coo_rigth, getMeasuredHeight() - coo_bottom);//图片区域
        canvas.drawBitmap(dstbmp, null, mSrcRect, null);
    }

    /**
     * 绘制提示文字
     */
    private void drawCursor(Canvas canvas, RectF gridblock) {
        Paint paint=new Paint();
        String str="提示文字";
        float tw=paint.measureText(str);
        int text_w=DensityUtils.dip2px(context,tw);

        Bitmap imag = BitmapFactory.decodeResource(this.getResources(), R.mipmap.grid_title_img);

        Bitmap dstbmp =createBitmapThumbnail(imag,text_w+10,(int)(block_Higth*2));

        Rect mSrcRect = new Rect(0, 0, dstbmp.getWidth(), dstbmp.getHeight());//图片区域
        int x=0;
        int y=0;
        x= (int) ((int) gridblock.left-(dstbmp.getWidth()/5)*4+gridblock.width())-10;
        if(num<=11){
            y=0;
        }else{
             y=(int) gridblock.top-(dstbmp.getHeight()/5)*4-10;
        }



        Rect mRect = new Rect(x, y,  x+dstbmp.getWidth(),y+ dstbmp.getHeight());//图片在那个区域显示

        canvas.drawBitmap(dstbmp,mSrcRect,mRect,null);

        drawTitleText(canvas,str,mRect);
    }
    private void drawTitleText(Canvas canvas,String text,Rect rect){
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.BLUE);
        rectPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(rect, rectPaint);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStrokeWidth(0.1f);
        textPaint.setTextSize(DensityUtils.sp2px(context, 11));
        textPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.LEFT);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) ((rect.centerY()-(rect.height()/6)) - top/2 - bottom/2);//基线中间点的y轴计算公式
        canvas.drawText(text,rect.left+10,baseLineY,textPaint);
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
        if (num2 < 0) {
            num2 = 0;
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num < 0) {
            num = 0;
        }
    }

    public Bitmap createBitmapThumbnail(Bitmap bitMap,int w,int h) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = w;
        int newHeight = h;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,matrix, true);
        return newBitMap;
    }
}
