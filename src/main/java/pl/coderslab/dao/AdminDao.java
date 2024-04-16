package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static pl.coderslab.utils.DbUtil.getConnection;


public class AdminDao {
    private static final Logger log = LogManager.getLogger(AdminDao.class);

    private static final String FIND_ALL_ADMINS = "SELECT * FROM admins;";
    private static final String GET_ADMIN_BY_ID = "SELECT * FROM admins WHERE id = ?;";
    private static final String DELETE_ADMIN = "DELETE FROM admins WHERE id = ?;";
    private static final String CREATE_ADMIN = """
            INSERT INTO admins 
            (first_name, last_name, email, password, superadmin) 
            values (?, ?, ?, ?, 0);
            """;
    private static final String UPDATE_ADMIN = """
            UPDATE admins
            SET
                first_name = ?,
                last_name = ?,
                email = ?,
                password = ?
            WHERE id = ?;
            """;

    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_ADMINS);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                Admin adminToAdd = new Admin();
                adminToAdd.setId(resultSet.getInt("id"));
                adminToAdd.setFirstName(resultSet.getString("first_name"));
                adminToAdd.setLastName(resultSet.getString("last_name"));
                adminToAdd.setEmail(resultSet.getString("email"));
                adminToAdd.setPassword(resultSet.getString("password"));
                adminToAdd.setSuperAdmin(resultSet.getInt("superAdmin"));
                adminToAdd.setEnable(resultSet.getInt("enable"));
                admins.add(adminToAdd);
            }
        } catch (SQLException e) {
            log.error(e.getStackTrace());
        }
        return admins;
    }

    public Admin getById(Integer id) {
        Admin adminToReturn = null;
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ADMIN_BY_ID, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                adminToReturn = new Admin();
                adminToReturn.setId(resultSet.getInt("id"));
                adminToReturn.setFirstName(resultSet.getString("first_name"));
                adminToReturn.setLastName(resultSet.getString("last_name"));
                adminToReturn.setEmail(resultSet.getString("email"));
                adminToReturn.setPassword(resultSet.getString("password"));
                adminToReturn.setSuperAdmin(resultSet.getInt("superAdmin"));
                adminToReturn.setEnable(resultSet.getInt("enable"));
            }
        } catch (SQLException e) {
            log.error(e.getStackTrace());
        }
        return adminToReturn;
    }

    public Admin create(Admin admin) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_ADMIN, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(1, admin.getEmail());
            statement.setString(1, hashPassword(admin.getPassword()));
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                admin.setId(resultSet.getInt(1));
                log.info("Admin {} created", admin);
            }

        } catch (SQLException e) {
            log.error(e.getStackTrace());
        }
        return admin;
    }

    public int delete(Integer id) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN);
            statement.setInt(1, id);
            int isDeleted = statement.executeUpdate();

            if (isDeleted == 1) {
                log.info("User with id {} deleted", id);
            } else {
                log.warn("User with id {} NOT deleted", id);
            }
            return isDeleted;
        } catch (SQLException e) {
            log.error(e.getStackTrace());
            return 0;
        }
    }

    public int update(Admin admin) {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_ADMIN);
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3,hashPassword(admin.getPassword()));
            statement.setInt(4,admin.getId());
            int isUpdated = statement.executeUpdate();

            if (isUpdated == 1) {
                log.info("User with id {} successfully updated", admin.getId());

            } else {
                log.info("User with id {} NOT updated", admin.getId());
            }
            return isUpdated;

        } catch (SQLException e) {
            log.error(e.getStackTrace());
            return 0;
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
