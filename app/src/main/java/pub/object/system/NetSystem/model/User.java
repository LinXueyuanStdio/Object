package pub.object.system.NetSystem.model;

import java.util.List;

import pub.xylibrary.system.netSystem.APIJSONRequest;
import pub.xylibrary.system.netSystem.BaseModel;
import pub.xylibrary.system.netSystem.RequestMethod;

/**
 * @PackageName pub.object.system.serverSystem.model.
 * @author dlinking-lxy
 * @date 17-7-5.
 * @discription 用户类
 * @see
 * <br >POST:post/register/user<pre>
{
"User":{
"disallow":"id",
"necessary":"name,phone"
},
"necessary":"loginPassword,verify"
}
 * </pre>
 * <br >PUT:<pre>
{
"User":{
"disallow":"phone",
"necessary":"id"
}
}
 * </pre>
 * <br >PUT(User.phone):put/user/phone<pre>
{
"User":{
"disallow":"!",
"necessary":"id,phone"
},
"necessary":"loginPassword,verify"
}
 * </pre>
 */
@APIJSONRequest(
        method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.DELETE},
        POST = "{\"User\": {\"disallow\": \"id\", \"necessary\": \"name,phone\"}, \"necessary\": \"loginPassword,verify\"}",
        PUT = "{\"disallow\": \"phone\", \"necessary\": \"id\"}"
)
public class User extends BaseModel {
    private static final long serialVersionUID = -1635551656020732611L;

    public static final int SEX_MAIL = 0;
    public static final int SEX_FEMALE = 1;
    public static final int SEX_UNKNOWN = 2;


    private Integer sex; //性别
    private String head; //头像url
    private String name; //姓名
    private String phone; //手机

    private String username;
    private String password;
    private String realname;//名称
    private String schoolnumber;
    private String alipaynumber;
    private String userclass;


    private String tag; //标签
    private List<String> pictureList; //照片列表
    private List<Long> friendIdList; //朋友列表

    /**默认构造方法，JSON等解析时必须要有
     */
    public User() {
        super();
    }
    public User(long id) {
        this();
        setId(id);
    }

    public Integer getSex() {
        return sex;
    }
    public User setSex(Integer sex) {
        this.sex = sex;
        return this;
    }
    public String getHead() {
        return head;
    }
    public User setHead(String head) {
        this.head = head;
        return this;
    }
    public String getName() {
        return name;
    }
    public User setName(String name) {
        this.name = name;
        return this;
    }
    public String getPhone() {
        return phone;
    }
    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public List<String> getPictureList() {
        return pictureList;
    }
    public User setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
        return this;
    }

    public String getTag() {
        return tag;
    }
    public User setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public List<Long> getFriendIdList() {
        return friendIdList;
    }
    public User setFriendIdList(List<Long> friendIdList) {
        this.friendIdList = friendIdList;
        return this;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRealname() {
        return realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getSchoolnumber() {
        return schoolnumber;
    }
    public void setSchoolnumber(String schoolnumber) {
        this.schoolnumber = schoolnumber;
    }
    public String getAlipaynumber() {
        return alipaynumber;
    }
    public void setAlipaynumber(String alipaynumber) {
        this.alipaynumber = alipaynumber;
    }
    public String getUserclass() {
        return userclass;
    }
    public void setUserclass(String userclass) {
        this.userclass = userclass;
    }
}
