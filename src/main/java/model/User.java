package model;

import java.io.Serializable;

/**
 * ユーザーに関する情報
 * @author yata1
 *
 */
public class User implements Serializable{
        /** ユーザー名 */
        private String name;
        /** パスワード */
        private String pass;

        /**
         * コンストラクタ
         */
        public User() {}
        public User(String name, String pass) {
            this.name = name;
            this.pass = pass;
        }

        public String getName() {
            return name;
        }

        public String getPass() {
            return pass;
        }
}
