package web.common;

/**
 * Created by qaa on 2017/10/24.
 * 使用小程序的用户登录时，获取其相关信息
 */
public class LoginData {
    private String openId;//openid，用户唯一标识
    private String sessionKey;//session_key
    private String expires_in;//生效时间

    public LoginData(String openId, String sessionKey, String expires_in) {
        this.openId = openId;
        this.sessionKey = sessionKey;
        this.expires_in = expires_in;
    }
}
