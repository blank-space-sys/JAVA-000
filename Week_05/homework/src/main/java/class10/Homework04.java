package class10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Homework04 {
    // 数据库地址
    private static String dbUrl = "jdbc:mysql://localhost:3306/test?useSSL=true&serverTimezone=UTC";
    // 用户名
    private static String dbUserName = "root";
    // 密码
    private static String dbPassword = "123456";
    // 驱动名称
    private static String jdbcName = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws Exception {
        Connection conn = getConnection();
        String updateSql = "update user set name = ? ,age = ? where id = 3";
        // 关闭自动提交，此句代码会自动开启事务。默认为true，自动提交。
        conn.setAutoCommit(false);
        PreparedStatement preparedStatement = conn.prepareStatement(updateSql);
        preparedStatement.setString(1, "zhengxiaojun");//给第一个坑设值
        preparedStatement.setString(2, "18");
        preparedStatement.execute();
        conn.commit(); //提交


    }

    /**
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        // 加载数据库驱动
        Class.forName(jdbcName);
        return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
    }

    /**
     * 关闭连接
     */
    public static void close(Statement stmt, Connection con) throws Exception {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

}
