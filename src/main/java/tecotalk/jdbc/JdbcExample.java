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
        // SQL 쿼리를 작성합니다. (확정 X)
        String sql = "SELECT * FROM member WHERE id = ?";

        // finally 에서 close() 메서드로 리소스 반납을 해야하므로 try 문 안쪽이 아닌, 바깥 쪽에서 선언해야 합니다.
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // DB와 연결하고, Connection 객체를 받아옵니다.
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // SQL 쿼리를 넘겨주고, 해당 쿼리를 실행할 PreparedStatement 객체를 받아옵니다.
            preparedStatement = connection.prepareStatement(sql);

            // ?(placeholder)의 값을 바인딩하여 SQL 쿼리를 확정시킵니다.
            preparedStatement.setString(1, id);

            // 바인딩된 SQL 쿼리를 실행시킵니다.
            // 이때 반환되는 ResultSet 은 실행한 SQL 쿼리의 결과를 가지고 있습니다.
            resultSet = preparedStatement.executeQuery();

            // ResultSet 내부의 커서(cursor)를 이용해 결과가 있는지 탐색합니다.
            if (resultSet.next()) {
                // 만약 다음 커서의 위치에 SQL 쿼리 결과가 존재한다면, 해당 결과를 가져옵니다.
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
            // finally 에서는 DB 사용을 마치고, 사용했던 리소스를 반납하는 과정을 거쳐야 합니다.
            // close() 로 리소스를 반납하는 순서는 객체를 받아온 순서의 역순입니다.

            // ResultSet 객체가 null 이 아닌 경우에만 close() 해줍니다.
            if (resultSet != null) {
                resultSet.close();
            }
            // PreparedStatement 객체가 null 이 아닌 경우에만 close() 해줍니다.
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            // Connection 객체가 null 이 아닌 경우에만 close() 해줍니다.
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void insertQuery(final String id, final String name, final String message) throws SQLException {
        // SQL 쿼리를 작성합니다. (확정 X)
        String sql = "INSERT INTO member(id, name, message) values(?, ?, ?)";

        // finally 에서 close() 메서드로 리소스 반납을 해야하므로 try 문 안쪽이 아닌, 바깥 쪽에서 선언해야 합니다.
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // DB와 연결하고, Connection 객체를 받아옵니다.
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // SQL 쿼리를 넘겨주고, 해당 쿼리를 실행할 PreparedStatement 객체를 받아옵니다.
            preparedStatement = connection.prepareStatement(sql);

            // ?(placeholder)의 값을 바인딩하여 SQL 쿼리를 확정시킵니다.
            preparedStatement.setString(1, id); // 첫 번째 ? 파라미터
            preparedStatement.setString(2, name); // 두 번째 ? 파라미터
            preparedStatement.setString(3, message); // 세 번째 ? 파라미터

            // 바인딩된 SQL 쿼리를 실행시킵니다.
            // 이때 반환되는 정수형은, 쿼리 실행으로 인해 영향을 받은 행(row)의 수를 뜻합니다.
            int update = preparedStatement.executeUpdate();

            // 쿼리 실행으로 인해 영향 받은 행(row)이 존재한다면 쿼리가 정상 수행된 것이고, 존재하지 않는다면 쿼리가 수행되지 못한 상황입니다.
            if (update > 0) {
                System.out.println("INSERT 쿼리가 정상적으로 수행됐습니다.");
            } else {
                System.out.println("INSERT 쿼리가 수행되지 못했습니다.");
            }
        } finally {
            // finally 에서는 DB 사용을 마치고, 사용했던 리소스를 반납하는 과정을 거쳐야 합니다.
            // close() 로 리소스를 반납하는 순서는 객체를 받아온 순서의 역순입니다.

            // PreparedStatement 객체가 null 이 아닌 경우에만 close() 해줍니다.
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            //  Connection 객체가 null 이 아닌 경우에만 close() 해줍니다.
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
