package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LoginLogic;
import model.User;
import util.JsonConvertUtil;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 送信されたJSONの取得
        Map<String, String> reqMap = JsonConvertUtil.convertToObject(request);

        String name = reqMap.get("name");
        String pass = reqMap.get("pass");

        // Userインスタンス（ユーザー情報）の生成
        User user = new User(name, pass);

        // ログイン処理
        LoginLogic loginLogic = new LoginLogic();
        boolean isLogin = loginLogic.execute(user);

        // ログイン成功時の処理
        if (isLogin) {
            // ユーザー情報をセッションスコープに保存
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);

            // 戻り値用のオブジェクト作成
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("isLogin", isLogin);
            resMap.put("userName", name);

            // JSONを戻す
            JsonConvertUtil.convertToJson(resMap, response);
        }
    }

}
