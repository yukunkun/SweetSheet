package com.sweet.yukun.androidsweetsheet_yk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sweet.yukun.androidsweetsheet.entity.MenuEntity;
import com.sweet.yukun.androidsweetsheet.sweetpick.BlurEffect;
import com.sweet.yukun.androidsweetsheet.sweetpick.CustomDelegate;
import com.sweet.yukun.androidsweetsheet.sweetpick.DimEffect;
import com.sweet.yukun.androidsweetsheet.sweetpick.RecyclerViewDelegate;
import com.sweet.yukun.androidsweetsheet.sweetpick.SweetSheet;
import com.sweet.yukun.androidsweetsheet.sweetpick.ViewPagerDelegate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SweetSheet mSweetSheet;
    private SweetSheet mSweetSheet2;
    private SweetSheet mSweetSheet3;
    private RelativeLayout rl;
    private TextView mTvRv,mTvViewPager,mTvCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl = findViewById(R.id.rl);
        mTvRv = findViewById(R.id.tv_rv);
        mTvViewPager=findViewById(R.id.tv_viewpager);
        mTvCustomer=findViewById(R.id.tv_customer);

        mTvRv.setOnClickListener(this);
        mTvViewPager.setOnClickListener(this);
        mTvCustomer.setOnClickListener(this);

        setupViewpager();
        setupRecyclerView();
        setupCustomView();
    }


    private void setupRecyclerView() {

        final ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.drawable.ic_account_child;
        menuEntity1.titleColor = 0xff000000;
        menuEntity1.title = "code";
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.iconId = R.drawable.ic_account_child;
        menuEntity.titleColor = 0xFF5692F9;
        menuEntity.title = "SweetSheet";
        list.add(menuEntity1);
        list.add(menuEntity);
        for (int i = 0; i < 12; i++) {
            list.add(menuEntity);
        }
        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet = new SweetSheet(rl);

        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
        mSweetSheet.setBackgroundEffect(new BlurEffect(8));
        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                //即时改变当前项的颜色
                list.get(position).titleColor = 0xff5823ff;
                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();

                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                Toast.makeText(MainActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void setupViewpager() {
        mSweetSheet2 = new SweetSheet(rl);
        //从menu 中设置数据源
        mSweetSheet2.setMenuList(R.menu.menu_sweet);
        mSweetSheet2.setDelegate(new ViewPagerDelegate());
        mSweetSheet2.setBackgroundEffect(new DimEffect(0.5f));
        mSweetSheet2.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                Toast.makeText(MainActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupCustomView() {
        mSweetSheet3 = new SweetSheet(rl);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.DuangLayoutAnimation);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_custom_view, null, false);
        customDelegate.setCustomView(view);
        mSweetSheet3.setDelegate(customDelegate);
        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetSheet3.dismiss();
            }
        });
    }


    @Override
        public void onBackPressed() {

            if (mSweetSheet.isShow() || mSweetSheet2.isShow()) {
                if (mSweetSheet.isShow()) {
                    mSweetSheet.dismiss();
                }
                if (mSweetSheet2.isShow()) {
                    mSweetSheet2.dismiss();
                }
            } else {
                super.onBackPressed();
            }
        }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_rv) {
            if (mSweetSheet2.isShow()) {
                mSweetSheet2.dismiss();
            }
            if (mSweetSheet3.isShow()) {
                mSweetSheet3.dismiss();
            }
            mSweetSheet.toggle();
        }
        if (id == R.id.tv_viewpager) {
            if (mSweetSheet.isShow()) {
                mSweetSheet.dismiss();
            }
            if (mSweetSheet3.isShow()) {
                mSweetSheet3.dismiss();
            }
            mSweetSheet2.toggle();
        }
        if (id == R.id.tv_customer) {
            if (mSweetSheet.isShow()) {
                mSweetSheet.dismiss();
            }
            if (mSweetSheet2.isShow()) {
                mSweetSheet2.dismiss();
            }
            mSweetSheet3.toggle();
        }
    }
}
