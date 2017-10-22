package pub.object.bone.order;

import pub.xylibrary.bone.order.Settings;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.bone.order.
 * @date 17-7-19
 * @discription 公共资源类
 */
public class Constant extends Settings{
    @SuppressWarnings("unused")
    private static final String TAG = "Constant";


    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页 ： 数字，大于0
     *
     * 每日数据： http://gank.io/api/day/年/月/日
     * 例：http://gank.io/api/day/2015/08/06
     */
    public static String URL_SERVER_ADDRESS_NORMAL_HTTP = "http://gank.io/api/";
    public static String URL_REQUEST_FOR_DATA = URL_SERVER_ADDRESS_NORMAL_HTTP + "data/";//"http://gank.io/api/data/"

    public static String URL_REQUEST_FOR_DATA_CONTACT = URL_REQUEST_FOR_DATA + "福利/";
    public static String URL_REQUEST_FOR_DATA_HELP    = URL_REQUEST_FOR_DATA + "福利/";
    public static String URL_REQUEST_FOR_DATA_GETHELP = URL_REQUEST_FOR_DATA + "福利/";
    public static String URL_REQUEST_FOR_DATA_HISTORY = URL_REQUEST_FOR_DATA + "福利/";

    public static String URL_SERVER_ADDRESS_TEST = "http://localhost:8000/";

    public static String URL_POST_DATA_USER_REGISTER = URL_SERVER_ADDRESS_TEST + "register/";
    public static String URL_POST_DATA_USER_LOGIN;

    public static String URL_POST_DATA_HELP;
    public static String URL_POST_DATA_GETHELP;

    //每次请求大小
    public static final int PAGE_SIZE = 10;
    //更新数据的状态 0:正常加载、下拉刷新   1: 加载更多
    public static final int GET_DATA_TYPE_NOMAL = 0;
    public static final int GET_DATA_TYPE_LOADMORE = 1;

}
