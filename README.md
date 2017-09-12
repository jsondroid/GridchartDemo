# GridchartDemo
this is a gridchartView
![image](https://github.com/jsondroid/GridchartDemo/blob/master/screenshots/screenshot.png)
这是一个网格行的图表，自定义View实现的
核心代码如下：
```
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
```
