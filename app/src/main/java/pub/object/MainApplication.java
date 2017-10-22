package pub.object;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.util.Log;

import pub.object.bone.model.User;
import pub.object.system.DataSystem.manager.DataManager;
import pub.object.system.ThemeSystem.manager.ThemeManager;
import pub.object.system.ThemeSystem.utils.ThemeUtils;
import pub.xylibrary.bone.base.BaseApplication;
import pub.xylibrary.bone.util.StringUtil;

/**
 * @PackageName pub.object.
 * @author dlinking-lxy
 * @date 17-7-5.
 * @discription 主应用类
 */
public class MainApplication extends BaseApplication implements ThemeUtils.switchColor{
    private static final String TAG = "MainApplication";

    private static MainApplication context;
    public static MainApplication getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ThemeUtils.setSwitchColor(this);
    }





    //<user>----------------------------------------------------------------------------------------
    /**获取当前用户id
     * @return
     */
    public long getCurrentUserId() {
        currentUser = getCurrentUser();
        Log.d(TAG, "getCurrentUserId  currentUserId = " + (currentUser == null ? "null" : currentUser.getId()));
        return currentUser == null ? 0 : currentUser.getId();
    }

    /**获取当前用户phone
     * @return
     */
    public String getCurrentUserPhone() {
        currentUser = getCurrentUser();
        return currentUser == null ? null : currentUser.getPhone();
    }

    private static User currentUser = null;
    public User getCurrentUser() {
        if (currentUser == null) {
            currentUser = DataManager.getInstance().getCurrentUser();
        }
        return currentUser;
    }
    public void saveCurrentUser(User user) {
        if (user == null) {
            Log.e(TAG, "saveCurrentUser  currentUser == null >> return;");
            return;
        }
        if (user.getId() <= 0 && StringUtil.isNotEmpty(user.getName(), true) == false) {
            Log.e(TAG, "saveCurrentUser  user.getId() <= 0" +
                    " && StringUtil.isNotEmpty(user.getName(), true) == false >> return;");
            return;
        }

        currentUser = user;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }

    /**判断是否为当前用户
     * @param userId
     * @return
     */
    public boolean isCurrentUser(long userId) {
        return DataManager.getInstance().isCurrentUser(userId);
    }
    //</user>---------------------------------------------------------------------------------------





    //<login and logout>----------------------------------------------------------------------------
    public boolean isLoggedIn() {
        return getCurrentUserId() > 0;
    }
    public void logout() {
        currentUser = null;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }
    //</login and logout>---------------------------------------------------------------------------





    //<theme>---------------------------------------------------------------------------------------
    private String getTheme(Context context) {
        if (ThemeManager.getTheme(context) == ThemeManager.CARD_STORM) {
            return "blue";
        } else if (ThemeManager.getTheme(context) == ThemeManager.CARD_HOPE) {
            return "purple";
        } else if (ThemeManager.getTheme(context) == ThemeManager.CARD_WOOD) {
            return "green";
        } else if (ThemeManager.getTheme(context) == ThemeManager.CARD_LIGHT) {
            return "green_light";
        } else if (ThemeManager.getTheme(context) == ThemeManager.CARD_THUNDER) {
            return "yellow";
        } else if (ThemeManager.getTheme(context) == ThemeManager.CARD_SAND) {
            return "orange";
        } else if (ThemeManager.getTheme(context) == ThemeManager.CARD_FIREY) {
            return "red";
        }
        return null;
    }
    
    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        if (ThemeManager.isDefaultTheme(context)) {
            return context.getResources().getColor(colorId);
        }
        String theme = getTheme(context);
        if (theme != null) {
            colorId = getThemeColorId(context, colorId, theme);
        }
        return context.getResources().getColor(colorId);
    }

    @Override
    public int replaceColor(Context context, @ColorInt int originColor) {
        if (ThemeManager.isDefaultTheme(context)) {
            return originColor;
        }
        String theme = getTheme(context);
        int colorId = -1;
        if (theme != null) {
            colorId = getThemeColor(context, originColor, theme);
        }
        return colorId != -1 ? getResources().getColor(colorId) : originColor;
    }

    private
    @ColorRes
    int getThemeColorId(Context context, int colorId, String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case R.color.playbarProgressColor:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return colorId;
    }

    private
    @ColorRes
    int getThemeColor(Context context, int color, String theme) {
        switch (color) {
            case 0xd20000:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
        }
        return -1;
    }
    //</theme>--------------------------------------------------------------------------------------
}
