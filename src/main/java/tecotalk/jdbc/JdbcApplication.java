package tecotalk.jdbc;

import java.sql.SQLException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcApplication {

    public static void main(String[] args) throws SQLException {
        JdbcExample jdbcExample = new JdbcExample();
        jdbcExample.selectQuery("1");
        jdbcExample.selectQuery("10");
    }
}
