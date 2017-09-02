package com.example.cheng.watermark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * 水印图片工具类
 *
 * @author cheng
 */
public class ImageUtil {

  /**
   * 设置水印图片在左上角
   */
  public static Bitmap createWaterMaskLeftTop(Context context, Bitmap src, Bitmap watermark,
      int paddingLeft, int paddingTop) {
    return createWaterMaskBitmap(src, watermark,
        dp2px(context, paddingLeft),
        dp2px(context, paddingTop));
  }

  private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark,
      int paddingLeft, int paddingTop) {
    if (src == null) {
      return null;
    }
    int width = src.getWidth();
    int height = src.getHeight();
    //创建一个bitmap
    Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
    //将该图片作为画布
    Canvas canvas = new Canvas(newBitmap);
    //在画布 0，0坐标上开始绘制原始图片
    canvas.drawBitmap(src, 0, 0, null);
    //在画布上绘制水印图片
    canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
    // 保存
    canvas.save(Canvas.ALL_SAVE_FLAG);
    // 存储
    canvas.restore();
    return newBitmap;
  }

  /**
   * 设置水印图片在右下角
   */
  public static Bitmap createWaterMaskRightBottom(
      Context context, Bitmap src, Bitmap watermark,
      int paddingRight, int paddingBottom) {
    return createWaterMaskBitmap(src, watermark,
        src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
        src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
  }

  /**
   * 设置水印图片到右上角
   */
  public static Bitmap createWaterMaskRightTop(
      Context context, Bitmap src, Bitmap watermark,
      int paddingRight, int paddingTop) {
    return createWaterMaskBitmap(src, watermark,
        src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
        dp2px(context, paddingTop));
  }

  /**
   * 设置水印图片到左下角
   */
  public static Bitmap createWaterMaskLeftBottom(
      Context context, Bitmap src, Bitmap watermark,
      int paddingLeft, int paddingBottom) {
    return createWaterMaskBitmap(src, watermark, dp2px(context, paddingLeft),
        src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
  }

  /**
   * 设置水印图片到中间
   */
  public static Bitmap createWaterMaskCenter(Bitmap src, Bitmap watermark) {
    return createWaterMaskBitmap(src, watermark,
        (src.getWidth() - watermark.getWidth()) / 2,
        (src.getHeight() - watermark.getHeight()) / 2);
  }

  /**
   * 给图片添加文字到左上角
   */
  public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text,
      int size, int color, int paddingLeft, int paddingTop) {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(color);
    paint.setTextSize(dp2px(context, size));
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);
    return drawTextToBitmap(context, bitmap, text, paint, bounds,
        dp2px(context, paddingLeft),
        dp2px(context, paddingTop) + bounds.height());
  }

  /**
   * 绘制文字到右下角
   */
  public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text,
      int size, int color, int paddingRight, int paddingBottom) {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(color);
    paint.setTextSize(dp2px(context, size));
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);
    return drawTextToBitmap(context, bitmap, text, paint, bounds,
        bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
        bitmap.getHeight() - dp2px(context, paddingBottom));
  }

  /**
   * 绘制文字到右上方
   */
  public static Bitmap drawTextToRightTop(Context context, Bitmap bitmap, String text,
      int size, int color, int paddingRight, int paddingTop) {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(color);
    paint.setTextSize(dp2px(context, size));
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);
    return drawTextToBitmap(context, bitmap, text, paint, bounds,
        bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
        dp2px(context, paddingTop) + bounds.height());
  }

  /**
   * 绘制文字到左下方
   */
  public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String text,
      int size, int color, int paddingLeft, int paddingBottom) {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(color);
    paint.setTextSize(dp2px(context, size));
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);
    return drawTextToBitmap(context, bitmap, text, paint, bounds,
        dp2px(context, paddingLeft),
        bitmap.getHeight() - dp2px(context, paddingBottom));
  }

  /**
   * 绘制文字到中间
   */
  public static Bitmap drawTextToCenter(Context context, Bitmap bitmap, String text,
      int size, int color) {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(color);
    paint.setTextSize(dp2px(context, size));
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);
    return drawTextToBitmap(context, bitmap, text, paint, bounds,
        (bitmap.getWidth() - bounds.width()) / 2,
        (bitmap.getHeight() + bounds.height()) / 2);
  }

  /**
   * 图片上绘制文字
   */
  private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
      Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
    Config bitmapConfig = bitmap.getConfig();

    paint.setDither(true); // 获取跟清晰的图像采样
    paint.setFilterBitmap(true);// 过滤一些
    if (bitmapConfig == null) {
      bitmapConfig = Config.ARGB_8888;
    }
    bitmap = bitmap.copy(bitmapConfig, true);
    Canvas canvas = new Canvas(bitmap);

    canvas.drawText(text, paddingLeft, paddingTop, paint);
    return bitmap;
  }

  /**
   * 缩放图片
   */
  public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
    if (w == 0 || h == 0 || src == null) {
      return src;
    } else {
      // 记录src的宽高
      int width = src.getWidth();
      int height = src.getHeight();
      // 创建一个matrix容器
      Matrix matrix = new Matrix();
      // 计算缩放比例
      float scaleWidth = (float) (w / width);
      float scaleHeight = (float) (h / height);
      // 开始缩放
      matrix.postScale(scaleWidth, scaleHeight);
      // 创建缩放后的图片
      return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
    }
  }

  /**
   * dip转pix
   */
  public static int dp2px(Context context, float dp) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }
}