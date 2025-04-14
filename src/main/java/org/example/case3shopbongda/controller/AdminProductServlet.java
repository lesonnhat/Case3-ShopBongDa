// AdminProductServlet.java (trong thư mục controller)
package org.example.case3shopbongda.controller;

import org.example.case3shopbongda.model.Product;
import org.example.case3shopbongda.service.DBConnection;
import org.example.case3shopbongda.service.ProductDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AdminProductServlet", urlPatterns = "/admin")
public class AdminProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        System.out.println("Initializing AdminProductServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra đăng nhập và role
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null || !"admin".equals(session.getAttribute("role"))) {
            System.out.println("Unauthorized access to /admin/list, redirecting to admin");
            response.sendRedirect(request.getContextPath() + "admin");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
                    break;
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Lỗi: " + ex.getMessage());
            request.getRequestDispatcher("/admin/list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra đăng nhập và role
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null || !"admin".equals(session.getAttribute("role"))) {
            System.out.println("Unauthorized access to /admin/list, redirecting to admin");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
                    break;
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Lỗi: " + ex.getMessage());
            request.getRequestDispatcher("/admin/list.jsp").forward(request, response);
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        System.out.println("Listing products for admin");
        List<Product> listProducts = productDAO.findAllWithStoreProcedure();
        request.setAttribute("listProducts", listProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> categories = Arrays.asList("ao", "giay", "tui");
        request.setAttribute("categories", categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        Product existingProduct = productDAO.findByIdWithStoreProcedure(id);
        if (existingProduct == null) {
            request.setAttribute("error", "Sản phẩm không tồn tại.");
            request.getRequestDispatcher("/admin/list.jsp").forward(request, response);
            return;
        }
        // Mock categories (replace with actual DAO call)
        List<String> categories = Arrays.asList("ao", "giay", "tui");
        request.setAttribute("categories", categories);
        request.setAttribute("product", existingProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String origin = request.getParameter("origin");
        String imageUrl = request.getParameter("imageUrl");
        String category = request.getParameter("category");

        // Validation
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "Tên sản phẩm không được để trống.");
            request.getRequestDispatcher("/admin/create.jsp").forward(request, response);
            return;
        }

        System.out.println("Inserting product: ID=" + id + ", Name=" + name);
        Product newProduct = new Product(id, name, price, origin, imageUrl, category);
        productDAO.saveWithStoreProcedure(newProduct);
        response.sendRedirect("admin");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String origin = request.getParameter("origin");
        String imageUrl = request.getParameter("imageUrl");
        String category = request.getParameter("category");

        // Validation
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "Tên sản phẩm không được để trống.");
            request.getRequestDispatcher("/admin/edit.jsp").forward(request, response);
            return;
        }

        System.out.println("Updating product: ID=" + id + ", Name=" + name);
        Product product = new Product(id, name, price, origin, imageUrl, category);
        productDAO.updateWithStoreProcedure(product);
        response.sendRedirect("admin");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        String id = request.getParameter("id");
        productDAO.deleteWithStoreProcedure(id);
        request.getSession().setAttribute("message", "Xóa sản phẩm thành công.");
        response.sendRedirect("/admin");
    }

    @Override
    public void destroy() {
        System.out.println("Destroy AdminProductServlet");
    }
}