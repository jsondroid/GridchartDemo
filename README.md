# GridchartDemo
this is a gridchartView
![image](https://github.com/jsondroid/GridchartDemo/blob/master/screenshots/screenshot.png)
这是一个网格行的图表，自定义View实现的
核心代码如下：
'''java
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
    }//java'''
