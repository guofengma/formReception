package web.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import web.common.LoginData;
import web.common.SessionKey;
import web.utils.UHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qaa on 2017/10/24.
 * @author zhao qing
 */
@Service("OnLoginService")
public class OnLoginService {
    ////在用户登录时使用登录凭证 code 获取 session_key 和 openid
    public String onLogin(String code) throws Exception{
        String baseUrl = "https://api.weixin.qq.com/sns/jscode2session";
        String appId = "wx6d6c2249b3f3c7c2";
        String secret = "275b6eb69257fe3e909e1a5d6bbc5c5e";
        String jsCode = code;

        Map<String,String> params = new HashMap<String, String>();
        params.put("appid",appId);
        params.put("secret",secret);
        params.put("js_code",jsCode);
        params.put("grant_type","authorization_code");

        UHttpClient.Res res = UHttpClient.doRequest(UHttpClient.Method.get,baseUrl,params,"UTF-8",true);

        //解析openid、session_key
        JSONObject jsonObject = new JSONObject(res.content);
        String openId = (String) jsonObject.get("openid");
        String sessionKey = (String) jsonObject.get("session_key");
        Integer expireIn = (Integer) jsonObject.get("expires_in");
        LoginData loginData = new LoginData(openId,sessionKey,expireIn);

        String localSessionKey =  DigestUtils.md5Hex(appId + openId);//生成自己的sessionkey
        SessionKey.SessionsMap.put(localSessionKey,loginData);//存入全局map

        return localSessionKey;
    }
}
