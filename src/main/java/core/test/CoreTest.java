package core.test;

abstract class Parent{
    public abstract boolean insert();
    public boolean insert(String where_condition){
        return true;
    }
}
public class CoreTest {
    public static void main(String[] args) {
        TDBConnection tdbCoon = new TDBConnection();
        //tdbCoon.testInsertUser();
        //tdbCoon.testUpdateUser();
        //tdbCoon.testRemoveUser();
    }
}
