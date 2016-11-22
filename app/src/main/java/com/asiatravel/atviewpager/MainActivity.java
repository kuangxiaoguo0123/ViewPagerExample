package com.asiatravel.atviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.asiatravel.atviewpager.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private TextView tvLine;
    private LayoutParams layoutParams;// 布局参数
    private List<Fragment> lists;
    private RadioGroup mRadioGroup;
    private int width;
    private int lineWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tvLine = (TextView) findViewById(R.id.tvLine);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioRroup);

        // 获取line的长度 获取手机屏幕的宽度
        width = getWindowManager().getDefaultDisplay().getWidth();
        lineWidth = width / 3;
        // 得到tvLine参数类,参数类中包含tvLine的width,height,gravity等属性
        layoutParams = (LayoutParams) tvLine.getLayoutParams();
        // 把屏幕宽度赋值给layoutParams.width,不要写反了
        layoutParams.width = lineWidth;
        // 黑条的长度就是屏幕的1/3了
        tvLine.setLayoutParams(layoutParams);

        // 初始化ViewPager的集合
        initPager();

        // 创建适配器
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        // 给ViewPager设置适配器
        viewPager.setAdapter(adapter);
        // 给ViewPager设置监听事件
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                /**
                 * 当手动滑动viewPager时，需要设置mRadioGroup被选中的radioButton是当前viewPager
                 * 所在的角标，不然mRadioGroup的onCheckedChangeListener在点击tvLine之前所在的位置时
                 * 就不会生效。
                 */
                mRadioGroup.check(arg0);
            }

            /**
             * arg0:position当前ViewPager的索引值 arg1:表示当前Pager偏移的百分比 arg2:偏移了多少像素
             */
            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
                // 获取tvLine的左边距
                layoutParams = (LayoutParams) tvLine.getLayoutParams();
                // 说明它有偏移了
                if (layoutParams != null && arg1 != 0) {
                    layoutParams.leftMargin = (int) ((position + arg1) * layoutParams.width);
                    tvLine.setLayoutParams(layoutParams);// 具有新的layoutMargin的layoutParams
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        onCheckedChange();
    }

    /**
     * RadioButton监听事件
     */
    private void onCheckedChange() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_01:
                        changeItem(0, lineWidth, 0);
                        break;
                    case R.id.rb_02:
                        changeItem(lineWidth, lineWidth * 2, 1);
                        break;
                    case R.id.rb_03:
                        changeItem(lineWidth * 2, width, 2);
                        break;
                }
            }
        });
    }

    /**
     * 点击切换条目
     *
     * @param left  tvLine的左边位置
     * @param right tvLine的右边位置
     * @param index 同步切换viewPager显示位置
     */
    private void changeItem(int left, int right, int index) {
        tvLine.layout(left, tvLine.getTop(), right, tvLine.getBottom());
        viewPager.setCurrentItem(index);
    }

    //设置适配器
    class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int arg0) {

            return lists.get(arg0);
        }

        @Override
        public int getCount() {

            return lists.size();
        }

    }

    //初始化ViewPager
    private void initPager() {
        lists = new ArrayList<Fragment>();
        for (int i = 0; i < 3; i++) {
            // 创建一个Fragment对象
            MyFragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            fragment.setArguments(bundle);
            lists.add(fragment);
        }
    }
}
