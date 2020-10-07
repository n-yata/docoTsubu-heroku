package model;

import java.util.List;

/**
 * つぶやきを追加するロジック
 * @author yata1
 */
public class PostMutterLogic {
    public void execute(Mutter mutter, List<Mutter> mutterList) {
        // 先頭に追加
        mutterList.add(0, mutter);
    }
}
