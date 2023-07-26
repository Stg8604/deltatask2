package com.example.deltaproject2;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private float chaserX=chaseradius;
    private float chaserY;
    private float chaserVelocityY;
    private RectF chaserRect;
    private int counteract;
    private float obstacleY;        // Vertical position of the obstacle
    private float obstacleVelocityY;    // Vertical velocity of the obstacle
    private float obstacleSpeed =7;
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
    private int jumpThreshold=50;
    private int circleY;
    private float gravity=2f;
    private List<Obstacle> obstacles;
    private List<Integer> collisionCounts;
    private int collisionCount;
    private Paint chaserPaint;
    private int chaserdelay=2000;
    private List<let> letters=new ArrayList<>();
    private boolean upp=true;
    private boolean shouldJump=false;
    private int velocityY;
    private boolean isJumping;
    private int height1,height2;
    private boolean isTextVisible = true;
    private int lettersOnScreen;
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
    private int floatingLetterCount=0;
    private Obstacle currentobs=null;
    float previousCircleX=0;
    private int backgroundX;
    private SharedPreferences sharedPreferences;
    private MediaPlayer media1,media2,media5;
    private boolean obstaclevisible=false;
    private int floatcount=0;
    private Rect bounds;
    Call<Data> call;
    Retrofit retrofit;
    Service service;
    String word;
    private static final String url="https://api-obstacle-dodge.vercel.app";
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
        Picasso picasso = Picasso.get();
        bit1=BitmapFactory.decodeResource(getResources(), R.drawable.helpy);
        bit2=BitmapFactory.decodeResource(getResources(), R.drawable.helpy);
        Glide.with(context)
                .asBitmap()
                .load(MainActivity2.str)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        // The image has been loaded successfully into the bitmap
                        // Do something with the bitmap here
                        bit1=bitmap;
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        // Error loading the image URL into the bitmap
                        // Handle the error condition appropriately
                        Log.e("Glide", "Image loading failed");
                    }
                });
        bit3=BitmapFactory.decodeResource(getResources(), R.drawable.cookie2);
        bit4=BitmapFactory.decodeResource(getResources(),R.drawable.scream2);
        bit5=BitmapFactory.decodeResource(getResources(),R.drawable.genie2);
        Glide.with(context)
                .asBitmap()
                .load(MainActivity2.str2)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        // The image has been loaded successfully into the bitmap
                        // Do something with the bitmap here
                        bit2=bitmap;
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        // Error loading the image URL into the bitmap
                        // Handle the error condition appropriately
                        Log.e("Glide", "Image loading failed");
                    }
                });
        circlePaint.setColor(Color.RED);
        obstaclePaint = new Paint();
        obstaclePaint.setColor(Color.BLUE);
        obstacles = new ArrayList<>();
        chaserPaint=new Paint();
        chaserPaint.setColor(Color.BLUE);
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
        call = service.getWord();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()) {
                    word=response.body().getWord();
                    //Toast.makeText(context2, word, Toast.LENGTH_SHORT).show();

                    //Log.e("api","name:"+data1.getName());
                    //we need to create a for each loop here
                    //tip.setText(message);
                } else {
                    Log.e("API", "Error: " + response.code() + " - " + response.message());
                    //tip.setText("Error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                //tip.setText("itsfailure");
                Log.d("API","Error");
            }
        }   );
    }
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width,height, oldWidth, oldHeight);
        int spacing = 350;
        chaserY=height-chaseradius;
        chaserVelocityY=0;
        obstacleVelocityY=2.0f;
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
            Paint newpaint=new Paint();
            newpaint.setColor(Color.BLUE);
            newpaint.setTextSize(65f);
            Typeface typical=Typeface.create(Typeface.MONOSPACE,Typeface.BOLD);
            newpaint.setTypeface(typical);
            canvas.drawBitmap(backgroundBitmap, backgroundX, 0, null);
            Obstacle obs;
            if(obstacles.size()!=0) {
                obs= obstacles.get(obstacles.size() - 1);
            }else{
                generateObstacles(getWidth(),getHeight());
                obs= obstacles.get(obstacles.size() - 1);
            }
                if (chaserstart) {
                    Paint tp=new Paint();
                    tp.setColor(Color.WHITE);
                    tp.setTextSize(75f);
                    canvas.drawText("SCORE: "+obstaclejumps,getWidth()-1300,120,tp);

                    if (chaserY >= getHeight() - bit2.getHeight() && chaserVelocityY >= 0) {
                        if (shouldJump || (obs.left() - bit2.getWidth() <= jumpThreshold && chaserVelocityY == 0)) {
                            //chaserVelocityY = -jumpForce;
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
                        chaserVelocityY=0;
                    }
                    float left = chaserX - chaseradius - jmpoffset;
                    float top = chaserY-100;
                    canvas.drawBitmap(bit2, left, top, null);
                }
            setLetters(2);
                //this is two
            Random random=new Random();
            int start=getHeight()-100;
            for (char letter : "king".toCharArray()) {
                letters.add(new let(letter,start));
                start=start+50;
            }
            /*if(!(word==null)){
                for (char letter : word.toCharArray()) {
                    letters.add(new let(letter, random.nextInt(canvas.getWidth()), random.nextInt(canvas.getHeight())));
                }
            }else{
                for (char letter : "test".toCharArray()) {
                    letters.add(new let(letter, random.nextInt(canvas.getWidth()), random.nextInt(canvas.getHeight())));
                }
            }
            if(obstaclejumps>1000){
                for (let letter : letters) {
                    if (letter.isfloating) {
                        bounds=new Rect();
                        newpaint.getTextBounds(String.valueOf(letter.getLetter()), 0, 1, bounds);
                        letter.setBounds(bounds);
                        canvas.drawText(String.valueOf(letter.getLetter()), letter.getX(), letter.getY(), newpaint);
                    }
                }
            }*/
            int baselineY = getHeight() - 100;
            Paint baselinePaint = new Paint();
            baselinePaint.setColor(Color.WHITE);
            canvas.drawLine(0, baselineY, getWidth(), baselineY, baselinePaint);
            Random rank=new Random();
            canvas.drawBitmap(bit1,circleX,circleY,null);
                int horizontalSpacing = 200;
                int previousObstacleRight = screenWidth;
                for (Obstacle obstacleRect : obstacles) {
                    if (skip) {
                        skip = false;
                    } else {
                        count += 1;
                    }
                    boolean x=rank.nextBoolean();
                    int ink=0;
                    int pas=500;
                    if (obstacleRect.getObstacleBitmap().equals(bit5)) {
                        if (upp) {
                            obstacleY -= obstacleVelocityY; // move the obstacle up
                            if (obstacleY <= 0) {
                                // Reached the minimum y-coordinate, change direction to move down
                                upp = false;
                            }
                        } else {
                            obstacleY += obstacleVelocityY; // move the obstacle down
                            if (obstacleY >= getHeight() - bit5.getHeight()) {
                                // Reached the maximum y-coordinate, change direction to move up
                                upp = true;
                            }
                        }
                    }
                    int obstacleLeft = previousObstacleRight + horizontalSpacing;
                    int obstacleRight = obstacleLeft + obstacleRect.getObstacleBitmap().getWidth();
                    obstacleRect.setLeft(obstacleLeft);
                    obstacleRect.setRight(obstacleRight);
                    obstacleRect.setBottom(obstacleY + obs.getObstacleBitmap().getHeight());
                    obstacleRect.setTop(obstacleY);
                    obstacleRect.draw(canvas);
                    obstacleRect.move(-OBSTACLE_SPEED);
                    OBSTACLE_SPEED += 0.0009;
                    if (obstacleRect.collidesWith(chaserRect)) {
                        chaserVelocityY = chaserjumpv;
                    }
                        obstacleRect.draw(canvas);
                    previousObstacleRight = obstacleRight;
                }
                obstacleLeft = obs.left();
                obstacleRight = obs.right();
                obstacleTop = baselineY-100;
                obstacleBottom = baselineY;
                RectF rect=new RectF(circleX,circleY+CIRCLE_RADIUS,circleX,circleY-CIRCLE_RADIUS);
                if(chaserX>obstacleLeft-300 && chaserX<obstacleRight-300 && chaserY+chaseradius>obstacleTop
                                    && chaserY-chaseradius<obstacleBottom){
                    shouldJump=true;
                }
                // Set the obstacle's position using obstacleY
                if (circleX  > obstacleLeft && circleX  < obstacleRight
                        && circleY + CIRCLE_RADIUS+75 > obstacleTop && circleY - CIRCLE_RADIUS-75 < obstacleBottom) {
                    if(obs.getObstacleBitmap().equals(bit3)) {
                        if (counter == 0) {
                            checker.add(obs);
                            counter++;
                            chaserX=chaserX+150;
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
                if (circleY >= baselineY - CIRCLE_RADIUS-75) {
                    circleY = baselineY - CIRCLE_RADIUS-75;
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
                intent.putExtra("score",score);
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
            obstacleRect = new Obstacle(obstacleBitmap, width, obstacleTop, width + obstacleBitmap.getWidth(), height, up);
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
        int action = event.getAction();
        float touchX = event.getX();
        float touchY = event.getY();
        return super.onTouchEvent(event);
    }
    public void setLetters(int count) {
        lettersOnScreen = count;
        invalidate(); // Redraw the canvas with the updated letter count
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
