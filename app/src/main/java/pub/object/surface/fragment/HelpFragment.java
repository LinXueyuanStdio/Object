package pub.object.surface.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pub.object.surface.view.KannerView;
import pub.object.system.NetSystem.Manager.OkhttpManager;
import pub.object.system.ThemeSystem.utils.ThemeUtils;
import pub.xylibrary.bone.util.Log;
import pub.xylibrary.bone.util.SnackbarUtil;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.surface.fragment.
 * @date 17-7-19
 * @discription 帮区
 */
public class HelpFragment extends BaseNetFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HelpFragment";


    //<生命周期>-------------------------------------------------------------------------------------
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //</生命周期>------------------------------------------------------------------------------------





    //<UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码--------------------------------
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout; //下拉刷新layout
    private static RecyclerView recyclerview;
    private LinearLayoutManager mLayoutManager;
    private KannerView kanner;
    private HelpAdapter mAdapter;
    private List<Meizi> meizis;
    private int lastVisibleItem ;
    private int page=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.line_coordinatorLayout);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.line_swipe_refresh);
        recyclerview = (RecyclerView) view.findViewById(R.id.line_recycler);


        //<功能归类分区方法，必须调用>-----------------------------------------------------------------
        initView();
        initData();
        initEvent();
        //</功能归类分区方法，必须调用>----------------------------------------------------------------


        return view;
    }
    @Override
    public void initView() {
        super.initView();

        swipeRefreshLayout.setColorSchemeColors(ThemeUtils.getColorById(mContext, R.color.theme_color_primary));
        swipeRefreshLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        recyclerview.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
    }
    //</UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码-------------------------------





    //<Data数据区>---存在数据获取或处理代码，但不存在事件监听代码-----------------------------------------
    @Override
    public void initData() {
        super.initData();
        //执行加载数据
        new GetData().execute(Constant.URL_REQUEST_FOR_DATA_HELP + "10/1");
    }
    //</Data数据区>---存在数据获取或处理代码，但不存在事件监听代码----------------------------------------





    //<Event事件区>---只要存在事件监听代码就是----------------------------------------------------------
    @Override
    public void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=mLayoutManager.getItemCount()) {
                    new GetData().execute(Constant.URL_REQUEST_FOR_DATA_HELP + "10/" + (++page));
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
        new GetData().execute(Constant.URL_REQUEST_FOR_DATA_HELP + "10/1");
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
            Log.e(TAG, "doInBackground -> get = " + OkhttpManager.get(params[0]) +
                    "\nlength = " + OkhttpManager.get(params[0]).length() +
                    "\nparams[0] = " + params);

            return OkhttpManager.get(params[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Log.e(TAG, "onPostExecute()" + " result = " + result +
//                    "\nresult.length = " + result.length());

            if(!TextUtils.isEmpty(result)){
                Log.i(TAG, "result");
                JSONObject jsonObject;
                Gson gson = new Gson();
                String jsonData = null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(meizis == null || meizis.size() == 0){
                    meizis = gson.fromJson(jsonData, new TypeToken<List<Meizi>>() {}.getType());
                    Meizi pages = new Meizi();
                    pages.setPage(page);
                    meizis.add(pages);
                }else{
                    List<Meizi> more = gson.fromJson(jsonData, new TypeToken<List<Meizi>>() {}.getType());
                    assert more != null;
                    meizis.addAll(more);
                    Meizi pages=new Meizi();
                    pages.setPage(page);
                    meizis.add(pages);
                }

//                recyclerview.setAdapter(mAdapter = new HelpAdapter());
// 2017-7-22 转到其他fragment后再转回来,出现不更新的情况.解决方法是上面1行,强行setAdapter()
// 2017-7-23见鬼了,上面的bug没有了,而且必须把kannerView放在第一次setAdapter()后,不然会addHeaderView()失败
                if(mAdapter==null){
                    recyclerview.setAdapter(mAdapter = new HelpAdapter());
//                    itemTouchHelper.attachToRecyclerView(recyclerview);
                    kanner = new KannerView(myContext);
                    kanner.setImagesUrl(new String[] {
                            "http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg",
                            "http://img03.muzhiwan.com/2015/06/05/upload_557165f4850cf.png",
                            "http://img02.muzhiwan.com/2015/06/11/upload_557903dc0f165.jpg",
                            "http://img04.muzhiwan.com/2015/06/05/upload_5571659957d90.png",
                            "http://img03.muzhiwan.com/2015/06/16/upload_557fd2a8da7a3.jpg" });
                    ViewGroup.LayoutParams lParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 450);
                    kanner.setLayoutParams(lParams);
                    mAdapter.addHeaderView(kanner);
                }else{
                    mAdapter.notifyDataSetChanged();
                }

            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder> {
        private View VIEW_FOOTER;
        private View VIEW_HEADER;

        //Type
        private int TYPE_NORMAL = 1000;
        private int TYPE_HEADER = 1001;
        private int TYPE_FOOTER = 1002;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_FOOTER) {
                return new MyViewHolder(VIEW_FOOTER);
            } else if (viewType == TYPE_HEADER) {
                return new MyViewHolder(VIEW_HEADER);
            } else {
                return new MyViewHolder(getLayout(R.layout.item_helpfragment_recycler_view));
            }

//            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//            DisplayMetrics outMetrics = new DisplayMetrics();
//            wm.getDefaultDisplay().getMetrics(outMetrics);
//            screenWidth =outMetrics.widthPixels;

        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
//            holder.ll.setMinimumWidth(screenwidth);//设置LinearLayout宽度为屏幕宽度
//            Log.e(TAG,meizis.get(position).getUrl() + " " + Picasso.with(myContext) + "");
//            Picasso.with(mContext).load(meizis.get(position).getUrl()).into(holder.iv);//格式正确，但不能加载图象

            if (!isHeaderView(position) && !isFooterView(position)) {
                int realPosition = position;
                if (haveHeaderView()) realPosition--;//

                holder.tv_help_id.setText(" id: " + meizis.get(realPosition).getId());
                holder.tv_help_name.setText(meizis.get(realPosition).getName());
                holder.tv_help_time.setText(meizis.get(realPosition).getTime());
                holder.tv_help_type.setText(meizis.get(realPosition).getType());
                holder.tv_help_typeRed.setText(meizis.get(realPosition).getTypeRed());
                holder.tv_help_pay.setText(meizis.get(realPosition).getPay());
                holder.tv_help_payRed.setText(meizis.get(realPosition).getPayRed());
                holder.tv_help_sex.setText(meizis.get(realPosition).getSex());
                holder.tv_help_phone.setText(" phone: " + meizis.get(realPosition).getPhone());

                ImageManager.getInstance().loadImage(mContext, meizis.get(position).getUrl(), holder.iv_help_head);

                final int finalPosition = realPosition;
                holder.ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SnackbarUtil.ShortSnackbar(coordinatorLayout,"点击第"+ finalPosition +"个",SnackbarUtil.Info).show();
                    }
                });
                holder.iv_help_head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                holder.iv_help_chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                holder.iv_help_help.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }


        }

        @Override
        public int getItemCount() {
            int count = (meizis == null ? 0 : meizis.size());
            if (VIEW_FOOTER != null) count++;
            if (VIEW_HEADER != null) count++;
            return count;
        }
        @Override
        public int getItemViewType(int position) {
            if (isHeaderView(position)) {
                return TYPE_HEADER;
            } else if (isFooterView(position)) {
                return TYPE_FOOTER;
            } else {
                return TYPE_NORMAL;
            }
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            try {
                if (recyclerview == null && recyclerview != recyclerView) {
                    recyclerview = recyclerView;
                }
                ifWithGridLayoutManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private View getLayout(int layoutId) {
            return LayoutInflater.from(mContext).inflate(layoutId, null);
        }

        public void addHeaderView(View headerView) {
            if (haveHeaderView()) {
                throw new IllegalStateException("hearview has already exists!");
            } else {
                //避免出现宽度自适应
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                headerView.setLayoutParams(params);
                VIEW_HEADER = headerView;
                ifWithGridLayoutManager();
                notifyItemInserted(0);
            }

        }

        public void addFooterView(View footerView) {
            if (haveFooterView()) {
                throw new IllegalStateException("footerView has already exists!");
            } else {
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                footerView.setLayoutParams(params);
                VIEW_FOOTER = footerView;
                ifWithGridLayoutManager();
                notifyItemInserted(getItemCount() - 1);
            }
        }

        /**
         * GridLayoutManager的时候,发现头部会跑到第一格
         * 此方法强行令头部占1行
         */
        private void ifWithGridLayoutManager() {
            if (recyclerview == null) return;
            final RecyclerView.LayoutManager layoutManager = recyclerview.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                        ((GridLayoutManager) layoutManager).getSpanSizeLookup();
                ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeaderView(position) || isFooterView(position)) ?
                                ((GridLayoutManager) layoutManager).getSpanCount() :
                                1;
                    }
                });
            }
        }

        private boolean haveHeaderView() {
            return VIEW_HEADER != null;
        }

        public boolean haveFooterView() {
            return VIEW_FOOTER != null;
        }

        private boolean isHeaderView(int position) {
            return haveHeaderView() && position == 0;
        }

        private boolean isFooterView(int position) {
            return haveFooterView() && position == getItemCount() - 1;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView iv_help_head;
            private TextView tv_help_id;
            private TextView tv_help_name;
            private TextView tv_help_time;
            private TextView tv_help_type;
            private TextView tv_help_typeRed;
            private TextView tv_help_pay;
            private TextView tv_help_payRed;
            private TextView tv_help_sex;
            private TextView tv_help_phone;
            private ImageView iv_help_help;
            private ImageView iv_help_chat;


            private LinearLayout ll;
            public MyViewHolder(View view) {
                super(view);
                iv_help_head = (ImageView) view.findViewById(R.id.iv_help_head);
                tv_help_id = (TextView) view.findViewById(R.id.tv_help_id);
                tv_help_name = (TextView) view.findViewById(R.id.tv_help_name);
                tv_help_time = (TextView) view.findViewById(R.id.tv_help_time);
                tv_help_type = (TextView) view.findViewById(R.id.tv_help_type);
                tv_help_typeRed = (TextView) view.findViewById(R.id.tv_help_typeRed);
                tv_help_pay = (TextView) view.findViewById(R.id.tv_help_pay);
                tv_help_payRed = (TextView) view.findViewById(R.id.tv_help_payRed);
                tv_help_sex = (TextView) view.findViewById(R.id.tv_help_sex);
                tv_help_phone = (TextView) view.findViewById(R.id.tv_help_phone);
                iv_help_help = (ImageView) view.findViewById(R.id.iv_help_help);
                iv_help_chat = (ImageView) view.findViewById(R.id.iv_help_chat);

                ll = (LinearLayout) view.findViewById(R.id.ll_help_item_main);
            }
        }

        public void addItem(Meizi meizi, int position) {
            meizis.add(position, meizi);
            notifyItemInserted(position);
            recyclerview.scrollToPosition(position);
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
