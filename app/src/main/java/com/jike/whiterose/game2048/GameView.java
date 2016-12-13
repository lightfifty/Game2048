package com.jike.whiterose.game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

/**
 * Created by whiterose on 2016/12/13.
 * 这个是游戏的主类，这里我们需要得到xml里面的参数，所以我们得有相关的构造方法，
 * */

public class GameView extends GridView {

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    /**
     * 上面三个构造方法，不论从哪个方法进入，都调用initGameView方法，
     * 相当于initGameview方法是我们这个类的入口方法。
     */
    private void initGameView(){
        setOnTouchListener(new OnTouchListener() {
            private float startX,startY,offsetX,offsetY;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("dd","ddd");
                        startX=motionEvent.getX();
                        startY=motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX=motionEvent.getX()-startX;
                        offsetY=motionEvent.getY()-startY;
                        Log.d("aa","aaa"+offsetX);
                        Log.d("aa","aaa"+offsetY);
                        if (Math.abs(offsetX)>Math.abs(offsetY)){
                            if (offsetX<-5){
                                System.out.print("left");

                            }else if (offsetX>5){
                                System.out.print("right");
                            }
                        }else {
                            if (offsetY<-5){
                                System.out.print("up");
                            }else if (offsetY>5){
                                System.out.print("down");
                            }
                        }
                        break;

                }
                return true;
            }
        });

    }
}
