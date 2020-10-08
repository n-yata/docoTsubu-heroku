package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;
import util.Database;

public class MutterDAO {

    public List<Mutter> findAll(){
        Connection conn = null;
        List<Mutter> mutterList = new ArrayList<Mutter>();
        try {
            // データベースへ接続
            conn = Database.getConnection();
            //SELECT文の準備
            String sql = "SELECT ID, NAME, TEXT FROM MUTTER ORDER BY ID DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //SELECT文を実行
            ResultSet rs = pstmt.executeQuery();
            //SELECT文の結果をArrayListに格納
            while(rs.next()) {
                int id = rs.getInt("ID");
                String userName = rs.getString("NAME");
                String text = rs.getString("TEXT");
                Mutter mutter = new Mutter(id, userName, text);
                mutterList.add(mutter);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            //データベース切断
            if(conn != null) {
                try {
                    conn.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return mutterList;
    }

    public boolean create(Mutter mutter) {
        Connection conn = null;
        try {
            //データベースへ接続
            conn = Database.getConnection();
            //INSERT文の準備(idは自動連番なので指定しなくて良い)
            String sql = "INSERT INTO MUTTER(NAME, TEXT) VALUES(?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //INSERT文中の「？」に使用する値を設定しSQLを完成
            pstmt.setString(1, mutter.getUserName());
            pstmt.setString(2, mutter.getText());
            //INSERT文を実行
            int result = pstmt.executeUpdate();

            if(result != 1) {
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            //データベース切断
            if(conn != null) {
                try {
                    conn.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
