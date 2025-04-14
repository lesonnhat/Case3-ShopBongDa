// UserDAO.java (trong thư mục service)
package org.example.case3shopbongda.service;

import org.example.case3shopbongda.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements IDAO<User> {

    @Override
    public User findById(String id) {
        return null; // Không cần dùng trong trường hợp này
    }

    @Override
    public User findByIdWithStoreProcedure(String id) {
        return null; // Không cần dùng trong trường hợp này
    }

    @Override
    public List<User> findAll() {
        return null; // Không cần dùng trong trường hợp này
    }

    @Override
    public List<User> findAllWithStoreProcedure() {
        return null; // Không cần dùng trong trường hợp này
    }

    @Override
    public void save(User user) throws SQLException {
        // Không cần dùng trong trường hợp này
    }

    @Override
    public void saveWithStoreProcedure(User user) throws SQLException {
        // Không cần dùng trong trường hợp này
    }

    @Override
    public boolean update(User user) throws SQLException {
        return false; // Không cần dùng trong trường hợp này
    }

    @Override
    public boolean updateWithStoreProcedure(User user) throws SQLException {
        return false; // Không cần dùng trong trường hợp này
    }

    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}