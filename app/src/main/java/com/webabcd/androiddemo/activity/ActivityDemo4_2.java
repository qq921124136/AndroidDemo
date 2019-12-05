/**
 * Activity 之间的跳转动画（本例用于演示一个 activity 关闭后，从堆栈中打开之前的 activity 时的跳转动画）
 *
 *
 * 一、单独指定
 * 在 finish() 之后通过 overridePendingTransition(int enterAnim, int exitAnim) 指定跳转动画，假设是从 A 跳转到 B，然后这里关闭了 B
 *     enterAnim - A 的出现动画的资源 id（传 0 代表无动画）
 *     exitAnim - B 的消失动画的资源 id（传 0 代表无动画）
 *
 *
 * 二、全局指定
 * 关于全局指定 activity 之间的跳转动画请参见 res/values/styles.xml
 */

package com.webabcd.androiddemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class ActivityDemo4_2 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_activitydemo4_2);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.activity_flip_horizontal_in, R.anim.activity_flip_horizontal_out);
    }
}
