package pub.object.surface.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pub.object.R;
import pub.object.bone.base.BaseActivity;
import pub.object.bone.order.Constant;
import pub.object.bone.util.CommonUtils;
import pub.object.system.NetSystem.Manager.OkhttpManager;
import pub.object.system.ThemeSystem.utils.ThemeUtils;
import pub.xylibrary.bone.util.Log;
import pub.xylibrary.bone.util.StringUtil;
import pub.xylibrary.surface.window.BottomMenuWindow;
import pub.xylibrary.surface.window.EditTextInfoWindow;
import pub.xylibrary.surface.window.PlacePickerWindow;
import pub.xylibrary.surface.window.TimePickerWindow;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.surface.fragment.
 * @date 17-7-19
 * @discription 发布求助编辑界面
 * @use toActivity(PublishGethelpActivity.createIntent(...));
 */
public class PublishGethelpActivity extends BaseActivity implements View.OnClickListener {
	@SuppressWarnings("unused")
	private static final String TAG = "PublishGethelpActivity";


	//<启动方法>-------------------------------------------------------------------------------------
	/**
	 * 启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, PublishGethelpActivity.class);
	}
	@Override
	public Activity getActivity() {
		return this;
	}
	//</启动方法>------------------------------------------------------------------------------------





	//<生命周期>-------------------------------------------------------------------------------------
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gethelp);
		context = this;

		//<功能归类分区方法，必须调用>-----------------------------------------------------------------
		initView();
		initData();
		initEvent();
		//</功能归类分区方法，必须调用>----------------------------------------------------------------

		if (Build.VERSION.SDK_INT >= 21) {
			final PublishGethelpActivity context = this;
			ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
			setTaskDescription(taskDescription);
			getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
			Log.e(TAG, "status bar change color");
		}
		if (CommonUtils.isLollipop() && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions((Activity) this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
		}
	}
	//</生命周期>------------------------------------------------------------------------------------





	//<UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码--------------------------------
	TextView tv_gethelp_publish_name;
	TextView tv_gethelp_publish_phone;
	TextView tv_gethelp_publish_place;
	TextView tv_gethelp_publish_type;
	TextView tv_gethelp_publish_money;
	EditText et_gethelp_publish_message;
	ImageView iv_gethelp_publish_photo;
	TextView tv_gethelp_publish_setting;
	TextView tv_gethelp_publish_time;
	Button btn_gethelp_publish_publish;

	LinearLayout ll_gethelp_publish_place;
	LinearLayout ll_gethelp_publish_type;
	LinearLayout ll_gethelp_publish_money;
	LinearLayout ll_gethelp_publish_setting;
	LinearLayout ll_gethelp_publish_time;

	@Override
	public void initView() {//必须调用
		autoSetTitle();

		tv_gethelp_publish_name = (TextView) findViewById(R.id.tv_gethelp_publish_name);
		tv_gethelp_publish_phone = (TextView) findViewById(R.id.tv_gethelp_publish_phone);
		tv_gethelp_publish_place = (TextView) findViewById(R.id.tv_gethelp_publish_place);
		tv_gethelp_publish_type = (TextView) findViewById(R.id.tv_gethelp_publish_type);
		tv_gethelp_publish_money = (TextView) findViewById(R.id.tv_gethelp_publish_money);
		et_gethelp_publish_message = (EditText) findViewById(R.id.et_gethelp_publish_message);
		iv_gethelp_publish_photo = (ImageView) findViewById(R.id.iv_gethelp_publish_photo);
		tv_gethelp_publish_setting = (TextView) findViewById(R.id.tv_gethelp_publish_setting);
		tv_gethelp_publish_time = (TextView) findViewById(R.id.tv_gethelp_publish_time);
		btn_gethelp_publish_publish = (Button) findViewById(R.id.btn_gethelp_publish_publish);

		ll_gethelp_publish_place = (LinearLayout) findViewById(R.id.ll_gethelp_publish_place);
		ll_gethelp_publish_type = (LinearLayout) findViewById(R.id.ll_gethelp_publish_type);
		ll_gethelp_publish_money = (LinearLayout) findViewById(R.id.ll_gethelp_publish_money);
		ll_gethelp_publish_setting = (LinearLayout) findViewById(R.id.ll_gethelp_publish_setting);
		ll_gethelp_publish_time = (LinearLayout) findViewById(R.id.ll_gethelp_publish_time);
	}
	//</UI显示区>---操作UI，但不存在数据获取或处理代码，也不存在事件监听代码--------------------------------





	//<Data数据区>---存在数据获取或处理代码，但不存在事件监听代码-----------------------------------------
	String publish_name;
	String publish_phone;
	String publish_place;
	String publish_type;
	String publish_money;
	String publish_message;
	String publish_setting;
	String publish_time;

	String[] PUBLISH_TYPES = {"加急", "普通"};
	private int[] selectedTime = new int[]{12, 0, 0};

	@Override
	public void initData() {//必须调用

//		showProgressDialog(getTitleName());
	}

//	public String getTitleName() {
//		return isSucceed || isShowingProgress() ? "发布服务" : "有点问题，点击重试";
//	}
	//</Data数据区>---存在数据获取或处理代码，但不存在事件监听代码----------------------------------------





	//<Event事件区>---只要存在事件监听代码就是----------------------------------------------------------
	@Override
	public void initEvent() {//必须调用
		iv_gethelp_publish_photo.setOnClickListener(this);
		btn_gethelp_publish_publish.setOnClickListener(this);

		ll_gethelp_publish_place.setOnClickListener(this);
		ll_gethelp_publish_type.setOnClickListener(this);
		ll_gethelp_publish_money.setOnClickListener(this);
		ll_gethelp_publish_setting.setOnClickListener(this);
		ll_gethelp_publish_time.setOnClickListener(this);
	}


	//-//<View.OnClickListener>---------------------------------------------------------------------
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_gethelp_publish_place:
				toActivity(PlacePickerWindow.createIntent(context, getPackageName(), 2), REQUEST_PLACE, false);
				break;
			case R.id.ll_gethelp_publish_type:
				toActivity(BottomMenuWindow.createIntent(context, PUBLISH_TYPES)
						.putExtra(BottomMenuWindow.INTENT_TITLE, "选择能提供的帮助类型"), REQUEST_TYPE, false);
				break;
			case R.id.ll_gethelp_publish_money:
				toActivity(EditTextInfoWindow.createIntent(context, EditTextInfoWindow.TYPE_NUMBER
						, "请输入金额（单位：元）", StringUtil.getTrimedString(tv_gethelp_publish_money),
						getPackageName()), REQUEST_MONEY, false);
				break;
			case R.id.iv_gethelp_publish_photo:
				showShortToast("假的");
				break;
			case R.id.ll_gethelp_publish_setting:
				showShortToast("假的");
				break;
			case R.id.ll_gethelp_publish_time:
				toActivity(TimePickerWindow.createIntent(context, selectedTime), REQUEST_TIME, false);
				break;
			case R.id.btn_gethelp_publish_publish:
				showShortToast(publish_place + "\n" + publish_type + "\n" + publish_money + "\n" + publish_time);
				new PostData().execute(Constant.URL_REQUEST_FOR_DATA_HELP + "10/1");
				break;
		}
	}
	//-//<View.OnClickListener>---------------------------------------------------------------------


	//-//<Activity>---------------------------------------------------------------------------------
	private static final int REQUEST_PLACE = 0;
	private static final int REQUEST_TYPE = 1;
	private static final int REQUEST_MONEY = 2;
	private static final int REQUEST_TIME = 3;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		if (data != null) {
			switch (requestCode) {
				case REQUEST_PLACE:
					ArrayList<String> placeList = data.getStringArrayListExtra(PlacePickerWindow.RESULT_PLACE_LIST);
					if (placeList != null) {
						String place = "";
						for (String s : placeList) {
							place += StringUtil.getTrimedString(s);
						}
						publish_place = place;
						tv_gethelp_publish_place.setText(publish_place);
						showShortToast("选择的地区为: " + place);
					}
					break;
				case REQUEST_TYPE:
					int selectedPosition = data.getIntExtra(BottomMenuWindow.RESULT_ITEM_ID, -1);
					if (selectedPosition >= 0 && selectedPosition < PUBLISH_TYPES.length) {
						publish_type = PUBLISH_TYPES[selectedPosition];
						tv_gethelp_publish_type.setText(publish_type);
						showShortToast(publish_type);
					}
					break;
				case REQUEST_MONEY:
					publish_money = StringUtil.getTrimedString(data.getStringExtra(EditTextInfoWindow.RESULT_VALUE));
					tv_gethelp_publish_money.setText(publish_money);
					showShortToast(publish_money);
					break;
				case REQUEST_TIME:
					ArrayList<Integer> list = data.getIntegerArrayListExtra(TimePickerWindow.RESULT_TIME_DETAIL_LIST);
					if (list != null && list.size() >= 2) {

						selectedTime = new int[list.size()];
						for (int i = 0; i < list.size(); i++) {
							selectedTime[i] = list.get(i);
						}

						String minute = "" + selectedTime[1];
						if (minute.length() < 2) minute = "0" + minute;
						publish_time = selectedTime[0] + ":" + minute;
						tv_gethelp_publish_time.setText(publish_time);
						showShortToast("选择的时间为" + selectedTime[0] + ":" + minute);
					}
					break;

			}
		}
	}
	//-//<Activity>---------------------------------------------------------------------------------

	//</Event事件区>---只要存在事件监听代码就是---------------------------------------------------------





	//<内部类>---尽量少用-----------------------------------------------------------------------------
	private class PostData extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			return OkhttpManager.get(params[0]);
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
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

			}
		}
	}

	//</内部类>---尽量少用----------------------------------------------------------------------------

}