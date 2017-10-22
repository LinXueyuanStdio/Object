package pub.object.bone.model;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.bone.model.
 * @date 17-8-3
 * @discription
 */
public class Help {
    @SuppressWarnings("unused")
    private static final String TAG = "Help";
    String help_owner ;
    String help_place;
    String help_type;//加急?加急:普通
    String help_money ;
    String help_message ;
    String help_time;
    String help_gethelpers;//可为空

    public static String getTAG() {
        return TAG;
    }
    public String getHelp_owner() {
        return help_owner;
    }
    public void setHelp_owner(String help_owner) {
        this.help_owner = help_owner;
    }
    public String getHelp_place() {
        return help_place;
    }
    public void setHelp_place(String help_place) {
        this.help_place = help_place;
    }
    public String getHelp_type() {
        return help_type;
    }
    public void setHelp_type(String help_type) {
        this.help_type = help_type;
    }
    public String getHelp_money() {
        return help_money;
    }
    public void setHelp_money(String help_money) {
        this.help_money = help_money;
    }
    public String getHelp_message() {
        return help_message;
    }
    public void setHelp_message(String help_message) {
        this.help_message = help_message;
    }
    public String getHelp_time() {
        return help_time;
    }
    public void setHelp_time(String help_time) {
        this.help_time = help_time;
    }
    public String getHelp_gethelpers() {
        return help_gethelpers;
    }
    public void setHelp_gethelpers(String help_gethelpers) {
        this.help_gethelpers = help_gethelpers;
    }
}
