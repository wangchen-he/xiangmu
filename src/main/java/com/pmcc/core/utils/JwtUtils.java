package com.pmcc.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: JwtUtils <br>
 * @Description: JWT生成类，创建TOKEN
 *
 * 同一用户每次获取token，获取到的都是同一个token，只有token失效后才会获取新token。
 * 同一用户每次获取token都生成一个完成周期的token并且保证每次生成的token都能够使用（多点登录）。
 * 同一用户每次获取token都保证只有最后一个token能够使用，之前的token都设为无效（单点token）
 *
 * @Date: 2019/12/9 20:45 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class JwtUtils {

    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    //签发人
    private static final String SUBJECT = "darkBF";
    //过期时间
    private static long EXPIRE = 1000 * 60 * 30; //设置30分钟后过期
    //密钥
    //正式开发时可以调用该方法实时生成加密的salt
    // APPSECRET = BCrypt.gensalt();
    private static final String APPSECRET = "xd666";

    /**
     * token的创建
     * @param user
     * @return java.lang.String
     */
    public static String createToken(UserDetails user) {
        if (user == null || user.getUsername() == null) {
            return null;
        } else {
            try {
                //过期时间
                Date expiration_time = new Date(System.currentTimeMillis() + EXPIRE);  //设置1小时后过期
                //设置密钥
                Algorithm algorithm = Algorithm.HMAC256(APPSECRET);
                // 设置头部信息
                Map<String, Object> headerMap = new HashMap<>(2);
                headerMap.put("type", "JWT");
                headerMap.put("alg", "HS256");
                // 返回token字符串
                return JWT.create()
                        .withHeader(headerMap)
                        .withIssuer(SUBJECT)                                 //签发人（验证密钥）
                        .withIssuedAt(new Date())                            //签发时间
                        .withClaim("name", user.getUsername())         //用户名
//                        .withClaim("pass", user.getPassword())             //用户名
                        .withExpiresAt(expiration_time)                      //过期时间
                        .sign(algorithm);
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 解析token字符串
     * @param token
     * @return io.jsonwebtoken.Claims
     */
    public static String parsingToken(String token) {
        DecodedJWT decodedJWT=null;
        try {
            // 使用了HMAC256加密算法。
            // APPSECRET是用来加密数字签名的密钥。
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(APPSECRET))
                    .withIssuer(SUBJECT)
                    .build();
            decodedJWT = verifier.verify(token);
            logger.info("超时时间:"+decodedJWT.getExpiresAt());
            logger.info("载体信息:"+decodedJWT.getClaim("name").asString());
            logger.info("算法:"+decodedJWT.getAlgorithm());
        }catch (Exception e){
            //解码异常则抛出异常
            logger.error(e.getMessage());
            return null;
        }
        return decodedJWT.getClaim("name").asString();
    }


    /**
     * 先验证token是否被伪造，然后解码token。
     * @param token 字符串token
     * @return 解密后的DecodedJWT对象，可以读取token中的数据。
     */
    public static DecodedJWT deToken(String token) {
        DecodedJWT jwt = null;
        try {
            // 使用了HMAC256加密算法。
            // APPSECRET是用来加密数字签名的密钥。
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(APPSECRET))
                    .withIssuer(SUBJECT)
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jwt;
    }

    /**
     * 检验token是否正确语句
     */
    public static boolean verify(String token){
        DecodedJWT jwt = null;
        try {
            // 使用了HMAC256加密算法。
            // APPSECRET是用来加密数字签名的密钥。
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(APPSECRET))
                    .withIssuer(SUBJECT)
                    .build(); //Reusable verifier instance
            // 对字段进行校验，超时抛出JWTVerificationException类的子类InvalidClaimException
            jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取token中的参数值
     * @param token
     * @return
     */
    public static String getUserName(String token){
        try{
            DecodedJWT jwt = deToken(token);
            return jwt.getClaim("name").asString();
        }catch (Exception e){
            return null;
        }

    }
}
