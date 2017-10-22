package pub.object.surface.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import pub.object.R;
import pub.object.bone.base.BaseActivity;
import pub.object.system.ThemeSystem.manager.ThemeManager;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.surface.activity.
 * @date 17-7-8
 * @discription
 */
public class ThemeActivity extends BaseActivity implements View.OnClickListener{
    @SuppressWarnings("unused")
    private static final String TAG = "ThemeActivity";


    //<启动方法>-------------------------------------------------------------------------------------
    /**
     * 启动这个Activity的Intent
     *
     * @param context 　上下文
     * @return 返回intent实例
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, ThemeActivity.class);
    }

    @Override
    public Activity getActivity() {
        return this;
    }
    //</启动方法>------------------------------------------------------------------------------------





    //<生命周期>-------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_theme);
        getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);

        //<功能归类分区方法，必须调用>-----------------------------------------------------------------
        initView();
        initData();
        initEvent();
        //</功能归类分区方法，必须调用>----------------------------------------------------------------
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //</生命周期>------------------------------------------------------------------------------------





    //<UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码--------------------------------
    ImageView[] mCards = new ImageView[8];
    Button mConfirm;
    Button mCancel;

    @Override
    public void initView() {//必须调用
        mCancel = (Button) findViewById(android.R.id.button2);
        mConfirm = (Button) findViewById(android.R.id.button1);
        mCards[0] = (ImageView) findViewById(R.id.theme_pink);
        mCards[1] = (ImageView) findViewById(R.id.theme_purple);
        mCards[2] = (ImageView) findViewById(R.id.theme_blue);
        mCards[3] = (ImageView) findViewById(R.id.theme_green);
        mCards[4] = (ImageView) findViewById(R.id.theme_green_light);
        mCards[5] = (ImageView) findViewById(R.id.theme_yellow);
        mCards[6] = (ImageView) findViewById(R.id.theme_orange);
        mCards[7] = (ImageView) findViewById(R.id.theme_red);
    }
    //</UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>-----------------------------





    //<Data数据区>---存在数据获取或处理代码，但不存在事件监听代码-----------------------------------------
    @Override
    public void initData() {//必须调用
        mCurrentTheme = ThemeManager.getTheme(getActivity());
    }
    //</Data数据区>---存在数据获取或处理代码，但不存在事件监听代码----------------------------------------





    //<Event事件区>---只要存在事件监听代码就是----------------------------------------------------------
    private int mCurrentTheme;
    private ClickListener mClickListener;

    @Override
    public void initEvent() {//必须调用
        setImageButtons(mCurrentTheme);
        for (ImageView card : mCards) {
            card.setOnClickListener(this);
        }
        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    private void setImageButtons(int currentTheme) {
        mCards[0].setSelected(currentTheme == ThemeManager.CARD_SAKURA);
        mCards[1].setSelected(currentTheme == ThemeManager.CARD_HOPE);
        mCards[2].setSelected(currentTheme == ThemeManager.CARD_STORM);
        mCards[3].setSelected(currentTheme == ThemeManager.CARD_WOOD);
        mCards[4].setSelected(currentTheme == ThemeManager.CARD_LIGHT);
        mCards[5].setSelected(currentTheme == ThemeManager.CARD_THUNDER);
        mCards[6].setSelected(currentTheme == ThemeManager.CARD_SAND);
        mCards[7].setSelected(currentTheme == ThemeManager.CARD_FIREY);
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }


    //-//<系统自带监听>------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        Log.i("theme", "onconclick");
        switch (v.getId()) {

            case android.R.id.button1:
                Log.e(TAG, "onClick >> button1\n");
                if (mClickListener != null) {
                    mClickListener.onConfirm(mCurrentTheme);
                    Log.e(TAG, "onConfirm != null >> ");
                }
            case android.R.id.button2:
                Log.e(TAG, "onClick >> button2\n");
                finish();
                break;
            case R.id.theme_pink:
                mCurrentTheme = ThemeManager.CARD_SAKURA;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_purple:
                mCurrentTheme = ThemeManager.CARD_HOPE;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_blue:
                mCurrentTheme = ThemeManager.CARD_STORM;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_green:
                mCurrentTheme = ThemeManager.CARD_WOOD;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_green_light:
                mCurrentTheme = ThemeManager.CARD_LIGHT;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_yellow:
                mCurrentTheme = ThemeManager.CARD_THUNDER;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_orange:
                mCurrentTheme = ThemeManager.CARD_SAND;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_red:
                mCurrentTheme = ThemeManager.CARD_FIREY;
                setImageButtons(mCurrentTheme);
                break;
            default:
                break;
        }
    }

    //-//-//<类相关监听>-----------------------------------------------------------------------------

    //-//-//</类相关监听>----------------------------------------------------------------------------

    //-//</系统自带监听>-----------------------------------------------------------------------------

    //</Event事件区>---只要存在事件监听代码就是---------------------------------------------------------





    //<内部类>---尽量少用----------------------------------------------------------------------------
    public interface ClickListener {
        void onConfirm(int currentTheme);
    }
    //</内部类>---尽量少用---------------------------------------------------------------------------

}
