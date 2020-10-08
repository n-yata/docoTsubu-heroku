package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DeleteMutterLogic;
import model.GetMutterListLogic;
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

        // つぶやきリストを取得
        GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
        List<Mutter> mutterList = getMutterListLogic.execute();

        // ログインしているか確認するためセッションスコープからユーザー情報を取得
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // ログイン情報の確認
        boolean isLogin;
        if (loginUser == null) {
            isLogin = false;
        } else {
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
        String operate = reqMap.get("operate");

        List<Mutter> resMutterList = new ArrayList<>();
        String resErroMsg = "";

        if (operate == "create" ) {
            // 入力値チェック
            if (text != null && text.length() != 0) {
                // セッションスコープに保存されたユーザー情報を取得
                HttpSession session = request.getSession();
                User loginUser = (User) session.getAttribute("loginUser");

                // つぶやきリストに追加
                Mutter mutter = new Mutter(loginUser.getName(), text);
                PostMutterLogic postMutterLogic = new PostMutterLogic();
                postMutterLogic.execute(mutter);

            } else {
                // エラーメッセージをJSONに変換して送信
                String errorMsg = "つぶやきが入力されていません";

                // レスポンス用変数に格納
                resErroMsg = errorMsg;
            }
        }

        if (operate == "delete" ) {
            // つぶやきリストを削除
            DeleteMutterLogic deleteMutterLogic = new DeleteMutterLogic();
            deleteMutterLogic.execute();
        }

        // つぶやきリストを取得
        GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
        List<Mutter> mutterList = getMutterListLogic.execute();

        // レスポンス用変数に格納
        resMutterList = mutterList;

        // 戻り値用のオブジェクト作成
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("mutterList", resMutterList);
        resMap.put("errorMsg", resErroMsg);

        // JSONを戻す
        JsonConvertUtil.convertToJson(resMap, response);

    }

}
