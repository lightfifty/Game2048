package com.jike.whiterose.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by whiterose on 2016/12/14.
 * 我们游戏里面的每一个方格就是一个卡片，每个卡片都是一个对象。
 */

public class Card extends FrameLayout {

    //每个卡片里面都有一个数字，
    private int num = 0;
    //每个方块都得有一个textView控件来显示内容
    private TextView lable;


    //这里是默认的构造函数
    public Card(Context context) {
        super(context);
        lable = new TextView(context);
        lable.setTextSize(32);
        lable.setGravity(Gravity.CENTER);
        lable.setBackground(getResources().getDrawable(R.drawable.tvborder));
        //宽高设置为-1，代表填充满整个父级容器
        LayoutParams lp = new LayoutParams(-1, -1);
        addView(lable, lp);
        setNum(0);

    }

    public int getNum() {
        return num;
    }


    public void setNum(int num) {
        this.num = num;
        if (num <= 0) {
            lable.setText("");
        } else {
            lable.setText(num + "");
        }
    }

    /**
     * 这个方法判断两张卡片是否能折叠在一起
     *
     * @param obj
     * @return
     */
    public boolean equals(Card obj) {
        return getNum() == obj.getNum();
    }
}
