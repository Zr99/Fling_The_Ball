package com.example.assignmentseven;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import static java.lang.Math.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class GameScreen extends AppCompatActivity {

    String TAG="TAG_GESTURE";
    //ScoreTextview
    private TextView scoreText;
    private TextView livesText;
    private TextView userText;

    //integer which stores the score and lives
    int score = 0;
    int lives = 5;

    //boolean for onFling
    private boolean ballReleased = false;
    //boolean for game over
    private boolean gameOver = false;
    String name = "";


    public class GraphicsView extends View {
        //Simple On Gesture listener to move the Player1 ball
        private GestureDetector gestureDetector;
        //paint objects
        Paint paintBall = new Paint();
        Paint paintTarget = new Paint();
        Paint paintObstacle = new Paint();
        //ball object
        Ball player1 = new Ball(50,100,400,0,0, getColor(R.color.colorPrimary));
        //Target Object
        float targetRandPos = 0;
        Target target = new Target(200,800,300,0,0,getColor((R.color.colorTarget)));
        //Obstacle Objects
        Obstacle obstacle1 = new Obstacle(35,300,398,0,-3, getColor(R.color.colorObstacle));
        Obstacle obstacle2 = new Obstacle(35,600,102,0,2, getColor(R.color.colorObstacle));

        //Velocity of flinged ball
        double xVelo = Math.pow(100,2);
        double yVelo = Math.pow(-100,2);
        double velocity = Math.sqrt(Math.abs(xVelo) + Math.abs(yVelo));

        //gravity constant
        float gravity = 0;

        public GraphicsView(Context context){
            super(context);
            //gesture listener
            gestureDetector = new GestureDetector(context,new MyGestureListener());
            paintBall.setColor(player1.get_color());
            paintTarget.setColor(target.get_color());
            paintObstacle.setColor(obstacle1.get_color());
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(!gameOver)
            {
                //update the speed
                player1.set_x(player1._x + player1._xSpeed);
                player1.set_y(player1._y - player1._ySpeed);
                //add gravity
                player1.set_ySpeed(player1._ySpeed - gravity);

                //userText.setText(name);

                //check if the ball collides with the target
                if(checkCollision(player1._x,target._x,player1._y,target._y,(player1._diameter/2),(target._diameter/2)))
                {
                    Toast.makeText(GameScreen.this, "Hit target!", Toast.LENGTH_SHORT).show();
                    //Reset position of the ball
                    player1._x = 100;
                    player1._y = 400;
                    player1._xSpeed = 0;
                    player1._ySpeed = 0;
                    //reset gravity
                    gravity = 0;
                    //Change position of the target
                    target._y = 100 + (float)(Math.random()*300);
                    //update the score
                    score += 10;
                    scoreText.setText("" + score);
                    ballReleased = false;
                }

                //Check if the ball hits one of the obstacles
                if(checkCollision(player1._x,obstacle1._x,player1._y,obstacle1._y,(player1._diameter/2),(obstacle1._diameter/2)) || checkCollision(player1._x,obstacle2._x,player1._y,obstacle2._y,(player1._diameter/2),(obstacle2._diameter/2)))
                {
                    Toast.makeText(GameScreen.this, "Hit obstacle!", Toast.LENGTH_SHORT).show();
                    //Reset position of the ball
                    player1._x = 100;
                    player1._y = 400;
                    player1._xSpeed = 0;
                    player1._ySpeed = 0;
                    //reset gravity
                    gravity = 0;
                    //update the score
                    if(score == 0){
                        score = 0;
                    }
                    else{
                        score -= 2;
                    }

                    scoreText.setText("" + score);
                    //update player lives
                    lives--;
                    livesText.setText("" + lives);
                    //make user be able to throw again
                    ballReleased = false;
                }

                //check if the ball goes out of bounds
                if (player1._x > canvas.getWidth() || player1._y > canvas.getHeight())
                {
                    Toast.makeText(GameScreen.this, "Out of bounds!", Toast.LENGTH_SHORT).show();
                    //Reset position of the ball
                    player1._x = 100;
                    player1._y = 400;
                    player1._xSpeed = 0;
                    player1._ySpeed = 0;
                    //reset gravity
                    gravity = 0;
                    //update score
                    if(score == 0){
                        score = 0;
                    }
                    else {
                        score--;
                    }

                    scoreText.setText("" + score);
                    //update player lives
                    lives--;
                    livesText.setText("" + lives);
                    //make user be able to throw again
                    ballReleased = false;
                }

                //make obstacles move up and down
                if(obstacle1._y <= 100 || obstacle2._y <= 100){
                    if(obstacle1._y <= 100) obstacle1._ySpeed = (float)Math.abs(obstacle1._ySpeed);
                    if(obstacle2._y <= 100) obstacle2._ySpeed = obstacle2._ySpeed * -1;
                }

                if(obstacle1._y >= 400 || obstacle2._y >= 400){
                    if(obstacle1._y >= 400) obstacle1._ySpeed = obstacle1._ySpeed * -1;
                    if(obstacle2._y >= 400) obstacle2._ySpeed = obstacle2._ySpeed * -1;
                }

                //add speed to the obstacles to move
                obstacle1._y += obstacle1._ySpeed;
                obstacle2._y += obstacle2._ySpeed;

                //check if the player has lives left
                if(lives == 0)
                {
                    //end the game
                    gameOver = true;


                }
                if(gameOver){
                    Toast.makeText(GameScreen.this, "GAME OVER!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(GameScreen.this,"Hi, " + name + ".  Your score is " + score , Toast.LENGTH_LONG).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                          finish();
                        }
                    }, 10000);
                }

                //draw player 1 ball
                canvas.drawCircle(player1.get_x(),player1.get_y(),player1.get_diameter()/2, paintBall);
                //draw target
                canvas.drawCircle(target.get_x(),target.get_y(),target.get_diameter()/2, paintTarget);
                //draw obstacles
                canvas.drawCircle(obstacle1.get_x(),obstacle1.get_y(),obstacle1.get_diameter()/2, paintObstacle);
                canvas.drawCircle(obstacle2.get_x(),obstacle2.get_y(),obstacle2.get_diameter()/2, paintObstacle);
                invalidate();
            }
        }


        //My Gesture Listener class
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(gestureDetector.onTouchEvent(event)){
                return true;
            }
            return super.onTouchEvent(event);
        }

        class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
            @Override
            public boolean onDown(MotionEvent e) {
                Log.i(TAG,"onDOWN");
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if(ballReleased == false)
                {
                    ballReleased = true;
                    Log.i(TAG,"onFLING");
                    xVelo = Math.pow((double)velocityX,2);
                    yVelo = Math.pow((double)velocityY,2);
                    velocity = Math.sqrt(Math.abs(xVelo) + Math.abs(yVelo));
                    //update ball speed
                    if (velocityX >=0 && velocityY <= 0){
                        player1.set_xSpeed((float)(velocity/330));
                        player1.set_ySpeed((float)(velocity/330));
                        gravity = 0.2f;
                    }
                    Log.i(TAG,"onFLING" + "velocity x " + velocityX);
                    Log.i(TAG,"onFLING" + "velocity y " + velocityY + "velocity " + velocity);
                }
                return true;
            }

        }
    }

    public boolean checkCollision(float x1, float x2, float y1, float y2, float r1, float r2){
        //check the distance between two points
        float dist = (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) );
        //if the distance between the points is less than the sum of the radius's then do something
        if(dist < (r1 + r2))
        {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        //set orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //enable immersive mode
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        //textviews for score and lives
        scoreText =  findViewById(R.id.textViewScore);
        livesText =  findViewById(R.id.textViewLives);
        userText = findViewById(R.id.usernameInGame);

        //create objects on screen
        GraphicsView graphicsView = new GraphicsView(this);
        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.gs_main);
        constraintLayout.addView(graphicsView);

        Intent intent =getIntent();
        name = intent.getStringExtra("username");

        userText.setText(name);
    }
}
