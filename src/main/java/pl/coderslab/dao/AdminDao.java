package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        return admins;
    }

    public Admin getById(Integer id) {
        return new Admin();
    }

    public Admin create(Admin admin) {
        return new Admin();
    }

    public void delete(Integer id) {

    }

    public void update(Admin admin) {

    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
