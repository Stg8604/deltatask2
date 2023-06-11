package com.example.deltaproject2;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomView extends View {
    private static final int CIRCLE_RADIUS = 100;
    private int chaseradius=50;
    private int chaserjumpv=-15;
    private int jmpoffset=20;
    private int up=2;
    private int index=0;
    private boolean isColliding;
    private Bitmap bit1,bit2,bit3,bit4,obstacleBitmap,bit5;
    private static final int OBSTACLE_WIDTH = 200;
    private static final int OBSTACLE_HEIGHT1 = 100;
    private float chaserX;
    private float chaserY;
    private float chaserVelocityY;
    private RectF chaserRect;
    private int counteract;
    private MediaPlayer media4;
    private static final int OBSTACLE_HEIGHT2=200;
    private float OBSTACLE_SPEED = 15;
    private static final int MAX_OBSTACLES = 1;
    private Boolean game=true;
    private Boolean chaserstart=false;
    private static final int JUMP_VELOCITY = -50;
    private int counter=0;
    private Paint circlePaint;
    private Paint obstaclePaint;
    private int jumpForce=-150;
    private int circleX;
    private int circleY;
    private float gravity=1f;
    private List<Obstacle> obstacles;
    private List<Integer> collisionCounts;
    private int collisionCount;
    private Paint chaserPaint;
    private int chaserdelay=2000;
    private boolean shouldJump=false;
    private int velocityY;
    private boolean isJumping;
    private int height1,height2;
    private boolean isTextVisible = true;
    private Handler handler = new Handler();
    private Handler handler2=new Handler();
    private boolean blink=true;
    float obstacleLeft;
    private boolean isfirst,issecond;
    float obstacleRight;
    private ArrayList<Obstacle> checker=new ArrayList<>();
    float obstacleTop;
    float obstacleBottom;
    private boolean skip=false;
    private boolean collisionboolean=false;
    private int count=0;
    private Obstacle walter;
    private int screenWidth,screenHeight;
    private Bitmap backgroundBitmap;
    private int obstaclejumps=0;
    private int bgspeed=-30;
    private int highscore;
    private Obstacle currentobs=null;
    float previousCircleX=0;
    private int backgroundX;
    private SharedPreferences sharedPreferences;
    private MediaPlayer media1,media2,media5;
    private boolean obstaclevisible=false;
    private Runnable blinkRunnable = new Runnable() {
        @Override
        public void run() {
            if(blink) {
                isTextVisible = !isTextVisible;
                invalidate();
                handler.postDelayed(this, 500);
            }
        }
    };
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.post(blinkRunnable);
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(blinkRunnable);
    }
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Context context2=getContext();
        media1=MediaPlayer.create(context2,R.raw.jump);
        media2=MediaPlayer.create(context2,R.raw.losingsound);
        media5=MediaPlayer.create(context2,R.raw.game);
        media5.start();
        sharedPreferences = context2.getSharedPreferences("highscorefile", MODE_PRIVATE);
        highscore = sharedPreferences.getInt("highscorekey",0);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bgclap);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        backgroundBitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, false);
        circlePaint = new Paint();
        bit1 = BitmapFactory.decodeResource(getResources(), R.drawable.gokuicon);
        bit2=BitmapFactory.decodeResource(getResources(), R.drawable.freddyicon);
        bit3=BitmapFactory.decodeResource(getResources(), R.drawable.cookie2);
        bit4=BitmapFactory.decodeResource(getResources(),R.drawable.scream2);
        bit5=BitmapFactory.decodeResource(getResources(),R.drawable.genie2);
        circlePaint.setColor(Color.RED);
        obstaclePaint = new Paint();
        obstaclePaint.setColor(Color.BLUE);
        obstacles = new ArrayList<>();
        chaserPaint=new Paint();
        chaserPaint.setColor(Color.BLUE);
    }
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width,height, oldWidth, oldHeight);
        int spacing = 350;
        chaserX=chaseradius;
        chaserY=height-chaseradius;
        chaserVelocityY=0;
        walter=new Obstacle(bit5, width, obstacleTop, width + bit5.getWidth(), height,up);
        chaserRect = new RectF(chaserX - chaseradius, chaserY - chaseradius,
                chaserX + chaseradius, chaserY + chaseradius);
        circleX = spacing + CIRCLE_RADIUS;
        circleY = height1- spacing - CIRCLE_RADIUS;
        generateObstacles(width, height);
    }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(backgroundBitmap, backgroundX, 0, null);
            counteract=0;
            index=index+1;
            if(isTextVisible) {
                Paint textPaint = new Paint();
                textPaint.setColor(Color.BLACK);
                textPaint.setTextSize(75f);
                String message = "Click to Start";
                float textX = getWidth()-1300;
                float textY = 120;
                Typeface typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
                textPaint.setTypeface(typeface);
                canvas.drawText(message, textX, textY, textPaint);
            }
            canvas.drawBitmap(backgroundBitmap, backgroundX, 0, null);
            if (chaserstart) {
                Paint tp=new Paint();
                tp.setColor(Color.WHITE);
                tp.setTextSize(75f);
                canvas.drawText("SCORE: "+obstaclejumps,getWidth()-1300,120,tp);
                if (chaserY >= getHeight() - chaseradius && chaserVelocityY >= 0) {
                    if (shouldJump) {
                        chaserVelocityY = -jumpForce;
                        shouldJump = false;
                    } else {
                        chaserVelocityY = 0;
                    }
                } else {
                    chaserVelocityY += gravity;
                }

                chaserY += chaserVelocityY;
                if (chaserY > getHeight() - chaseradius) {
                    chaserY = getHeight() - chaseradius;
                }

                float left = chaserX - chaseradius - jmpoffset;
                float top = chaserY - bit2.getHeight();
                canvas.drawBitmap(bit2, left, top, null);
            }
            int baselineY = getHeight() - 100;
            Paint baselinePaint = new Paint();
            baselinePaint.setColor(Color.WHITE);
            canvas.drawLine(0, baselineY, getWidth(), baselineY, baselinePaint);
            canvas.drawBitmap(bit1,circleX,circleY,null);
                int horizontalSpacing = 400;
                int previousObstacleRight = screenWidth;
                for (Obstacle obstacleRect : obstacles) {
                    if (skip) {
                        skip = false;
                    } else {
                        count += 1;
                    }
                    Random random = new Random();
                    int randomNumber = random.nextInt(3) + 1;
                    int obstacleLeft = previousObstacleRight + horizontalSpacing;
                    int obstacleRight = obstacleLeft + obstacleRect.getObstacleBitmap().getWidth();
                    obstacleRect.setLeft(obstacleLeft);
                    obstacleRect.setRight(obstacleRight);
                    obstacleRect.move(-OBSTACLE_SPEED);
                    OBSTACLE_SPEED += 0.0009;
                    if (obstacleRect.collidesWith(chaserRect)) {
                        chaserVelocityY = chaserjumpv;
                    }
                        obstacleRect.draw(canvas);
                    previousObstacleRight = obstacleRight;
                }
                Obstacle obs;
                if(obstacles.size()!=0) {
                    obs= obstacles.get(obstacles.size() - 1);
                }else{
                    generateObstacles(getWidth(),getHeight());
                    obs= obstacles.get(obstacles.size() - 1);
                }
                obstacleLeft = obs.left();
                obstacleRight = obs.right();
                obstacleTop = baselineY - 100;
                obstacleBottom = baselineY;
                if(chaserX+chaseradius>obstacleLeft && chaserX-chaseradius<obstacleRight-100 && chaserY+chaseradius>obstacleTop
                                    && chaserY-chaseradius<obstacleBottom){
                    shouldJump=true;
                }
                if (circleX  > obstacleLeft && circleX  < obstacleRight
                        && circleY + CIRCLE_RADIUS > obstacleTop && circleY - CIRCLE_RADIUS < obstacleBottom) {
                    if(obs.getObstacleBitmap().equals(bit3)) {
                        if (counter == 0) {
                            checker.add(obs);
                            counter++;
                            chaserX=chaserX+300;
                            count += 1;
                        } else if (counter != 0 && !(checker.contains(obs))) {
                            chaserX=circleX;
                            game = false;
                            media5.stop();
                            media2.start();
                            showdialog(obstaclejumps);
                        }
                    }else if(obs.getObstacleBitmap().equals(bit4)){
                        game = false;
                        media5.stop();
                        media2.start();
                        showdialog(obstaclejumps);
                    }
                    else{
                        //need to finish
                    }
                    obstaclePaint.setColor(Color.RED);
                }else{
                    if(chaserstart) {
                        obstaclejumps++;
                        obstaclePaint.setColor(Color.BLUE);
                    }
                }
            for (int i = obstacles.size() - 1; i >= 0; i--) {
                Obstacle obstacleRect = obstacles.get(i);
                if (obstacleRect.right()<= 0){
                    obstacles.remove(i);
                }
            }
            if (obstacles.size() < MAX_OBSTACLES) {
                if(!obstaclevisible) {
                    generateObstacles(getWidth(), getHeight());
                    obstaclevisible=true;
                }
            }
            if (isJumping) {
                velocityY += 2;
                circleY += velocityY;
                if (circleY >= baselineY - CIRCLE_RADIUS) {
                    circleY = baselineY - CIRCLE_RADIUS;
                    isJumping = false;
                }
            }
            if (highscore<obstaclejumps) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("highscorekey", obstaclejumps);
                editor.commit();
            }
            if(game) {
                invalidate();
            }
        }
    private void showdialog(int score){
        Context context=getContext();
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.gamegg);
        Button btnyellow=dialog.findViewById(R.id.btnyellow);
        TextView tscore=dialog.findViewById(R.id.scoretxt);
        tscore.setText("Your Score:"+String.valueOf(score));
        btnyellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity2.class);
                context.startActivity(intent);
            }
        });
        dialog.show();
    }
    private void generateObstacles(int width, int height) {
        List<Bitmap> obstacleBitmaps = new ArrayList<>();
        obstacleBitmaps.add(bit3);
        obstacleBitmaps.add(bit4);
        obstacleBitmaps.add(bit5);
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        obstacleBitmap=obstacleBitmaps.get(randomNumber);
        int obstacleTop = getHeight() -obstacleBitmap.getHeight();
        Obstacle obstacleRect;
        if(obstacleBitmap==bit5){
            obstacleRect=new Obstacle(obstacleBitmap,width,300,width + obstacleBitmap.getWidth(), 300+bit5.getHeight(),up);
        }else {
            obstacleRect = new Obstacle(obstacleBitmap, width, obstacleTop, width + obstacleBitmap.getWidth(), height, up);
        }
        obstacles.add(obstacleRect);
        if (obstacles.size() < MAX_OBSTACLES) {
            generateObstacles(width, height);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !isJumping) {
            isTextVisible=false;
            blink=false;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chaserstart = true;
                }
            }, chaserdelay);
            invalidate();
            jump();
        }
        return super.onTouchEvent(event);
    }
    private void jump() {
        velocityY = JUMP_VELOCITY;
        isJumping = true;
        media1.start();
    }
    public void startMoving() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    invalidate();
            }
        }, 0);
    }
}
