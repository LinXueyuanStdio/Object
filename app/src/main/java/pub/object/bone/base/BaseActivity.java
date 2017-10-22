package pub.object.bone.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pub.object.R;
import pub.object.surface.fragment.PublishFragment;
import pub.xylibrary.bone.interfaces.ActivityPresenter;
import pub.xylibrary.bone.manager.ThreadManager;
import pub.xylibrary.bone.util.Log;
import pub.xylibrary.bone.util.StringUtil;

/**
 * @PackageName pub.object.
 * @author dlinking-lxy
 * @date 17-7-5.
 * @discription 封装基类activity
 */
public abstract class BaseActivity extends AppCompatActivity  implements ActivityPresenter {
    @SuppressWarnings("unused")
    private static final String TAG = "BaseActivity";


    //<修饰系统Activity自带方法>----------------------------------------------------------------------
    /**
     * 该Activity实例，命名为context是因为大部分方法都只需要context，写成context使用更方便
     * @warn 不能在子类中创建
     */
    protected BaseActivity context = null;
    /**
     * 该Activity的界面，即contentView
     * @warn 不能在子类中创建
     */
    protected View view = null;
    /**
     * 布局解释器
     * @warn 不能在子类中创建
     */
    protected LayoutInflater inflater = null;
    /**
     * Fragment管理器
     * @warn 不能在子类中创建
     */
    protected FragmentManager fragmentManager = null;

    private PublishFragment fragment;//底部发表控制栏

