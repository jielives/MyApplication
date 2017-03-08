package com.administrator.myapplication;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ui.fragment.HomeFragment;
import ui.fragment.MoreFragment;
import ui.fragment.OrderFragment;
import ui.fragment.UserFragment;



public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_fragment_container)
    FrameLayout mainFragmentContainer;
    @InjectView(R.id.main_bottome_switcher_container)
    LinearLayout mainBottomeSwitcherContainer;
    private HomeFragment homeFragment ;
    private MoreFragment moreFragment ;
    private OrderFragment orderFragment ;
    private UserFragment userFragment ;
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = mainBottomeSwitcherContainer.indexOfChild(view);
            changeUI(index);
            changeFragment(index);
        }
    };

    private void changeFragment(int index) {
        //通过这个底部容器Item的index能获取到对应的Fragment，需要所有的fragment对号放好（集合）
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (index) {
            case 0 :
                if(homeFragment == null ) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.main_fragment_container,homeFragment,"homeFragmentTag");
                    hideFragment(transaction);
                    transaction.show(homeFragment);
                }else{
                    hideFragment(transaction);
                    transaction.show(homeFragment);
                }
                break;
            case 1 :
                if(orderFragment == null ) {
                    orderFragment = new OrderFragment();
                    transaction.add(R.id.main_fragment_container,orderFragment,"orderFragmentTag");
                    hideFragment(transaction);
                    transaction.show(orderFragment);
                }else{
                    hideFragment(transaction);
                    transaction.show(orderFragment);
                }
                break;
            case 2 :
                if(userFragment == null ) {
                    userFragment = new UserFragment();
                    transaction.add(R.id.main_fragment_container,userFragment,"userFragmentTag");
                    hideFragment(transaction);
                    transaction.show(userFragment);
                }else{
                    hideFragment(transaction);
                    transaction.show(userFragment);
                }
                break;
            case 3 :
                if(moreFragment == null ) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.main_fragment_container,moreFragment,"moreFragmentTag");
                    hideFragment(transaction);
                    transaction.show(moreFragment);
                }else{
                    hideFragment(transaction);
                    transaction.show(moreFragment);
                }
                break;
            default:
                break;
        }
            transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if(homeFragment != null){
            transaction.hide(homeFragment);
        }
        if(orderFragment != null){
            transaction.hide(orderFragment);
        }
        if(moreFragment != null){
            transaction.hide(moreFragment);
        }
        if(userFragment != null){
            transaction.hide(userFragment);
        }
    }

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
        if(savedInstanceState != null) {//内存重启调用
            homeFragment = (HomeFragment) fragmentManager.findFragmentByTag("homeFragmentTag");
            orderFragment = (OrderFragment) fragmentManager.findFragmentByTag("orderFragmentTag");
            userFragment = (UserFragment) fragmentManager.findFragmentByTag("userFragmentTag");
            moreFragment = (MoreFragment) fragmentManager.findFragmentByTag("moreFragmentTag");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setLinstener();
        init();
    }
    private void init() {
        onClickListener.onClick(mainBottomeSwitcherContainer.getChildAt(0));
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
