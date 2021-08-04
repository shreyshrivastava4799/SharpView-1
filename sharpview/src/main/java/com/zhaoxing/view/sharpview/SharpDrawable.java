package com.zhaoxing.view.sharpview;

import ohos.agp.colors.RgbColor;
import ohos.agp.colors.RgbPalette;
import ohos.agp.components.Component;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.Path;
import ohos.agp.utils.Color;
import ohos.agp.utils.Point;
import ohos.agp.utils.RectFloat;

/*
 * SharpDrawable:
 * */
class SharpDrawable extends ShapeElement {

    private float mSharpSize;
    private RgbColor[] mBgColor;
    private RgbColor mBackgroundColor;
    private float mCornerRadius;
    private float[] mCornerRadii;
    private SharpView.ArrowDirection mArrowDirection;
    private float mBorder;
    private RgbColor mBorderColor;
    private float mRelativePosition;
    private Paint mPaint;
    private RectFloat mRect;
    private Path mSharpPath;
    private Point[] mPointFs;
    private Component mComponent;

    SharpDrawable() {
        super();
        init();
    }

    SharpDrawable(Orientation orientation, Component component) {
        mComponent = component;
        super.setGradientOrientation(orientation);
        init();
    }

    SharpDrawable(Orientation orientation, Component component, RgbColor[] colors) {
        mComponent = component;
        super.setRgbColors(colors);
        super.setGradientOrientation(orientation);
        init();
    }

    public void setBgColor(RgbColor[] bgColor) {
        mBgColor = bgColor;
        super.setRgbColors(bgColor);
    }

    public void setBgColor(RgbColor bgColor) {
        mBackgroundColor = bgColor;
        super.setRgbColor(mBackgroundColor);
    }

    public void setSharpViewCornerRadius(float cornerRadius) {
        mCornerRadius = cornerRadius;
        super.setCornerRadius(cornerRadius);
    }

    public void setCornerRadii(float[] cornerRadii) {
        mCornerRadii = cornerRadii;
        super.setCornerRadiiArray(cornerRadii);
    }

    public void setArrowDirection(SharpView.ArrowDirection arrowDirection) {
        mArrowDirection = arrowDirection;
    }

    public void setRelativePosition(float relativePosition) {
        mRelativePosition = Math.min(Math.max(relativePosition, 0), 1);
    }

    public void setBorder(float border) {
        mBorder = border;
        super.setStroke((int) mBorder, mBorderColor);
    }

    public void setBorderColor(RgbColor borderColor) {
        mBorderColor = borderColor;
        super.setStroke((int) mBorder, mBorderColor);
    }

    public void setSharpSize(float sharpSize) {
        mSharpSize = sharpSize;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    private void init() {
        mSharpSize = 0;
        mBgColor = new RgbColor[]{RgbPalette.CYAN};
        mBackgroundColor = RgbPalette.CYAN;
        mCornerRadius = 0;
        mArrowDirection = SharpView.ArrowDirection.LEFT;
        mBorder = 0;
        mBorderColor = RgbPalette.CYAN;
        mRelativePosition = 0;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRect = new RectFloat();
        mPointFs = new Point[3];
        mSharpPath = new Path();
        mPointFs[0] = new Point();
        mPointFs[1] = new Point();
        mPointFs[2] = new Point();
    }

    @Override
    public void drawToCanvas(Canvas canvas) {
        if (mComponent != null && mSharpSize != 0) {
            int left = 0;
            int top = 0;
            int right = mComponent.getWidth();
            int bottom = mComponent.getHeight();
            float length;
            int bleft = 0;
            int btop = 0;
            int bright = mComponent.getWidth();
            int bbottom = mComponent.getHeight();
            switch (mArrowDirection) {
                case LEFT:
                    left += mSharpSize;
                    length = Math.max(mRelativePosition * mComponent.getHeight() + btop, mSharpSize + mCornerRadius);
                    length = Math.min(length, mComponent.getHeight() - mSharpSize - mCornerRadius);
                    mPointFs[0].modify(bleft, length + btop);
                    mPointFs[1].modify(left, mPointFs[0].getPointY() + mSharpSize);
                    mPointFs[2].modify(left, mPointFs[0].getPointY() - mSharpSize);
                    mRect.modify(left, top, right, bottom);
                    break;
                case TOP:
                    top += mSharpSize;
                    length = Math.max(mRelativePosition * mComponent.getWidth() + btop, mSharpSize + mCornerRadius);
                    length = Math.min(length, mComponent.getWidth() - mSharpSize - mCornerRadius);
                    mPointFs[0].modify(bleft + length, btop);
                    mPointFs[1].modify(mPointFs[0].getPointX() - mSharpSize, top);
                    mPointFs[2].modify(mPointFs[0].getPointX() + mSharpSize, top);
                    mRect.modify(left, top, right, bottom);
                    break;
                case RIGHT:
                    right -= mSharpSize;
                    length = Math.max(mRelativePosition * mComponent.getHeight() + btop, mSharpSize + mCornerRadius);
                    length = Math.min(length, mComponent.getHeight() - mSharpSize - mCornerRadius);
                    mPointFs[0].modify(bright, length + btop);
                    mPointFs[1].modify(right, mPointFs[0].getPointY() - mSharpSize);
                    mPointFs[2].modify(right, mPointFs[0].getPointY() + mSharpSize);
                    mRect.modify(left, top, right, bottom);
                    break;
                case BOTTOM:
                    bottom -= mSharpSize;
                    length = Math.max(mRelativePosition * mComponent.getWidth() + btop, mSharpSize + mCornerRadius);
                    length = Math.min(length, mComponent.getWidth() - mSharpSize - mCornerRadius);
                    mPointFs[0].modify(bleft + length, bbottom);
                    mPointFs[1].modify(mPointFs[0].getPointX() - mSharpSize, bottom);
                    mPointFs[2].modify(mPointFs[0].getPointX() + mSharpSize, bottom);
                    mRect.modify(left, top, right, bottom);
                    break;
                default:
                    break;
            }
            mSharpPath.reset();
            mSharpPath.addRoundRect(mRect, mCornerRadius, mCornerRadius, Path.Direction.CLOCK_WISE);
            mSharpPath.moveTo(mPointFs[0].getPointX(), mPointFs[0].getPointY());
            mSharpPath.lineTo(mPointFs[1].getPointX(), mPointFs[1].getPointY());
            mSharpPath.lineTo(mPointFs[2].getPointX(), mPointFs[2].getPointY());
            mSharpPath.lineTo(mPointFs[0].getPointX(), mPointFs[0].getPointY());
            mPaint.setColor(new Color(mBackgroundColor.asRgbaInt()));
            canvas.drawPath(mSharpPath, mPaint);
        }
    }

    public float getmSharpSize() {
        return mSharpSize;
    }

    public RgbColor[] getmBgColor() {
        return mBgColor;
    }

    public RgbColor getmBackgroundColor() {
        return mBackgroundColor;
    }

    public float getmCornerRadius() {
        return mCornerRadius;
    }

    public float[] getmCornerRadii() {
        return mCornerRadii;
    }

    public SharpView.ArrowDirection getmArrowDirection() {
        return mArrowDirection;
    }

    public float getmBorder() {
        return mBorder;
    }

    public RgbColor getmBorderColor() {
        return mBorderColor;
    }

    public float getmRelativePosition() {
        return mRelativePosition;
    }

    public Paint getmPaint() {
        return mPaint;
    }

    public RectFloat getmRect() {
        return mRect;
    }

    public Point[] getmPointFs() {
        return mPointFs;
    }

    public Component getmComponent() {
        return mComponent;
    }
}