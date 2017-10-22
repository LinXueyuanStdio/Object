package pub.object.bone.Manager;

import android.content.Context;
import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.surface.fragment.
 * @date 17-7-19
 * @discription
 */
public class HandlerManager extends Handler {

    private static HandlerManager instance = null;
    WeakReference<Context> mActivityReference;

    public static HandlerManager getInstance(Context context) {
        if (instance == null) {
            instance = new HandlerManager(context.getApplicationContext());
        }
        return instance;
    }

    HandlerManager(Context context) {
        mActivityReference = new WeakReference<>(context);
    }
}
