package tecotalk.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcExample {

    private static final String URL = "jdbc:h2:tcp://localhost/~/tecotalk";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    public void selectQuery(final String id) throws SQLException {
        String sql = "SELECT * FROM member WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String memberId = resultSet.getString("id");
                String memberName = resultSet.getString("name");
                String memberMessage = resultSet.getString("message");

                System.out.println("Id: " + memberId);
                System.out.println("Name: " + memberName);
                System.out.println("Message: " + memberMessage);
            } else {
                System.out.println("찾을 수 없습니다");
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void insertQuery(final String id, final String name, final String message) throws SQLException {
        String sql = "INSERT INTO member(id, name, message) values(?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, message);
            int update = preparedStatement.executeUpdate();

            if (update > 0) {
                System.out.println("INSERT 쿼리가 정상적으로 수행됐습니다.");
            } else {
                System.out.println("INSERT 쿼리가 수행되지 못했습니다.");
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void updateQuery(final String id, final String message) throws SQLException {
        String sql = "UPDATE member SET message = ? WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message);
            preparedStatement.setString(2, id);
            int update = preparedStatement.executeUpdate();

            if (update > 0) {
                System.out.println("UPDATE 쿼리가 정상적으로 수행됐습니다.");
            } else {
                System.out.println("UPDATE 쿼리가 수행되지 못했습니다.");
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void deleteQuery(final String id) throws SQLException {
        String sql = "DELETE FROM member WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);
            int update = preparedStatement.executeUpdate();

            if (update > 0) {
                System.out.println("DELETE 쿼리가 정상적으로 수행됐습니다.");
            } else {
                System.out.println("DELETE 쿼리가 수행되지 못했습니다.");
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}

    /*
drop table member if exists cascade;
create table member (
 id integer not null,
 name varchar(30) not null,
 message varchar(50),
 primary key (id)
);
     */
