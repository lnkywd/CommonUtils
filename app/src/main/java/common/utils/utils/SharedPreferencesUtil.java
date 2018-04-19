package common.utils.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    /**
     * Title: clearSharedPreferencesInfo<br>
     * Description: 删除指定sp中的数据<br>
     *
     * @param context
     * @param itemName
     * @author yanghao
     * @Modified by
     * @CreateDate 2015年8月9日 下午6:08:08
     * @link yanghao@iyangpin.com
     * @Version
     * @since JDK 1.7
     */
    public static void clearSharedPreferencesInfo(Context context, String itemName) {
        SharedPreferences sp = context.getSharedPreferences(itemName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear().commit();
    }


    public static void putFirstOpen(Context context) {
        SharedPreferences sp = context.getSharedPreferences("appFlag", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("isOpen", 201).commit();
    }

    public static int getFirstOpen(Context context) {
        SharedPreferences sp = context.getSharedPreferences("appFlag", Context.MODE_PRIVATE);
        return sp.getInt("isOpen", 0);
    }


    /**
     * 保存用户的登录方式
     */
    public static void setLoginWay(Context context, String loginWay) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("loginWay", loginWay).commit();
    }

    /**
     * 获取用户的登录方式
     */
    public static String getLoginWay(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("loginWay", "");
    }

    /**
     * 保存用户的id
     */
    public static void setUid(Context context, String uid) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("uid", uid).commit();
    }

    /**
     * 保存用户的个性签名
     */
    public static void setSignature(Context context, String uid) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Signature", uid).commit();
    }

    /**
     * 得到用户的个性签名
     */
    public static String getSignature(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("Signature", "");
    }

    /**
     * 获取用户的id
     */
    public static String getUid(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("uid", "");
    }

    /**
     * 保存用户的step
     */
    public static void setStep(Context context, String uid) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("step", uid).commit();
    }

    /**
     * 获取用户的Step
     */
    public static String getStep(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("step", "1");
    }

    /**
     * 保存用户的手机号
     */
    public static void setMobile(Context context, String mobile) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("mobile", mobile).commit();
    }

    /**
     * 获取用户的手机号
     */
    public static String getMobile(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("mobile", "");
    }


    /**
     * 保存用户的备注姓名
     */
    public static void setUserNickName(Context context, String usernickname) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("usernickname", usernickname).commit();
    }

    /**
     * 获取用户的备注姓名
     */
    public static String getUserNickName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("usernickname", "");
    }

    /**
     * 保存用户的昵称
     */
    public static void setNickname(Context context, String nickname) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nickname", nickname).commit();
    }

    /**
     * 获取露脸的 sessionid
     */
    public static String getSessionid(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("sessionid", "");
    }


    /**
     * 保存露脸sessionid
     */
    public static void setSessionid(Context context, String sessionid) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sessionid", sessionid).commit();
    }

    /**
     * 获取用户的昵称
     */
    public static String getNickname(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("nickname", "");
    }

    /**
     * 保存用户的头像
     */
    public static void setAvatar(Context context, String avatar) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("avatar", avatar).commit();
    }

    /**
     * 获取用户的头像
     */
    public static String getAvatar(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("avatar", "");
    }

    /**
     * 保存用户的生日
     */
    public static void setBirthday(Context context, String birthday) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("birthday", birthday).commit();
    }

    /**
     * 获取用户的生日
     */
    public static String getBirthday(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("birthday", "");
    }

    /**
     * 保存用户的nianling
     */
    public static void setAge(Context context, String age) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("age", age).commit();
    }

    /**
     * 获取用户的生日
     */
    public static String getAge(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("age", "");
    }


    /**
     * 保存用户的性别
     */
    public static void setSex(Context context, String sex) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sex", sex).commit();
    }

    /**
     * 获取用户的性别
     */
    public static String getSex(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("sex", "");
    }

    /**
     * 保存用户的token
     */
    public static void setToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token).commit();
    }


    /**
     * 获取用户的token
     */
    public static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("token", "");
    }


    /**
     * 保存用户的密码
     */
    public static void setPassword(Context context, String pwd) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password", pwd).commit();
    }

    /**
     * 获取用户的密码
     */
    public static String getPassword(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("password", "");
    }

    /**
     * 保存invitecode
     */
    public static void setInvitecode(Context context, String pwd) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("invitecode", pwd).commit();
    }

    /**
     * 获取invitecode
     */
    public static String getInvitecode(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("invitecode", "");
    }

    /**
     * inviteopen
     */
    public static void setInviteopen(Context context, String pwd) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("inviteopen", pwd).commit();
    }

    /**
     * 获取inviteopen
     */
    public static String getInviteopen(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("inviteopen", "");
    }

    /**
     * 保存用户Baidu推送ID
     */
    public static void setXGPushID(Context context, String baiduPushID) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("baiduPushID", baiduPushID).commit();
    }

    /**
     * 获取用户Baidu推送ID
     */
    public static String getXGPushID(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("baiduPushID", "");
    }

    /**
     * 保存区ID
     */
    public static void setCommunityId(Context context, String community_id) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("community_id", community_id).commit();
    }

    /**
     * 获取区ID
     */
    public static String getCommunityId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("community_id", "");
    }

    /**
     * 保存区Name
     */
    public static void setCommunityName(Context context, String communityName) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("communityName", communityName).commit();
    }

    /**
     * 获取区Name
     */
    public static String getCommunityName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("communityName", "");
    }

    /**
     * 保存城市ID
     */
    public static void setCityId(Context context, String city_id) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("city_id", city_id).commit();
    }

    /**
     * 获取城市ID
     */
    public static String getCityId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("city_id", "");
    }

    /**
     * 保存日期
     */
    public static void setTimes(Context context, String city_id) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("current_time", city_id).commit();
    }

    /**
     * 获取日期
     */
    public static String getTimes(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("current_time", "");
    }

    /**
     * 保存城市Name
     */
    public static void setCityName(Context context, String cityName) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("cityName", cityName).commit();
    }

    /**
     * 获取城市Name
     */
    public static String getCityName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("cityName", "");
    }

    /**
     * 保存省Name
     */
    public static void setProvinceName(Context context, String provinceName) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("provinceName", provinceName).commit();
    }

    /**
     * 获取省Name
     */
    public static String getProvinceName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("provinceName", "");
    }

    /**
     * 保存省id
     */
    public static void setProvinceId(Context context, String provinceName) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("provinceid", provinceName).commit();
    }

    /**
     * 获取省id
     */
    public static String getProvinceId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("provinceid", "");
    }

    /**
     * 保存用户所在的纬度
     */
    public static void setLatitude(Context context, String lat) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("lat", lat).commit();
    }

    /**
     * 获取用户所在的纬度
     */
    public static String getLatitude(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("lat", "");
    }

    /**
     * 保存用户所在的经度
     */
    public static void setLongitude(Context context, String lng) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("lng", lng).commit();
    }

    /**
     * 获取用户所在的经度
     */
    public static String getLongitude(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("lng", "");
    }

    /**
     * 保存用户所在的地址
     */
    public static void setAddress(Context context, String address) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("address", address).commit();
    }

    /**
     * 获取用户所在的地址
     */
    public static String getAddress(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("address", "");
    }

    /**
     * 保存用户屏幕的高度
     */
    public static void setPictureProportionHeight(Context context, String proportionHeight) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("proportionHeight", proportionHeight).commit();
    }

    /**
     * 获取用户屏幕的高度
     */
    public static String getPictureProportionHeight(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("proportionHeight", "");
    }

    /**
     * 保存用户是否第一次启动
     */
    public static void setIsFristStart(Context context, boolean booleanValue) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isFristStart", booleanValue).commit();
    }

    /**
     * 获取用户是否第一次启动
     */
    public static Boolean getIsFristStart(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getBoolean("isFristStart", true);
    }

    /**
     * 保存是否接收推送
     */
    public static void setReceivePush(Context context, String isReceivePush) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("isReceivePush", isReceivePush).commit();
    }

    /**
     * 获取是否接收推送
     */
    public static String getReceivePush(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("isReceivePush", "true");
    }


    /**
     * 保存主键ID
     */
    public static void setId(Context context, String id) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", id).commit();
    }

    /**
     * 获取主键ID
     */
    public static String getId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("id", "");
    }

    /**
     * 保存省市区
     */
    public static void setAllCommunity(Context context, String allCommunity) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("allCommunity", allCommunity).commit();
    }

    /**
     * 获取省市区
     */
    public static String getAllCommunity(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return "";
    }

    //A计划

    /**
     * 更新学校信息
     *
     * @param context
     */
    public static void updateSchoolInfo(Context context, String schoolId, String schoolName,
                                        String gradeName, String gradeId, String className, String classId) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("schoolId", schoolId);
        editor.putString("schoolName", schoolName);
        editor.putString("gradeId", gradeId);
        editor.putString("gradeName", gradeName);
        editor.putString("className", className);
        editor.putString("classId", classId);
        editor.commit();
    }

    /**
     * 保存学校id
     *
     * @param context
     * @param schoolId
     */
    public static void setSchoolId(Context context, String schoolId) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("schoolId", schoolId).commit();
    }

    /**
     * 获取学校id
     *
     * @param context
     * @return
     */
    public static String getSchoolId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("schoolId", "");
    }

    public static void setSchoolName(Context context, String schoolId) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("schoolName", schoolId).commit();
    }

    public static String getSchoolName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("schoolName", "");
    }

    public static void setGradeId(Context context, String grade) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("gradeId", grade).commit();
    }

    public static String getGradeId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("gradeId", "");
    }

    public static void setGradeName(Context context, String grade) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("gradeName", grade).commit();
    }

    public static String getGradeName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("gradeName", "");
    }

    public static void setClassName(Context context, String grade) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("className", grade).commit();
    }

    public static String getClassName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("className", "");
    }

    public static void setClassId(Context context, String grade) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("classId", grade).commit();
    }

    public static String getClassId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sp.getString("classId", "");
    }

    /**
     * 保存易货搜索记录
     */
    public static void saveFreeSearchHistory(Context context, String json, int type) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("free_search_json_" + type, json).apply();
    }

    /**
     * 读取易货搜索记录
     */
    public static String getFreeSearchHistory(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("System", Context.MODE_PRIVATE);
        return sp.getString("free_search_json_" + type, "");
    }

}
