package ua.foxminded.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerate implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataGenerate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        createUserRoleRelations();
    }

    private void createUserRoleRelations() {

        String getUserIdsQuery = "SELECT id FROM cms.users";
        List<Integer> userIds = jdbcTemplate.queryForList(getUserIdsQuery, Integer.class);

        String getRoleIdsQuery = "SELECT id FROM cms.roles";
        List<Integer> roleIds = jdbcTemplate.queryForList(getRoleIdsQuery, Integer.class);

        for (int userId : userIds) {
            String userNameQuery = "SELECT user_name FROM cms.users WHERE id = ?";
            String userName = jdbcTemplate.queryForObject(userNameQuery, String.class, userId);

            int numRoles = 1;

            if (userName.equals("admin")) {
                numRoles = 1;
            } else if (userName.equals("student") || userName.equals("teacher")) {
                numRoles = 1;
            }

            List<Integer> assignedRoleIds = new ArrayList<>();
            for (int i = 0; i < numRoles; i++) {
                int roleId;
                if (userName.equals("admin")) {
                    roleId = 1;
                } else {
                    roleId = 2;
                }

                if (!assignedRoleIds.contains(roleId)) {
                    assignedRoleIds.add(roleId);

                    String assignUserToRoleQuery = "INSERT INTO cms.user_roles (user_id, role_id) VALUES (?, ?)";
                    jdbcTemplate.update(assignUserToRoleQuery, userId, roleId);
                }
            }
        }
    }

}
