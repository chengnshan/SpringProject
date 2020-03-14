import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-03-14 14:30
 */
public class TestH2Database {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring/application.xml");
        JdbcTemplate jdbcTemplate2 = context.getBean("jdbcTemplate2", JdbcTemplate.class);
        List<Map<String, Object>> maps2 = jdbcTemplate2.queryForList("select * from user_info");
        System.out.println(maps2);

        JdbcTemplate jdbcTemplate1 = context.getBean("jdbcTemplate1", JdbcTemplate.class);
        List<Map<String, Object>> maps1 = jdbcTemplate1.queryForList("select * from quartz_task_info where id = " + 3 );
        System.out.println(maps1);
    }
}
    