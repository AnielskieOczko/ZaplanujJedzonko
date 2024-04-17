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
            values (?, ?, ?, ?, ?);
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

    public Admin read(Integer adminId) {
        Admin adminToReturn = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ADMIN_BY_ID)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    adminToReturn.setId(resultSet.getInt("id"));
                    adminToReturn.setFirstName(resultSet.getString("first_name"));
                    adminToReturn.setLastName(resultSet.getString("last_name"));
                    adminToReturn.setEmail(resultSet.getString("email"));
                    adminToReturn.setPassword(resultSet.getString("password"));
                    adminToReturn.setSuperAdmin(resultSet.getInt("superAdmin"));
                    adminToReturn.setEnable(resultSet.getInt("enable"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminToReturn;
    }

    public Admin create(Admin admin) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_ADMIN, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, hashPassword(admin.getPassword()));
            statement.setInt(5, admin.getSuperAdmin());

            int result = statement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    admin.setId(generatedKeys.getInt(1));
                    return admin;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public boolean delete(Integer adminId) {
        boolean deleted;
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN);
            statement.setInt(1, adminId);
            statement.executeUpdate();

            deleted = statement.execute();
            if (!deleted) {
                log.error("Admin not deleted");
            }
            return deleted;
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
    }



    public int update(Admin admin) {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_ADMIN);
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, hashPassword(admin.getPassword()));
            statement.setInt(5, admin.getId());

            int isUpdated = statement.executeUpdate();

            if (isUpdated == 1) {
                log.info("User with id {} successfully updated", admin.getId());

            } else {
                log.info("User with id {} NOT updated", admin.getId());
            }
            return isUpdated;

        } catch (SQLException e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
