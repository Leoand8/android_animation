package com.imooc.android_animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Bug问题：
 * 如果点击速度过快，当动画还未完全展开时，此时再点击红色按钮，程序会响应回收图片的事件，
 * 由于图片还未完全展开，导致出现动画展开和回收一起出现的混乱场面。
 * Created by Administrator on 2016/4/12.
 */
public class AnimatorActivity extends Activity implements View.OnClickListener {

    private int[] res = {
            R.id.imageView_a,
            R.id.imageView_b,
            R.id.imageView_c,
            R.id.imageView_d,
            R.id.imageView_e,
            R.id.imageView_f,
            R.id.imageView_g
    };
    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    private boolean flag = true;//点击动画收起标志
    private int ico_Count = res.length - 1;//展开的图标个数
    private int r = 200;//动画半径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        for (int i = 0; i < res.length; i++) {
            ImageView imageView = (ImageView) findViewById(res[i]);
            imageView.setOnClickListener(this);
            imageViewList.add(imageView);
        }
    }

    //扇形菜单动画
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_a:
                if (flag) {
                    //展开动画菜单
                    startAnim();
                } else {
                    //关闭动画菜单
                    closeAnim();
                }
                break;
            default:
                Toast.makeText(AnimatorActivity.this, "click" + view.getId(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //ObjectAnimator --继承于ValueAnimator，可以很好滴使用属性对话框架；
    private void startAnim() {
        for (int i = 1; i < res.length; i++) {
            ObjectAnimator animator_X = ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationX", 0F,
                    (float) (Math.sin(Math.toRadians((i - 1) * 90 / (ico_Count - 1))) * r));

            Log.i("startAnim", "startAnim: " + Math.sin(Math.toRadians((i - 1) * 90 / (ico_Count - 1))));

            ObjectAnimator animator_Y = ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY", 0F,
                    -(float) (Math.cos(Math.toRadians((i - 1) * 90 / (ico_Count - 1))) * r));

            animator_X.setDuration(200);//每个图标动画效果从开始到结束的总时间
            //setInterpolator－－－插值计算器，用于控制值变化的规律
//            animator_X.setInterpolator(new BounceInterpolator());//小球跳动效果
            animator_X.setStartDelay(i * 100);

            animator_Y.setDuration(200);
//            animator_Y.setInterpolator(new BounceInterpolator());
            animator_Y.setStartDelay(i * 100);

            animator_X.start();
            animator_Y.start();

            flag = false;
        }
    }

    private void closeAnim() {
        for (int i = 1; i < res.length; i++) {
            ObjectAnimator animator_X = ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationX",
                    (float) (Math.sin(Math.toRadians((i - 1) * 90 / (ico_Count - 1))) * r), 0F);

            ObjectAnimator animator_Y = ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY",
                    -(float) (Math.cos(Math.toRadians((i - 1) * 90 / (ico_Count - 1))) * r), 0F);

            animator_X.setDuration(200);
//            animator_X.setInterpolator(new BounceInterpolator());
//            animator_X.setStartDelay(i * 100);

            animator_Y.setDuration(200);
//            animator_Y.setInterpolator(new BounceInterpolator());
//            animator_Y.setStartDelay(i * 100);

            animator_X.start();
            animator_Y.start();

            flag = true;
        }
    }


    public void click(View view) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
    }

    public void move(View view) {
//        TranslateAnimation animation = new TranslateAnimation(0, 200, 0, 0);
//        animation.setDuration(1000);
//        animation.setFillAfter(true);
        ImageView imageView = (ImageView) findViewById(R.id.animator_img);
//        imageView.startAnimation(animation);

//        ObjectAnimator.ofFloat(imageView, "rotation", 0, 360F).setDuration(1000).start();//翻转360度
//        ObjectAnimator.ofFloat(imageView, "translationX", 0, 200F).setDuration(1000).start();//向右滑动200
//        ObjectAnimator.ofFloat(imageView, "translationY", 0, 200F).setDuration(1000).start();//向下滑动200

        //PropertyValuesHolder --用于控制动画集合的显示效果
//        PropertyValuesHolder p1=PropertyValuesHolder.ofFloat( "rotation", 0, 360F);
//        PropertyValuesHolder p2=PropertyValuesHolder.ofFloat( "translationX", 0, 200F);
//        PropertyValuesHolder p3=PropertyValuesHolder.ofFloat( "translationY", 0, 200F);
//        ObjectAnimator.ofPropertyValuesHolder(imageView, p1, p2, p3).setDuration(1000).start();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView, "rotation", 0, 360F);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageView, "translationX", 0, 200F);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(imageView, "translationY", 0, 200F);
        //Animatorset --用于控制动画集合的显示效果
        AnimatorSet set = new AnimatorSet();
//        set.playTogether(animator1, animator2, animator3);//一起播放动画
        set.playSequentially(animator2, animator3);//逐步开始动画
//        set.play(animator2).with(animator3);//两个一起开始
        set.play(animator1).after(animator2);//一个在另一个完成之后开始
        set.setDuration(1000).start();
    }

    public void move2(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0F, 1F);
        animator.setDuration(1000);
        //AnimatorListenerAdapter-- 用于动画监听器
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(AnimatorActivity.this, "onAnimationEnd", Toast.LENGTH_SHORT).show();
            }
        });
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                Toast.makeText(AnimatorActivity.this, "onAnimationEnd", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
        animator.start();
    }

    //ValueAnimator --数值发生器，可以实现很多很灵活的动画效果；
    public void move3(View view) {
        //计时器，从0到100
        final Button button = (Button) view;
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(5000);
        //AnimatorUpdateListener -- 用于动画监听器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setText("" + value);
            }
        });
        animator.start();

        //与计时器无关

        //TypeEvaluators －－－值计算器，用于控制值变化的规律
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float v, PointF o, PointF t1) {
                return null;
            }
        });
    }
}
