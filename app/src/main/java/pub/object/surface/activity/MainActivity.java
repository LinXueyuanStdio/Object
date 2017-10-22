package pub.object.surface.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pub.object.R;
import pub.object.bone.Manager.HandlerManager;
import pub.object.bone.base.BaseActivity;
import pub.object.surface.adapter.MenuItemAdapter;
import pub.object.surface.fragment.ContactFragment;
import pub.object.surface.dialog.DialogThemeFragment;
import pub.object.surface.fragment.HistoryFragment;
import pub.object.surface.fragment.TabNetPagerFragment;
import pub.object.surface.view.CustomPagerView;
import pub.object.surface.view.FirstView;
import pub.object.surface.view.SearchBoxView.SearchFragment;
import pub.object.surface.view.SearchBoxView.custom.IOnSearchClickListener;
import pub.object.system.AnimationSystem.ViewHelper;
import pub.object.system.ThemeSystem.manager.ThemeManager;
import pub.object.system.ThemeSystem.utils.ThemeUtils;
import pub.xylibrary.bone.util.Log;

/**
 * @PackageName pub.object.
 * @author dlinking-lxy
 * @date 17-7-7.
 * @discription 主activity
 */
public class MainActivity extends BaseActivity implements View.OnClickListener,
        DrawerLayout.DrawerListener, ViewPager.OnPageChangeListener, DialogThemeFragment.ClickListener,
        IOnSearchClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = "MainActivity";


    //<启动方法>-------------------------------------------------------------------------------------
    /**启动这个Activity的Intent
     * @param context　上下文
     * @return 返回intent实例
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
    @Override
    public Activity getActivity() {
        return this;
    }
    //</启动方法>------------------------------------------------------------------------------------





    //<生命周期>-------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firstView = new FirstView(this);
        firstView.show(R.drawable.art_login_bg, FirstView.SLIDE_LEFT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);

        //<功能归类分区方法，必须调用>-----------------------------------------------------------------
        initView();
        initData();
        initEvent();
        //</功能归类分区方法，必须调用>----------------------------------------------------------------

        HandlerManager.getInstance(this).postDelayed(new Runnable() {
            @Override
            public void run() {
                firstView.removeSplashScreen();
            }
        }, 3000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        firstView.removeSplashScreen();
    }
    //</生命周期>------------------------------------------------------------------------------------





    //<UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码--------------------------------
    private FirstView firstView;
    private ActionBar ab;
    private ImageView barnet, barmusic, barfriends, search;
    private SearchFragment searchFragment;
    private CustomPagerView customPagerView;
    private CustomPagerViewAdapter customPagerViewAdapter;
    private ArrayList<ImageView> tabs = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ListView mLvLeftMenu;

    @Override
    public void initView() {//必须调用
        drawerLayout = (DrawerLayout) findViewById(R.id.fd);
        assert drawerLayout != null;
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);

        barnet = (ImageView) findViewById(R.id.bar_net);
        barmusic = (ImageView) findViewById(R.id.bar_music);
        barfriends = (ImageView) findViewById(R.id.bar_friends);

        search = (ImageView) findViewById(R.id.bar_search);

        customPagerView = (CustomPagerView) findViewById(R.id.main_viewpager);

        setSearch();
        setUpDrawer();
        setToolBar();
        setViewPager();
        setQuickControl();
    }
    /**
     * 仿bilibili搜索栏
     */
    private void setSearch() {
        searchFragment = SearchFragment.getInstance();
    }
    /**
     * 侧滑栏
     */
    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.nav_header_main, mLvLeftMenu, false));
        mLvLeftMenu.setAdapter(new MenuItemAdapter(this));
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        drawerLayout.closeDrawers();
                        break;
                    case 2:
