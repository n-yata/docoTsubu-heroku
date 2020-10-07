package model;

import java.io.Serializable;

/**
 * つぶやきに関する情報
 * @author yata1
 *
 */
public class Mutter implements Serializable{
    /** ユーザー名 */
    private String userName;
    /** つぶやき内容 */
    private String text;

    /**
     * コンストラクタ
     */
    public Mutter() {}
    public Mutter(String userName, String text) {
        this.userName = userName;
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }
    public String getText() {
        return text;
    }
}
