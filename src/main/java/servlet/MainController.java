package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Mutter;
import model.PostMutterLogic;
import model.User;
import util.JsonConvertUtil;

/**
 * Servlet implementation class Main
 */
@WebServlet("/MainController")
public class MainController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // つぶやきリストをアプリケーションスコープから取得
        ServletContext application = this.getServletContext();
        List<Mutter> mutterList = (List<Mutter>) application.getAttribute("mutterList");

        // 取得できなかった場合は、つぶやきリストを新規作成してアプリケーションスコープに保存
        if(mutterList == null) {
            mutterList = new ArrayList<Mutter>();
            application.setAttribute("mutterList", mutterList);
        }

        // ログインしているか確認するためセッションスコープからユーザー情報を取得
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");


        // ログイン情報の確認
        boolean isLogin;
        if(loginUser == null) {
            isLogin = false;
        }else{
            isLogin = true;
        }

        // 戻り値用のオブジェクト作成
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("isLogin", isLogin);
        resMap.put("mutterList", mutterList);

        // JSONを戻す
        JsonConvertUtil.convertToJson(resMap, response);

    }

    /**
     *
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 送信されたJSONの取得
        Map<String, String> reqMap = JsonConvertUtil.convertToObject(request);

        String text = reqMap.get("text");

        List<Mutter> resMutterList = new ArrayList<>();
        String resErroMsg = "";

        // 入力値チェック
        if(text != null && text.length() != 0) {
            // アプリケーションスコープに保存されたつぶやきリストを取得
            ServletContext application = this.getServletContext();
            List<Mutter> mutterList = (List<Mutter>) application.getAttribute("mutterList");

            // セッションスコープに保存されたユーザー情報を取得
            HttpSession session = request.getSession();
            User loginUser = (User) session.getAttribute("loginUser");

            // つぶやきリストに追加
            Mutter mutter = new Mutter(loginUser.getName(), text);
            PostMutterLogic postMutterLogic = new PostMutterLogic();
            postMutterLogic.execute(mutter, mutterList);

            // アプリケーションスコープにつぶやきリストを保存
            application.setAttribute("mutterList", mutterList);

            // レスポンス用変数に格納
            resMutterList = mutterList;

        }else {
            // エラーメッセージをJSONに変換して送信
            String errorMsg = "つぶやきが入力されていません";

            // レスポンス用変数に格納
            resErroMsg = errorMsg;
        }

        // 戻り値用のオブジェクト作成
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("mutterList", resMutterList);
        resMap.put("errorMsg", resErroMsg);

        // JSONを戻す
        JsonConvertUtil.convertToJson(resMap, response);

    }

}
