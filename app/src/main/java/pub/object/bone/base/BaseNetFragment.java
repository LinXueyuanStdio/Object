package pub.object.bone.base;

import android.content.Context;
import android.content.Intent;

import pub.object.bone.order.Constant;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.bone.base.
 * @date 17-7-19
 * @discription 进一步封装fragment,加入网络连接功能
// */
public class BaseNetFragment extends BaseFragment {
    @SuppressWarnings("unused")
    private static final String TAG = "BaseNetFragment";


    //<启动方法>-------------------------------------------------------------------------------------
    /**
     * 启动这个Activity的Intent
     * @param context 　上下文
     * @return 返回intent实例
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, BaseNetFragment.class);
    }
    //</启动方法>------------------------------------------------------------------------------------


    /**
     * 获取接口访问分类地址
     * @return String
     */
    protected String getApiCategory() {return Constant.URL_SERVER_ADDRESS_NORMAL_HTTP;}


    //<生命周期>-------------------------------------------------------------------------------------
    //</生命周期>------------------------------------------------------------------------------------


    //<UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码--------------------------------
    @Override   //必须调用
    public void initView() {}
    //</UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>-----------------------------


    //<Data数据区>---存在数据获取或处理代码，但不存在事件监听代码-----------------------------------------
    @Override   //必须调用
    public void initData() {}
    //</Data数据区>---存在数据获取或处理代码，但不存在事件监听代码----------------------------------------


    //<Event事件区>---只要存在事件监听代码就是----------------------------------------------------------
    @Override   //必须调用
    public void initEvent() {}
    //</Event事件区>---只要存在事件监听代码就是---------------------------------------------------------


    //<内部类>---尽量少用----------------------------------------------------------------------------

    //</内部类>---尽量少用---------------------------------------------------------------------------

}
