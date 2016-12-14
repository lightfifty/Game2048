package com.jike.whiterose.game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

/**
 * Created by whiterose on 2016/12/13.
 * 这个是游戏的主类，这里我们需要得到xml里面的参数，所以我们得有相关的构造方法，
 */

public class GameView extends GridLayout {

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
    private void initGameView() {
        setColumnCount(4);

        setOnTouchListener(new OnTouchListener() {
            //这四个变量相当于按下坐标和抬起坐标
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //监听触摸事件
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        Log.d("dd","ddd");
                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = motionEvent.getX() - startX;
                        offsetY = motionEvent.getY() - startY;
//                        Log.d("aa","aaa"+offsetX);
//                        Log.d("aa","aaa"+offsetY);
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                Log.d("left", "left");
                            } else if (offsetX > 5) {
                                Log.d("right", "right");
                            }
                        } else {
                            if (offsetY < -5) {
                                Log.d("up", "up");
                            } else if (offsetY > 5) {
                                Log.d("down", "down");
                            }
                        }
                        break;

                }
                return true;
            }
        });

    }

    /**
     * 这个方法监听我们的屏幕是否发生变化
     * 我们在配置文件里面已经设置屏幕不能横向显示了。
     * 实际上我们这个方法只有活动创建的时候才会使用一次
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth=(Math.min(w,h)-10)/4;

        Log.d("cardWidth",cardWidth+"");
        addCards(cardWidth,cardWidth);

    }


    /**
     * 这个方法用于添加卡片
     * @param cardWidth
     * @param cardHeight
     */
    private void addCards(int cardWidth,int cardHeight){
        Card c;

        for (int x=0;x<4;x++){
            for (int y=0;y<4;y++){
                c=new Card(getContext());
                c.setNum(2);
                addView(c,cardWidth,cardHeight);
            }
        }
    }

    private void swipeLeft() {

    }

    private void swipeRight() {

    }

    private void swipeUp() {

    }

    private void swipeDown() {

    }
}
