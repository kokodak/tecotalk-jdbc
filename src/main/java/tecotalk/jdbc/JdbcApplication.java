package tecotalk.jdbc;

import java.sql.SQLException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcApplication {

    public static void main(String[] args) throws SQLException {
        JdbcExample jdbcExample = new JdbcExample();

        jdbcExample.insertQuery("1", "kokodak", "hello, world!");
        jdbcExample.selectQuery("1");
        jdbcExample.updateQuery("1", "i am kokodak");
        jdbcExample.selectQuery("1");
        jdbcExample.deleteQuery("1");
        jdbcExample.selectQuery("1");
    }
}
