package model;

import dao.MutterDAO;

public class DeleteMutterLogic {
    public void execute() {
        MutterDAO dao = new MutterDAO();
        dao.delete();
    }
}
