package pub.object.bone.model;

/**
 * @author dlinking-lxy
 * @PackageName: pub.object.bone.model.
 * @date 17-7-20
 * @discription 妹子
 */
public class Meizi {
    @SuppressWarnings("unused")
    private static final String TAG = "Meizi";

    private String url;//图片地址
    private int page;//页数
    private int id;
    private String name;
    private String time;
    private String type;
    private String typeRed;
    private String pay;
    private String payRed;
    private String sex;
    private String phone;

    private String _id;
    private String who;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTypeRed() {
        return typeRed;
    }
    public void setTypeRed(String typeRed) {
        this.typeRed = typeRed;
    }
    public String getPay() {
        return pay;
    }
    public void setPay(String pay) {
        this.pay = pay;
    }
    public String getPayRed() {
        return payRed;
    }
    public void setPayRed(String payRed) {
        this.payRed = payRed;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
}
