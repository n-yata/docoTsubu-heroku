package model;

import dao.MutterDAO;

/**
 * つぶやきを追加するロジック
 * @author yata1
 */
public class PostMutterLogic {
    public void execute(Mutter mutter) {
        MutterDAO dao = new MutterDAO();
        dao.create(mutter);
    }
}
