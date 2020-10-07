package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author yata1
 *
 */
public class JsonConvertUtil {
    /**
     *
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public static Map<String, String> convertToObject(HttpServletRequest request)
            throws ServletException, IOException {
        // 送信されたJSONの取得
        BufferedReader reader = new BufferedReader(request.getReader());
        String reqJson = reader.readLine();

        // JSONをオブジェクトに変更
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> reqMap = mapper.readValue(reqJson, new TypeReference<Map<String, String>>() {});

        return reqMap;
    }

    /**
     *
     * @param resMap
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void convertToJson(Map<String, Object> resMap ,HttpServletResponse response)
            throws ServletException, IOException{
        // JSON文字列に変換
        ObjectMapper mapper = new ObjectMapper();
        String resJson = mapper.writeValueAsString(resMap);

        // ヘッダ情報などセット
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "nocache");
        response.setCharacterEncoding("utf-8");

        // JSONを戻す
        PrintWriter out = response.getWriter();
        out.print(resJson);
    }
}
