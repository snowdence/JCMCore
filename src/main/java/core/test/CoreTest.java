package core.test;

abstract class Parent {
    public abstract boolean insert();

    public boolean insert(String where_condition) {
        return true;
    }
}

public class CoreTest {
    public static void main(String[] args) {
        TDBConnection tdb = new TDBConnection();
        tdb.testInsertUserModel();
        tdb.testSelectUserModel();
        tdb.testUpdateUserModel();
        tdb.testRemoveUserModel();

        tdb.testInsertCourseModel();
        tdb.testSelectCourseModel();
        tdb.testUpdateCourseModel();
        tdb.testRemoveCourseModel();


        tdb.testInsertLessonModel();
        tdb.testSelectLessonModel();
        tdb.testUpdateLessonModel();
        tdb.testRemoveLessonModel();
    }
}
