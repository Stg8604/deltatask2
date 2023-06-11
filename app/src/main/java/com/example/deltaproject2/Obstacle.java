package com.example.deltaproject2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Obstacle {
    private RectF rect;
    private Bitmap obstacleBitmap;
    private Paint paint;
    private float verticaloffset;
    private boolean movingUp;
    private float hitboxOffsetX;
    private float hitboxOffsetY;
    private int speed;
    private float left;
    private float right;

    public Obstacle(Bitmap obstacleBitmap, float left, float top, float right, float bottom,int speed) {
        this.obstacleBitmap = obstacleBitmap;
        this.rect = new RectF(left, top, right, bottom);
        this.paint = new Paint();
        this.left=left;
        this.right=right;
        this.verticaloffset=0;
        this.movingUp=true;
        this.hitboxOffsetX=10;
        this.hitboxOffsetY=10;
        this.speed=speed;
    }

    public Bitmap getObstacleBitmap() {
        return obstacleBitmap;
    }
    public void setLeft(int left){
        this.left=left;
    }
    public void setRight(int right){
        this.right=right;
    }

    public void move(float offsetX) {
        rect.offset(offsetX, 0);
    }
    public void moveup(){
            // Update the vertical position based on the offset
            this.rect.top += verticaloffset;
            this.rect.bottom+=verticaloffset;
            // Adjust the vertical offset based on the movement direction
            if (movingUp) {
                verticaloffset -= speed;
            } else {
                verticaloffset += speed;
            }
            // Check if the obstacle has reached the upper or lower limit
            if (verticaloffset <= -50) {
                // Reached upper limit, change direction to move down
                movingUp = false;
            } else if (verticaloffset >= 50) {
                // Reached lower limit, change direction to move up
                movingUp = true;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(obstacleBitmap, null, rect, paint);
    }

    public boolean collidesWith(RectF otherRect) {
        return RectF.intersects(rect, otherRect);
    }

    public float left() {
        return rect.left;
    }

    public float right() {
        return rect.right;
    }

    public float top() {
        return rect.top;
    }

    public float bottom() {
        return rect.bottom;
    }
    private RectF getHitboxRect() {
        return new RectF(rect.left + hitboxOffsetX, rect.top + hitboxOffsetY,
                rect.right - hitboxOffsetX, rect.bottom - hitboxOffsetY);
    }

}
