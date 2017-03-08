package com.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_fragment_container)
    FrameLayout mainFragmentContainer;
    @InjectView(R.id.main_bottome_switcher_container)
    LinearLayout mainBottomeSwitcherContainer;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = mainBottomeSwitcherContainer.indexOfChild(view);
            changeUI(index);
        }
    };
    /*
        * 改变index对应的孩子的状态，包括这个孩子中所有控件的状态（不可用，enable=false）
        * 改变其他孩子的状态，也包括这些孩子中所有控件的状态
        * @param index
        * */
    private void changeUI(int index) {
        int childCount = mainBottomeSwitcherContainer.getChildCount();
        for(int i = 0; i < childCount; i++) {
//            判断i是否与index相同，相同不可用状态
            if (i == index) {
                //不可以再点击了
                mainBottomeSwitcherContainer.getChildAt(i).setEnabled(false);
                //每个Item中的控件都需要切换状态
                setEnable(mainBottomeSwitcherContainer.getChildAt(i),false);
            }else{
                mainBottomeSwitcherContainer.getChildAt(i).setEnabled(true);
                //每个item中的控件都需要切换状态
                setEnable(mainBottomeSwitcherContainer.getChildAt(i),true);
            }
        }
    }
    /*
* 将每个item中的所有控件状态一同改变
* 由于我们要处理通用的代码，那么item可能会有很多层，所以需要使用递归
* */
    private void setEnable(View item, boolean b) {
        item.setEnabled(b);//核心
        if(item instanceof ViewGroup) {//处理递归的
            int childCount = ((ViewGroup) item).getChildCount();
            for(int i = 0 ; i< childCount; i++) {
                setEnable(((ViewGroup) item).getChildAt(i),b);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setLinstener();
    }

    private void setLinstener() {
        //所有孩子不包括孙子
        int childCount = mainBottomeSwitcherContainer.getChildCount();
        for (int i = 0 ; i < childCount; i++) {
            FrameLayout childAt = (FrameLayout) mainBottomeSwitcherContainer.getChildAt(i);
            childAt.setOnClickListener(onClickListener);
        }
    }
}
