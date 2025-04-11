package org.example.case3shopbongda.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.example.case3shopbongda.service.DBConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("doPost called for /login");
        System.out.println("Received - Username: " + username + ", Password: " + password); // Log để debug

        Connection conn = null;
        try {
            // Kết nối database
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Database connection failed");
                request.setAttribute("error", "Lỗi kết nối database");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
            System.out.println("Database connected"); // Log để debug

            String sql = "SELECT password, role FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Query executed"); // Log để debug

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String role = rs.getString("role");
                System.out.println("Stored password: " + storedPassword + ", Role: " + role); // Log để debug
                // Kiểm tra mật khẩu trực tiếp (không mã hóa), tránh NullPointerException
                if (password != null && storedPassword != null && password.equals(storedPassword)) {
                    // Đăng nhập thành công
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    session.setAttribute("role", role); // Lưu role vào session
                    System.out.println("Session username set: " + session.getAttribute("username")); // Log để debug
                    System.out.println("Context path: " + request.getContextPath()); // Log để debug
                    System.out.println("Redirecting to: " + request.getContextPath() + "products"); // Log để debug
                    response.sendRedirect(request.getContextPath() + "products");
                    return; // Đảm bảo không có code nào chạy sau redirect
                } else {
                    // Sai mật khẩu
                    System.out.println("Password does not match or is null"); // Log để debug
                    request.setAttribute("error", "Mật khẩu không hợp lệ");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {
                // Không tìm thấy username
                System.out.println("No user found for username: " + username); // Log để debug
                request.setAttribute("error", "Tên đăng nhập không tồn tại");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("error", "Lỗi database: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
        System.out.println("doGet called for /login"); // Log để debug
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}