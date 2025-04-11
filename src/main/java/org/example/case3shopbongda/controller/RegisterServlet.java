package org.example.case3shopbongda.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.case3shopbongda.service.DBConnection;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String role = "user"; // Role mặc định

        System.out.println("Register - Username: " + username + ", Password: " + password + ", Email: " + email); // Log để debug

        Connection conn = null;
        try {
            // Kết nối database
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Database connection failed");
                request.setAttribute("error", "Lỗi kết nối database");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            System.out.println("Database connected"); // Log để debug

            // Kiểm tra username đã tồn tại chưa
            String checkSql = "SELECT username FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            if (checkStmt.executeQuery().next()) {
                System.out.println("Username already exists: " + username); // Log để debug
                request.setAttribute("error", "Tên đăng nhập đã tồn tại");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Thêm người dùng mới vào database
            String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password); // Lưu mật khẩu dạng thô
            stmt.setString(3, email);
            stmt.setString(4, role);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Log để debug

            if (rowsAffected > 0) {
                // Đăng ký thành công
                System.out.println("Registration successful for: " + username); // Log để debug
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                // Đăng ký thất bại
                System.out.println("Registration failed for: " + username); // Log để debug
                request.setAttribute("error", "Đăng ký thất bại, vui lòng thử lại");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("error", "Email đã tồn tại");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } finally {
            // Đóng kết nối
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doGet called for /register"); // Log để debug
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}