//                        Intent intent = new Intent(MainActivity.this, ThemeActivity.class);
//                        MainActivity.this.startActivity(intent);
                        DialogThemeFragment themeDialog = new DialogThemeFragment();
                        themeDialog.setClickListener(MainActivity.this);
                        themeDialog.show(getSupportFragmentManager(), "theme");
//                        MainActivity.this.finish();
                        drawerLayout.closeDrawers();
                        break;
                    case 3:
                        drawerLayout.closeDrawers();
                        break;
                    case 4:
                        drawerLayout.closeDrawers();
                        break;
                    case 5:
                        drawerLayout.closeDrawers();
                        break;
                }
            }
        });
    }
    /**
     * 左上角的侧滑栏入口
     */
    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setTitle("");
    }
    /**
     * 多界面
     */
    private void setViewPager() {
        tabs.add(barnet);
        tabs.add(barmusic);
        tabs.add(barfriends);
        final ContactFragment contactFragment = new ContactFragment();
        final TabNetPagerFragment tabPagerFragment = new TabNetPagerFragment();
        final HistoryFragment historyFragment = new HistoryFragment();
        customPagerViewAdapter = new CustomPagerViewAdapter(getSupportFragmentManager());
        customPagerViewAdapter.addFragment(contactFragment);
        customPagerViewAdapter.addFragment(tabPagerFragment);
        customPagerViewAdapter.addFragment(historyFragment);
        assert customPagerView != null;
        customPagerView.setAdapter(customPagerViewAdapter);
        customPagerView.setCurrentItem(1);

        barmusic.setSelected(true);
    }
    /**
     * 下方的快速入口
     */
    private void setQuickControl() {
        showQuickControl(true);
    }
    //</UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码-------------------------------





    //<Data数据区>---存在数据获取或处理代码，但不存在事件监听代码-----------------------------------------
    @Override
    public void initData() {//必须调用
//        showProgressDialog(getTitleName());
    }

    public String getTitleName() {
        return isSucceed || isShowingProgress() ? "发布服务" : "有点问题，点击重试";
    }
    //</Data数据区>---存在数据获取或处理代码，但不存在事件监听代码----------------------------------------






    //<Event事件区>---只要存在事件监听代码就是----------------------------------------------------------
    @Override
    public void initEvent() {//必须调用
        barnet.setOnClickListener(this);
        barmusic.setOnClickListener(this);
        barfriends.setOnClickListener(this);
        search.setOnClickListener(this);
        searchFragment.setOnSearchClickListener(this);
        drawerLayout.setDrawerListener(this);
        customPagerView.addOnPageChangeListener(this);
    }


    //-//<DialogThemeFragment.ClickListener>------------------------------------------------------------
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onConfirm(int currentTheme) {
        if (ThemeManager.getTheme(MainActivity.this) != currentTheme) {
            ThemeManager.setTheme(MainActivity.this, currentTheme);
            ThemeUtils.refreshUI(MainActivity.this, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            //for global setting, just do once
                            if (Build.VERSION.SDK_INT >= 21) {
                                final MainActivity context = MainActivity.this;
                                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);
                                getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
                            }
                        }

                        @Override
                        public void refreshSpecificView(View view) {
                        }
                    }
            );
        }
        getWindow().setStatusBarColor(Color.TRANSPARENT);


//        changeTheme();
    }
    //-//</DialogThemeFragment.ClickListener>-----------------------------------------------------------


    //-//<View.OnClickListener>---------------------------------------------------------------------
    private boolean isSucceed = true;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_net:     customPagerView.setCurrentItem(0); break;
            case R.id.bar_music:   customPagerView.setCurrentItem(1); break;
            case R.id.bar_friends: customPagerView.setCurrentItem(2); break;
            case R.id.bar_search:
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
                break;
            default:
                final Intent intent = new Intent(MainActivity.this, /*TODO*/PublishGethelpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                MainActivity.this.startActivity(intent);
                break;
        }
    }
    //-//</View.OnClickListener>--------------------------------------------------------------------


    //-//<DrawerLayout.DrawerListener>--------------------------------------------------------------
    /**
     * Called when a drawer's position changes.
     *
     * @param drawerView  The child view that was moved
     * @param slideOffset The new offset of this drawer within its range, from 0-1
     */
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        View mContent = drawerLayout.getChildAt(0);
        View mMenu = drawerView;
        float scale = 1 - slideOffset;
        float rightScale = 0.8f + scale * 0.2f;
        float leftScale = 1 - 0.3f * scale;
        Log.e(TAG, "\nscale:" + scale + "\nleftScale:" + leftScale +"\nrightScale:" + rightScale);

