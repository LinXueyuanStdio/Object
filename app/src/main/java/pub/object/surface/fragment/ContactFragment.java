package pub.object.surface.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pub.object.R;
import pub.object.bone.Manager.ImageManager;
import pub.object.bone.base.BaseNetFragment;
import pub.object.bone.model.Meizi;
import pub.object.bone.order.Constant;
import pub.object.system.NetSystem.Manager.OkhttpManager;
import pub.object.system.ThemeSystem.utils.ThemeUtils;
import pub.xylibrary.bone.util.Log;
import pub.xylibrary.bone.util.SnackbarUtil;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.surface.fragment.
 * @date 17-7-19
 * @discription 社交,只实现基础的交流
 */
public class ContactFragment extends BaseNetFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "ContactFragment";


    //<生命周期>-------------------------------------------------------------------------------------
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //</生命周期>------------------------------------------------------------------------------------





    //<UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码--------------------------------
    FrameLayout frameLayout;
    View view;

    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout; //下拉刷新layout
    private static RecyclerView contact_recyclerView;
    private LinearLayoutManager mLayoutManager;
    private ContactAdapter mAdapter;

    private List<Meizi> meizis;
    private int lastVisibleItem;
    private int screenwidth;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, frameLayout, false);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.contact_coordinatorLayout);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.contact_swipe_refresh);
        contact_recyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler);


        //<功能归类分区方法，必须调用>-----------------------------------------------------------------
        initView();
        initData();
        initEvent();
        //</功能归类分区方法，必须调用>----------------------------------------------------------------


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e(TAG, "isVisibleToUser = " + isVisibleToUser +
                    "\nview == null ? -> " + (view == null ? "null" : "not null") +
                    "\n用setUserVisibleHint实现懒加载,setUserVisibleHint()在onCreateView()之前调用");
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.load_framelayout, container, false);
                frameLayout = (FrameLayout) view.findViewById(R.id.loadframe);
                View loadView = LayoutInflater.from(mContext).inflate(R.layout.loading, frameLayout, false);
                frameLayout.addView(loadView);
            }
        }
    }

    @Override
    public void initView() {
        super.initView();
        swipeRefreshLayout.setColorSchemeColors(ThemeUtils.getColorById(mContext, R.color.theme_color_primary));
        swipeRefreshLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        contact_recyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
    }
    //</UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码-------------------------------





    //<Data数据区>---存在数据获取或处理代码，但不存在事件监听代码-----------------------------------------
    @Override
    public void initData() {
        super.initData();
        //执行加载数据
        new GetData().execute(Constant.URL_REQUEST_FOR_DATA_CONTACT + "10/1");
    }
    //</Data数据区>---存在数据获取或处理代码，但不存在事件监听代码----------------------------------------






    //<Event事件区>---只要存在事件监听代码就是----------------------------------------------------------
    @Override
    public void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(this);

        contact_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=mLayoutManager.getItemCount()) {
                    new GetData().execute(Constant.URL_REQUEST_FOR_DATA_CONTACT + "10/" + (++page));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

            }

        });
    }

    //<SwipeRefreshLayout.OnRefreshListener>--------------------------------------------------------
    @Override
    public void onRefresh() {
        page=1;
        new GetData().execute(Constant.URL_REQUEST_FOR_DATA_CONTACT + "10/1");
    }
    //</SwipeRefreshLayout.OnRefreshListener>--------------------------------------------------------

    //</Event事件区>---只要存在事件监听代码就是---------------------------------------------------------






    //<内部类>---尽量少用-----------------------------------------------------------------------------
    private class GetData extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            return OkhttpManager.get(params[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "onPostExecute()" + "result = " + result);
            if(!TextUtils.isEmpty(result)){

                JSONObject jsonObject;
                Gson gson=new Gson();
                String jsonData=null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(meizis==null||meizis.size()==0){
                    meizis= gson.fromJson(jsonData, new TypeToken<List<Meizi>>() {}.getType());
                    Meizi pages=new Meizi();
                    pages.setPage(page);
                    meizis.add(pages);
                }else{
                    List<Meizi> more= gson.fromJson(jsonData, new TypeToken<List<Meizi>>() {}.getType());
                    assert more != null;
                    meizis.addAll(more);
                    Meizi pages=new Meizi();
                    pages.setPage(page);
                    meizis.add(pages);
                }
                Log.e(TAG, "setAdapter");
                contact_recyclerView.setAdapter(mAdapter = new ContactAdapter());

//                if(mAdapter == null){
//                    contact_recyclerView.setAdapter(mAdapter = new ContactAdapter());
//                }else{
//                    mAdapter.notifyDataSetChanged();
//                }//转到其他fragment后再转回来,出现不更新的情况
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_contactfragment_recycler_view, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            screenwidth =outMetrics.widthPixels;
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.ll.setMinimumWidth(screenwidth);//设置LinearLayout宽度为屏幕宽度
//            Picasso.with(mContext).load(meizis.get(position).getUrl()).into(holder.iv);//格式正确，但不能加载图象

            holder.tv_contact_id.setText(" id: " + meizis.get(position).getId());
            holder.tv_contact_name.setText(meizis.get(position).getName());
            holder.tv_contact_time.setText(meizis.get(position).getTime());
            holder.tv_contact_sex.setText(meizis.get(position).getSex());
            holder.tv_contact_phone.setText(" phone: " + meizis.get(position).getPhone());

            ImageManager.getInstance().loadImage(mContext, meizis.get(position).getUrl(), holder.iv_contact_head);

            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SnackbarUtil.ShortSnackbar(coordinatorLayout,"点击第"+position+"个",SnackbarUtil.Info).show();
                }
            });
            holder.iv_contact_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        @Override
        public int getItemCount()
        {
            return meizis.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView iv_contact_head;

            private TextView tv_contact_id;
            private TextView tv_contact_name;
            private TextView tv_contact_time;
            private TextView tv_contact_sex;
            private TextView tv_contact_phone;

            private LinearLayout ll;
            public MyViewHolder(View view) {
                super(view);
                iv_contact_head = (ImageView) view.findViewById(R.id.iv_contact_head);

                tv_contact_id = (TextView) view.findViewById(R.id.tv_contact_id);
                tv_contact_name = (TextView) view.findViewById(R.id.tv_contact_name);
                tv_contact_time = (TextView) view.findViewById(R.id.tv_contact_time);
                tv_contact_sex = (TextView) view.findViewById(R.id.tv_contact_sex);
                tv_contact_phone = (TextView) view.findViewById(R.id.tv_contact_phone);

                ll = (LinearLayout) view.findViewById(R.id.ll_contact_item_main);
            }
        }

        public void addItem(Meizi meizi, int position) {
            meizis.add(position, meizi);
            notifyItemInserted(position);
            contact_recyclerView.scrollToPosition(position);
        }

        public void removeItem(final int position) {
            final Meizi removed=meizis.get(position);
            meizis.remove(position);
            notifyItemRemoved(position);
            SnackbarUtil.ShortSnackbar(coordinatorLayout,"你删除了第"+position+"个item",SnackbarUtil.Warning).setAction("撤销", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addItem(removed,position);
                    SnackbarUtil.ShortSnackbar(coordinatorLayout,"撤销了删除第"+position+"个item",SnackbarUtil.Confirm).show();
                }
            }).setActionTextColor(Color.WHITE).show();
        }

        public void removeItem(Meizi meizi) {
            int position = meizis.indexOf(meizi);
            meizis.remove(position);
            notifyItemRemoved(position);
        }

    }
    //</内部类>---尽量少用----------------------------------------------------------------------------

}
