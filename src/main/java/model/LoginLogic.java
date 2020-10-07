package model;

/**
 * ログイン情報の取得
 * @author yata1
 */
public class LoginLogic {
    public boolean execute(User user) {
        if(user.getPass().equals("1234")) {
            return true;
        }
        return false;
    }
}
