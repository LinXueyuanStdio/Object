package pub.object.bone.model;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.bone.model.
 * @date 17-8-3
 * @discription
 */
public class GetHelp {
    @SuppressWarnings("unused")
    private static final String TAG = "GetHelp";

    String gethelp_owner ;
    String gethelp_place;
    String gethelp_type;//拿快递．．．
    String gethelp_money ;
    String gethelp_message ;
    String gethelp_time;
    String gethelp_gethelpers;//可为空

    public static String getTAG() {
        return TAG;
    }
    public String getGethelp_owner() {
        return gethelp_owner;
    }
    public void setGethelp_owner(String gethelp_owner) {
        this.gethelp_owner = gethelp_owner;
    }
    public String getGethelp_place() {
        return gethelp_place;
    }
    public void setGethelp_place(String gethelp_place) {
        this.gethelp_place = gethelp_place;
    }
    public String getGethelp_type() {
        return gethelp_type;
    }
    public void setGethelp_type(String gethelp_type) {
        this.gethelp_type = gethelp_type;
    }
    public String getGethelp_money() {
        return gethelp_money;
    }
    public void setGethelp_money(String gethelp_money) {
        this.gethelp_money = gethelp_money;
    }
    public String getGethelp_message() {
        return gethelp_message;
    }
    public void setGethelp_message(String gethelp_message) {
        this.gethelp_message = gethelp_message;
    }
    public String getGethelp_time() {
        return gethelp_time;
    }
    public void setGethelp_time(String gethelp_time) {
        this.gethelp_time = gethelp_time;
    }
    public String getGethelp_gethelpers() {
        return gethelp_gethelpers;
    }
    public void setGethelp_gethelpers(String gethelp_gethelpers) {
        this.gethelp_gethelpers = gethelp_gethelpers;
    }
}
