package pub.object.surface.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import pub.object.R;
import pub.object.bone.base.BaseFragment;
import pub.object.bone.base.BaseNetFragment;
import pub.object.surface.activity.PublishGethelpActivity;
import pub.object.surface.activity.PublishHelpActivity;
import pub.xylibrary.bone.util.Log;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.surface.fragment.
 * @date 17-7-22
 * @discription 下面的导航,用于快速发布
 */
public class PublishFragment extends BaseFragment implements View.OnClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = "PublishFragment";


    //<启动方法>-------------------------------------------------------------------------------------
    /**
     * 启动这个Fragment的Intent
     *
     * @param context 　上下文
     * @return 返回intent实例
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, BaseNetFragment.class);
    }
    public static PublishFragment getInstance() {
        return new PublishFragment();
    }
    //</启动方法>------------------------------------------------------------------------------------





    //<生命周期>-------------------------------------------------------------------------------------
    //</生命周期>------------------------------------------------------------------------------------





    //<UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码--------------------------------
    FrameLayout frameLayout;//加载中...
    View view;

    LinearLayout ll_publish_help;
    LinearLayout ll_publish_gethelp;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e(TAG, "isVisibleToUser = " + isVisibleToUser +
                    "\nview == null ? ->" + (view == null ? "null" : "not null") +
                    "\n用setUserVisibleHint实现懒加载,setUserVisibleHint()在onCreateView()之前调用");
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.load_framelayout, container, false);
                frameLayout = (FrameLayout) view.findViewById(R.id.loadframe);
                View loadView = LayoutInflater.from(mContext).inflate(R.layout.loading, frameLayout, false);
                frameLayout.addView(loadView);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publish, frameLayout, false);
        ll_publish_help = (LinearLayout) view.findViewById(R.id.ll_publish_help);
        ll_publish_gethelp = (LinearLayout) view.findViewById(R.id.ll_publish_gethelp);


        //<功能归类分区方法，必须调用>-----------------------------------------------------------------
        initView();
        initData();
        initEvent();
        //</功能归类分区方法，必须调用>----------------------------------------------------------------


        return view;
    }

    @Override
    public void initView() {//必须调用

    }
    //</UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>-----------------------------





    //<Data数据区>---存在数据获取或处理代码，但不存在事件监听代码-----------------------------------------
    @Override
    public void initData() {//必须调用

    }
    //</Data数据区>---存在数据获取或处理代码，但不存在事件监听代码----------------------------------------





    //<Event事件区>---只要存在事件监听代码就是----------------------------------------------------------
    @Override
    public void initEvent() {//必须调用
        ll_publish_help.setOnClickListener(this);
        ll_publish_gethelp.setOnClickListener(this);

    }


    //-//<View.OnClickListener>---------------------------------------------------------------------
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_publish_help:
                Toast.makeText(mContext, "publish_help", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "help");
                Intent intent_help = new Intent(mContext, PublishHelpActivity.class);
                mContext.startActivity(intent_help);
                break;
            case R.id.ll_publish_gethelp:
                Toast.makeText(mContext, "publish_gethelp", Toast.LENGTH_SHORT).show();
                Intent intent_gethelp = new Intent(mContext, PublishGethelpActivity.class);
                mContext.startActivity(intent_gethelp);
                Log.e(TAG, "gethelp");
                break;
        }
    }
    //-//</View.OnClickListener>---------------------------------------------------------------------

    //</Event事件区>---只要存在事件监听代码就是---------------------------------------------------------





    //<内部类>---尽量少用----------------------------------------------------------------------------

    //</内部类>---尽量少用---------------------------------------------------------------------------

}