    /**
     * 退出时之前的界面进入动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int enterAnim = R.anim.fade;
    /**
     * 退出时该界面动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int exitAnim = R.anim.right_push_out;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);
Log.e(TAG, isAlive + " " + (context == null ? "null" : "not null"));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        //<状态栏沉浸，4.4+生效>----------------------------------------------------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorateView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            //      | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 在华为上会有导航栏bug
            decorateView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //</状态栏沉浸，4.4+生效>---------------------------------------------------------------------

        pbBaseTitle = (ProgressBar) findViewById(R.id.pbBaseTitle);//绑定默认标题旁ProgressBar
        tvBaseTitle = (TextView) findViewById(R.id.tvBaseTitle);//绑定默认标题TextView
    }
    @Override
    protected void onResume() {
        Log.d(TAG, "\n onResume <<<<<<<<<<<<<<<<<<<<<<<");
        super.onResume();
        isRunning = true;
        Log.d(TAG, "onResume >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }
    @Override
    protected void onPause() {
        Log.d(TAG, "\n onPause <<<<<<<<<<<<<<<<<<<<<<<");
        super.onPause();
        isRunning = false;
        Log.d(TAG, "onPause >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }
    /**销毁并回收内存
     * @warn 子类如果要使用这个方法内用到的变量，应重写onDestroy方法并在super.onDestroy();前操作
     */
    @Override
    protected void onDestroy() {
        Log.d(TAG, "\n onDestroy <<<<<<<<<<<<<<<<<<<<<<<");
        dismissProgressDialog();
        ThreadManager.getInstance().destroyThread(threadNameList);
        if (view != null) {
            try {
                view.destroyDrawingCache();
            } catch (Exception e) {
                Log.w(TAG, "onDestroy  try { view.destroyDrawingCache();" +
                        " >> } catch (Exception e) {\n" + e.getMessage());
            }
        }

        isAlive = false;
        isRunning = false;
        super.onDestroy();

        inflater = null;
        view = null;
        pbBaseTitle = null;
        tvBaseTitle = null;

        fragmentManager = null;
        progressDialog = null;
        threadNameList = null;

        intent = null;

        context = null;

        Log.d(TAG, "onDestroy >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }
    @Override
    public void finish() {
        super.finish();//必须写在最前才能显示自定义动画
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (enterAnim > 0 && exitAnim > 0) {
                    try {
                        overridePendingTransition(enterAnim, exitAnim);
                    } catch (Exception e) {
                        Log.e(TAG, "finish overridePendingTransition(enterAnim, exitAnim);" +
                                " >> catch (Exception e) {  " + e.getMessage());
                    }
                }
            }
        });
    }
    /**一般用于对不支持的数据的处理，比如onCreate中获取到不能接受的id(id<=0)可以这样处理
     */
    public void finishWithError(String error) {
        showShortToast(error);
        enterAnim = exitAnim = R.anim.null_anim;
        finish();
    }
    //</修饰系统Activity自带方法>---------------------------------------------------------------------




    //<运行线程>-------------------------------------------------------------------------------------
    /**在UI线程中运行，建议用这个方法代替runOnUiThread
     * @param action
     */
    public final void runUiThread(Runnable action) {
        if (!isAlive()) {
            Log.w(TAG, "runUiThread  isAlive() == false >> return;");
            return;
        }
        runOnUiThread(action);
    }
    /**
     * 线程名列表
     */
    protected List<String> threadNameList;
    /**运行线程
     * @param name
     * @param runnable
     * @return
     */
    public final Handler runThread(String name, Runnable runnable) {
        if (!isAlive()) {
            Log.w(TAG, "runThread  isAlive() == false >> return null;");
            return null;
        }
        name = StringUtil.getTrimedString(name);
        Handler handler = ThreadManager.getInstance().runThread(name, runnable);
        if (handler == null) {
            Log.e(TAG, "runThread handler == null >> return null;");
            return null;
        }

        if (threadNameList.contains(name) == false) {
            threadNameList.add(name);
        }
        return handler;
    }
    //</运行线程>------------------------------------------------------------------------------------





    //<启动新Activity方法>---------------------------------------------------------------------------
    /**
     * 用于 打开activity以及activity之间的通讯（传值）等；一些通讯相关基本操作（打电话、发短信等）
     */
    protected Intent intent = null;
    /**打开新的Activity，向左滑入效果
     * @param intent
     */
    public void toActivity(Intent intent) {
        toActivity(intent, true);
    }
    /**打开新的Activity
     * @param intent
     * @param showAnimation
     */
    public void toActivity(Intent intent, boolean showAnimation) {
        toActivity(intent, -1, showAnimation);
    }
    /**打开新的Activity，向左滑入效果
     * @param intent
     * @param requestCode
     */
    public void toActivity(Intent intent, int requestCode) {
        toActivity(intent, requestCode, true);
    }
    /**打开新的Activity
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (intent == null) {
                    Log.w(TAG, "toActivity  intent == null >> return;");
                    return;
                }
                //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
    }
    //</启动新Activity方法>--------------------------------------------------------------------------





    //<自动设置标题方法>------------------------------------------------------------------------------
    /**自动把标题设置为上个Activity传入的INTENT_TITLE，建议在子类initView中使用
     * 这个方法没有return，tvTitle = tvBaseTitle，直接用tvBaseTitle
     * @must 在UI线程中调用
     */
    protected void autoSetTitle() {
        tvBaseTitle = autoSetTitle(tvBaseTitle);
    }
    /**自动把标题设置为上个Activity传入的INTENT_TITLE，建议在子类initView中使用
     * @param tvTitle
     * @return tvTitle 返回tvTitle是为了可以写成一行，如 tvTitle = autoSetTitle((TextView) findViewById(titleResId));
     * @must 在UI线程中调用
     */
    protected TextView autoSetTitle(TextView tvTitle) {
        if (tvTitle != null) {
            String title = getIntent().getStringExtra(INTENT_TITLE);
            if (StringUtil.isNotEmpty(title, false)) {
                tvTitle.setText(StringUtil.getString(title));
            }
            if (pbBaseTitle != null) {
                tvTitle.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (isShowingProgress() == false) {
                            initData();
                        }
                    }
                });
            }
        }
        return tvTitle;
    }
    //</自动设置标题方法>-----------------------------------------------------------------------------





    //<显示与关闭进度弹窗方法>-------------------------------------------------------------------------
    /**
     * 默认标题旁边的进度环，layout.xml中用@id/pbBaseTitle绑定。
     * @warn 如果子Activity的layout中没有android:id="@id/pbBaseTitle"的pbBaseTitle，使用前必须在子Activity中赋值
     */
    @Nullable
    protected ProgressBar pbBaseTitle;
    /**
     * 默认标题TextView，layout.xml中用@id/tvBaseTitle绑定。子Activity内调用autoSetTitle方法 会优先使用INTENT_TITLE
     * @see #autoSetTitle
     * @warn 如果子Activity的layout中没有android:id="@id/tvBaseTitle"的TextView，使用前必须在子Activity中赋值
     */
    @Nullable
    protected TextView tvBaseTitle;
    /**
     * 进度弹窗
     */
    protected ProgressDialog progressDialog = null;
    /**正在显示进度
     * @return
     */
    public boolean isShowingProgress() {
        if (pbBaseTitle != null) {
            return pbBaseTitle.getVisibility() == View.VISIBLE;
        }
        if (progressDialog != null) {
            return progressDialog.isShowing();
        }
        return false;
    }
    /**展示加载进度条,无标题
     * stringResId = R.string.loading
     */
    public void showProgressDialog() {
        showProgressDialog(R.string.loading);
    }
    /**展示加载进度条,无标题
     * @param stringResId
     */
    public void showProgressDialog(int stringResId) {
        try {
            showProgressDialog(null, context.getResources().getString(stringResId));
        } catch (Exception e) {
            Log.e(TAG, "showProgressDialog  showProgressDialog(null, context.getResources().getString(stringResId));");
        }
    }
    /**展示加载进度条,无标题
     * @param message
     */
    public void showProgressDialog(String message) {
        showProgressDialog(null, message);
    }
    /**展示加载进度条
     * @param title 标题
     * @param message 信息
     */
    public void showProgressDialog(final String title, final String message) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (pbBaseTitle != null) {
                    pbBaseTitle.setVisibility(View.VISIBLE);
                    String s = tvBaseTitle == null ? null : StringUtil.isNotEmpty(message, false) ? message : title;
                    if (StringUtil.isNotEmpty(s, true)) {
                        tvBaseTitle.setText(s);
                    }
                    return;
                }

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(context);
                }
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (StringUtil.isNotEmpty(title, false)) {
                    progressDialog.setTitle(title);
                }
                if (StringUtil.isNotEmpty(message, false)) {
                    progressDialog.setMessage(message);
                }
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        });
    }
    /**隐藏加载进度
     */
    public void dismissProgressDialog() {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                if (pbBaseTitle != null) {
                    pbBaseTitle.setVisibility(View.GONE);
                }
            }
        });
    }
    //</显示与关闭进度弹窗方法>------------------------------------------------------------------------





    //<show short toast 方法>------------------------------------------------------------------------
    /**快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     * @param stringResId
     */
    public void showShortToast(int stringResId) {
        try {
            showShortToast(context.getResources().getString(stringResId));
        } catch (Exception e) {
            Log.e(TAG, "showShortToast  context.getResources().getString(resId)" +
                    " >>  catch (Exception e) {" + e.getMessage());
        }
    }
    /**快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     * @param string
     */
    public void showShortToast(String string) {
        showShortToast(string, false);
    }
    /**快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     * @param string
     * @param isForceDismissProgressDialog
     */
    public void showShortToast(final String string, final boolean isForceDismissProgressDialog) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (isForceDismissProgressDialog) {
                    dismissProgressDialog();
                }
                Toast.makeText(context, "" + string, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //</show short toast 方法>----------------------------------------------------------------------





    //<ActivityPresenter接口实现>--------------------------------------------------------------------
    /**
     * 获取Activity
     *
     * @must 在非抽象Activity中 return this;
     */
    @Override
    public Activity getActivity() {
        return null;
    }
    /**
     * 返回按钮被点击
     * *Activity的返回按钮和底部弹窗的取消按钮几乎是必备，正好原生支持反射；而其它比如Fragment极少用到，也不支持反射
     *
     * @param v
     */
    @Override
    public void onReturnClick(View v) {
        onBackPressed();//会从最外层子类调finish();BaseBottomWindow就是示例
    }
    /**
     * 前进按钮被点击
     * *Activity常用导航栏右边按钮，而且底部弹窗BottomWindow的确定按钮是必备；而其它比如Fragment极少用到，也不支持反射
     *
     * @param v
     */
    @Override
    public void onForwardClick(View v) {

    }
    //</ActivityPresenter接口实现>-------------------------------------------------------------------





    //<Presenter接口实现>----------------------------------------------------------------------------
    /**
     * UI显示方法(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    @Override
    public void initView() {
        showQuickControl(true);
    }

    /**
     * @param show 显示或关闭底部播放控制栏
     */
    protected void showQuickControl(boolean show) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (show) {
            if (fragment == null) {
                fragment = PublishFragment.getInstance();
                ft.add(R.id.bottom_container, fragment).commitAllowingStateLoss();
            } else {
                ft.show(fragment).commitAllowingStateLoss();
            }
        } else {
            if (fragment != null)
                ft.hide(fragment).commitAllowingStateLoss();
        }
    }

    /**
     * Data数据方法(存在数据获取或处理代码，但不存在事件监听代码)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    @Override
    public void initData() {}
    /**
     * Event事件方法(只要存在事件监听代码就是)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    @Override
    public void initEvent() {}

    private boolean isAlive = false;
    private boolean isRunning = false;
    /**
     * 是否存活(已启动且未被销毁)
     */
    @Override
    public boolean isAlive() {
        return isAlive && context != null;
    }
    /**
     * 是否在运行
     */
    @Override
    public boolean isRunning() {
        return isRunning & isAlive();
    }
    //</Presenter接口实现>---------------------------------------------------------------------------

}
