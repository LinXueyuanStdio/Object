package pub.object.bone.model;

import pub.object.MainApplication;

/**
 * @PackageName pub.object.bone.model.
 * @author dlinking-lxy
 * @date 17-7-5
 * @discription 本地用户类
 */
public class User extends pub.object.system.NetSystem.model.User{
    private static final long serialVersionUID = -8778034378026642371L;





    public User() {
        super();
    }
    public User(long id) {
        super(id);
    }

    @Override
    public Long getId() {
        return value(super.getId());
    }
    @Override
    public Long getDate() {
        return value(super.getDate());
    }
    @Override
    public Integer getSex() {
        return value(super.getSex());
    }



    /**判断是否为当前用户的朋友
     * @return
     */
    public boolean isFriend() {
        return isFriend(MainApplication.getInstance().getCurrentUserId());
    }
    /**判断是否为朋友，双方friendIdList都必须包含对方id
     * @param user
     * @return
     */
    public boolean isFriend(User user) {
        return isFriend(this, user);
    }
    /**判断是否为朋友，双方friendIdList都必须包含对方id
     * @param user0
     * @param user1
     * @return
     */
    public static boolean isFriend(User user0, User user1) {
        return user0 != null && user1 != null && isFriend(user0, user1.getId()) && isFriend(user1, user0.getId());
    }
    /**判断是否为当前用户的朋友，仅从单方的friendIdList判断
     * @param id
     * @return
     */
    public boolean isFriend(long id) {
        return isFriend(this, id);
    }
    /**判断是否为朋友，仅从单方的friendIdList判断
     * @param user0
     * @param id1
     * @return
     */
    public static boolean isFriend(User user0, long id1) {
        //id
        if (id1 <= 0) {
            return false;
        }
        long id0 = user0 == null ? 0 : user0.getId();
        if (id0 <= 0) {
            return false;
        }

        //friendIdList
        return isContain(user0.getFriendIdList(), id1);
    }




}
