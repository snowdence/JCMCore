package core.service;

import core.database.MDBConnector;
import core.entity.AccountEntity;
import core.model.CourseModel;
import core.model.EnrollmentModel;
import core.model.UserModel;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseService {
    public CourseService() {

    }

    // Lay course bang id
    public CourseModel getCourseByID(int id) {
        CourseModel record = new CourseModel(id);
        boolean status = record.select();
        return (status ? record : null);
    }

    /**
     * Them khoa hoc
     *
     * @param account
     * @param model
     * @return -1: not logined
     * -2: account must be teacher
     * -3: unexpected insert error
     */
    public int insertCourse(AccountEntity account, CourseModel model) {
        if (!account.isLogined()) {
            return -1;
        }
        if (!account.isTeacher()) {
            return -2;
        }
        model.setAuthorID(account.getID());

        boolean status = model.insert();
        if (status) {
            return 0;
        } else {
            return -3;
        }
    }

    /**
     * Xoa khoa hoc course cua tai khoan account
     *
     * @param account
     * @param model
     * @return -1: not logined
     * -2: account must be teacher
     * -3: this post must be of current account
     * -4 : unexpected
     */
    public int removeCourse(AccountEntity account, CourseModel model) {
        if (!account.isLogined()) {
            return -1;
        }
        if (!account.isTeacher()) {
            return -2;
        }
        if (account.getID() != model.getAuthorID()) {
            return -3;
        }
        boolean status = model.remove();
        if (status) {
            return 0;
        } else {
            return -4;
        }
    }

    public ArrayList<CourseModel> getAllCourses() {
        return getListCourseModel(null);
    }

    /**
     * Lay danh sach khoa hoc dang boi tai khoan teacher
     *
     * @param account
     * @return
     */
    public ArrayList<CourseModel> getMyCourses(AccountEntity account) {
        return getListCourseModel(String.format("WHERE author_id ='%s' ", account.getID()));
    }

    /**
     * Lay toan bo danh sach khoa hoc
     *
     * @param condition
     * @return
     */
    public ArrayList<CourseModel> getListCourseModel(String condition) {
        MDBConnector mdbConnector = MDBConnector.getInstance();
        Connection conn = mdbConnector.getConnection();
        ArrayList<CourseModel> list = new ArrayList<CourseModel>();

        String queryString = String.format("SELECT * FROM course ");
        if (condition != null) {
            queryString += condition;
        }

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            System.out.println(queryString);
            ResultSet rs = stmt.executeQuery(queryString);
            if (rs.next()) {
                do {
                    CourseModel model = new CourseModel();
                    model.parseItem(rs);
                    list.add(model);
                }
                while (rs.next());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("[DBHandleException]: Có lỗi xảy ra DB select()");
        }
        return list;
    }


    /**
     * Lay danh sach khoa hoc da tham gia
     *
     * @param account
     * @return
     */
    public ArrayList<CourseModel> getEnrolledCourse(AccountEntity account) {


        ArrayList<CourseModel> listCourse = new ArrayList<CourseModel>();
        MDBConnector mdbConnector = MDBConnector.getInstance();
        Connection conn = mdbConnector.getConnection();

        ArrayList<EnrollmentModel> list = new ArrayList<EnrollmentModel>();

        if (!account.isLogined()) {
            System.out.println("Must login");

            return null;
        }
        if (!account.isStudent()) {
            System.out.println("Student permission");
            return null;
        }
        String queryString = String.format("SELECT * FROM enrollment WHERE user_id = %d", account.getID());

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            System.out.println(queryString);
            ResultSet rs = stmt.executeQuery(queryString);
            if (rs.next()) {
                do {
                    EnrollmentModel model = new EnrollmentModel();
                    model.parseItem(rs);
                    listCourse.add(model.getCourseRelation());
                    list.add(model);
                }
                while (rs.next());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("[DBHandleException]: Có lỗi xảy ra DB select()");
        }
        return listCourse;
    }


}