//        ViewHelper.setScaleX(mMenu, leftScale);
//        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
        ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));

//        ViewHelper.setPivotX(mContent, 0);
//        ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
        mContent.invalidate();
//        ViewHelper.setScaleX(mContent, rightScale);
//        ViewHelper.setScaleY(mContent, rightScale);
    }
    /**
     * Called when a drawer has settled in a completely open state.
     * The drawer is interactive at this point.
     *
     * @param drawerView Drawer view that is now open
     */
    @Override
    public void onDrawerOpened(View drawerView) {

    }
    /**
     * Called when a drawer has settled in a completely closed state.
     *
     * @param drawerView Drawer view that is now closed
     */
    @Override
    public void onDrawerClosed(View drawerView) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
    }
    /**
     * Called when the drawer motion state changes. The new state will
     * be one of {STATE_IDLE, STATE_DRAGGING, STATE_SETTLING}.
     *
     * @param newState The new drawer motion state
     */
    @Override
    public void onDrawerStateChanged(int newState) {

    }
    //-//</DrawerLayout.DrawerListener>-------------------------------------------------------------


    //-//<ViewPager.OnPageChangeListener>-----------------------------------------------------------
    /**
     * canOpenDrawer[0]是控制侧滑栏抽屉何时打开的重要参数
     * {state-[1开始2进行中0完毕]
     *       -[1->2->0 viewpage间的切换, 1->0 viewpage与drawer间的切换] }
     */
    final int[] canOpenDrawer = {0};
    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        switchTabs(position);
    }
    @Override
    public void onPageScrollStateChanged(int state) {
        Log.e(TAG, "state" + state);
        if (customPagerView.getCurrentItem() == 0) {
            switch (state) {
                case 1: canOpenDrawer[0] =0; break;
                case 2: canOpenDrawer[0]--; break;
                case 0: canOpenDrawer[0]++; break;
            }
            if (canOpenDrawer[0] == 1) {
                // 只要有canOpenDrawer[0] == 1,一定已经执行switch(state)case 0,且state==0时手势完毕
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        }
    }
    //-//</ViewPager.OnPageChangeListener>----------------------------------------------------------


    //-//</IOnSearchClickListener>------------------------------------------------------------------
    @Override
    public void OnSearchClick(String keyword) {
        Toast.makeText(this, keyword, Toast.LENGTH_SHORT).show();
    }
    //-//</IOnSearchClickListener>------------------------------------------------------------------


    //-//<FragmentActivity>-------------------------------------------------------------------------
    private long time = 0;
    /**
     * 双击返回桌面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    fragment.onRequestPermissionsResult(requestCode,permissions,grantResults);
                }
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    //-//</FragmentActivity>------------------------------------------------------------------------


    //-//<Activity>---------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case android.R.id.home: //Menu icon
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private static final int REQUEST_RECHARGE = 1;
    private static final int REQUEST_WITHDRAW = 2;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }


    }
    //-//</Activity>--------------------------------------------------------------------------------

    //</Event事件区>---只要存在事件监听代码就是---------------------------------------------------------





    //<内部类>---尽量少用----------------------------------------------------------------------------
    static class CustomPagerViewAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        public CustomPagerViewAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }
    //</内部类>---尽量少用---------------------------------------------------------------------------

}
