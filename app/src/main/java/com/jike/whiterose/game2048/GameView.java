package com.jike.whiterose.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

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
                                swipeLeft();
                                Log.d("left", "left");
                            } else if (offsetX > 5) {
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                                Log.d("up", "up");
                            } else if (offsetY > 5) {
                                swipeDown();
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
     * 动态计算卡片的宽高
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h) - 10) / 4;

        MainActivity.getMainActivity().clearScore();
        Log.d("cardWidth", cardWidth + "");
        addCards(cardWidth, cardWidth);

        //开始游戏
        startGame();

    }


    /**
     * 这个方法用于添加卡片
     *
     * @param cardWidth
     * @param cardHeight
     */
    private void addCards(int cardWidth, int cardHeight) {
        Card c;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                c = new Card(getContext());
                c.setNum(2);
                addView(c, cardWidth, cardHeight);
                //将我们的所有的卡片对象存入二维数组中
                cardMap[x][y] = c;
            }
        }
    }

    private void startGame() {
        //先进行初始化
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cardMap[x][y].setNum(-1);
            }
        }
        //开始添加两个随机数
        addRandomNum();
        addRandomNum();

    }

    /**
     * 用于给我们的空位置添加随机数字
     */
    private void addRandomNum() {

        emptyPoint.clear();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (cardMap[x][y].getNum() <= 0) {
                    emptyPoint.add(new Point(x, y));
                }
            }
        }
        //取到一个随机的位置
        Point p = emptyPoint.remove((int) (Math.random() * emptyPoint.size()));
        //给对应于位置添加数字2和4的概率为9:1
        cardMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);

    }

    /**
     * 这个方法判断游戏是否结束，游戏结束有两个条件：
     * 1就是没有多余的空格了
     * 2就是任何方向上都没有相邻的两个位置有空位置了
     */
    private void checkComplete() {
        //定义TRUE代表达到结束的条件，可以结束
        boolean complete = true;

        //下面这句定义的是一个标签
        ALL:
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (cardMap[x][y].getNum() == 0 ||
                        (x > 0 && cardMap[x][y].equals(cardMap[x - 1][y])) ||
                        (x < 3 && cardMap[x][y].equals(cardMap[x + 1][y])) ||
                        (y > 0 && cardMap[x][y].equals(cardMap[x][y - 1])) ||
                        (y < 3 && cardMap[x][y].equals(cardMap[x][y + 1]))
                        ) {
                    complete = false;
                    break ALL;
                }
            }
        }
//        ALL:
//        for (int y = 0; y < 4; y++) {
//            for (int x = 0; x < 4; x++) {
//
//                if (cardMap[x][y].getNum() == 0 ||
//                        (x > 0 && cardMap[x][y].equals(cardMap[x - 1][y])) ||
//                        (x < 3 && cardMap[x][y].equals(cardMap[x + 1][y])) ||
//                        (y > 0 && cardMap[x][y].equals(cardMap[x][y - 1])) ||
//                        (y < 3 && cardMap[x][y].equals(cardMap[x][y + 1]))
//                        ) {
//                    Log.d("complete", complete + "***");
//                    complete = false;
//                    break ALL;
//                }
//            }
//        }
        if (complete) {
            new AlertDialog.Builder(getContext())
                    .setTitle("你好")
                    .setMessage("游戏结束")
                    .setPositiveButton("重来", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startGame();
                        }
                    }).show();
        }
    }

    private void swipeLeft() {
        boolean merge = false;
        //向左移动一行一行遍历
        for (int x = 0; x < 4; x++) {
            //y相当于要比较的第一个位置
            for (int y = 0; y < 4; y++) {
                //拿第二个位置的数字进行比较，如果第二个数字为空，就直接拿第三个位置，
                for (int y1 = y + 1; y1 < 4; y1++) {
                    //第二个数字不为空，
                    if (cardMap[x][y1].getNum() > 0) {
                        //第一个数字为空，就直接向左移动一格
                        if (cardMap[x][y].getNum() <= 0) {
                            //拿第二个数字替换第一个数字
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            //第二个数字设置为零。
                            cardMap[x][y1].setNum(0);
                            //这个第一个数字位置已经数字了，而我们这个位置是第一个位置没有数字跳进来的，
                            // 需要重新遍历一次（再次判断第一个位置是否有数字）
                            y--;
                            //这个break跳出了当前循环，从数字变化了的那个y 的位置重新开始y+1遍历。
                            merge = true;
                            //如果第一位置数字不为空，并且和第二个位置数字相等，那就合并。
                        } else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            //合并后第一个位置的数字*2，但是不为空，所以不用y--回去再判断是否为空。
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum() / 2);
                            merge = true;
                        }
                        break;

                        //如果两个位置都有数字但是不相同，就什么都不做，继续往下遍历。
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            checkComplete();
        }

    }

    private void swipeRight() {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardMap[x][y1].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        } else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum() / 2);
                            merge = true;
                        }
                        break;

                    }
                }
            }
        }
        if (merge) {
            addRandomNum();  checkComplete();
        }
    }

    private void swipeUp() {
        boolean merge = false;
        // 网上滑动的话是一列一列的遍历，先从第一列开始
        for (int y = 0; y < 4; y++) {
            //从第一行开始遍历
            for (int x = 0; x < 4; x++) {
                //拿第二行的数字来进行比较
                for (int x1 = x + 1; x1 < 4; x1++) {
                    //如果第二行的数字不为空
                    if (cardMap[x1][y].getNum() > 0) {
                        //第一行的数字为空，那就移动第二行数字到第一行
                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            //由于第一行的数字现在不为空了，所以得重新遍历一次，所以break
                            x--;
                            merge = true;
                            //第一行数字和第二行数字相等，
                        } else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            //跳出循环，被比较的位置直接向下移动。
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum() / 2);
                            merge = true;
                        }
                        break;

                    }
                }
            }
        }
        if (merge) {
            addRandomNum();  checkComplete();
        }
    }

    private void swipeDown() {
        boolean merge = false;
        // 网下滑动的话是一列一列的遍历，先从第4列开始
        for (int y = 0; y < 4; y++) {
            //从第一行开始遍历
            for (int x = 3; x >= 0; x--) {
                //拿第二行的数字来进行比较
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    //如果第二行的数字不为空
                    if (cardMap[x1][y].getNum() > 0) {
                        //第一行的数字为空，那就移动第二行数字到第一行
                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            //由于第一行的数字现在不为空了，所以得重新遍历一次，所以break
                            x++;
                            merge = true;
                            //第一行数字和第二行数字相等，
                        } else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum() / 2);
                            merge = true;
                            //跳出循环，被比较的位置直接向下移动。
                        }
                        break;

                    }
                }
            }
        }
        if (merge) {
            addRandomNum();  checkComplete();
        }
    }

    //定义我们的卡片数组
    private Card[][] cardMap = new Card[4][4];
    //定义空着的位置编号，
    List<Point> emptyPoint = new ArrayList<Point>();
}
