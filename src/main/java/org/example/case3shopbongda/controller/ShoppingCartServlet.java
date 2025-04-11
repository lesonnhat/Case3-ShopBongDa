package org.example.case3shopbongda.controller;

import org.example.case3shopbongda.model.Item;
import org.example.case3shopbongda.model.Order;
import org.example.case3shopbongda.model.OrderDetail;
import org.example.case3shopbongda.model.Product;
import org.example.case3shopbongda.service.OrderDAO;
import org.example.case3shopbongda.service.ProductDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ShoppingCartServlet", urlPatterns = "/carts")
public class ShoppingCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        orderDAO = new OrderDAO();
        System.out.println("Initializing Cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    // insertProduct(request, response);
                    break;
                case "edit":
                    // updateProduct(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "add":
                    addToCart(request, response);
                    break;
                case "show":
                    showCart(request, response);
                    break;
                case "checkout":
                    checkout(request, response);
                    break;
                default:
                    showCart(request, response); // Mặc định hiển thị giỏ hàng
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        System.out.println("Destroy Cart");
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String id = request.getParameter("id");

        // Kiểm tra nếu id không hợp lệ
        if (id == null || id.isEmpty()) {
            System.out.println("Invalid product ID: " + id);
            response.sendRedirect("errorPage.jsp");
            return;
        }

        HttpSession session = request.getSession();
        List<Item> cart = (List<Item>) session.getAttribute("cart");

        // Khởi tạo giỏ hàng nếu chưa tồn tại
        if (cart == null) {
            cart = new ArrayList<>();
        }

        Product product = productDAO.findById(id); // Tìm sản phẩm bằng id
        if (product != null) {
            System.out.println("Adding product to cart: " + product.getName() + ", ID: " + id);
            int index = getIndex(id, cart); // Tìm vị trí sản phẩm trong giỏ
            if (index == -1) {
                // Thêm sản phẩm mới vào giỏ hàng
                cart.add(new Item(product, 1));
            } else {
                // Tăng số lượng nếu sản phẩm đã có
                int quantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart", cart); // Cập nhật giỏ hàng trong session
            System.out.println("Cart updated. Total items: " + cart.size());
        } else {
            System.out.println("Product not found with ID: " + id);
        }

        // Chuyển hướng về giỏ hàng để người dùng thấy kết quả ngay lập tức
        response.sendRedirect(request.getContextPath() + "/carts");
    }

    private void showCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart/list.jsp");
        dispatcher.forward(request, response);
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        List<Item> cart = (List<Item>) session.getAttribute("cart");

        // Kiểm tra nếu giỏ hàng trống
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("errorPage.jsp");
            return;
        }

        // Thêm đơn hàng mới
        Order order = new Order();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        order.setOrderDate(format.format(new Date()));

        double totalPrice = 0;
        for (Item item : cart) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        order.setTotalPrice(totalPrice);
        int orderId = orderDAO.saveOrder(order);

        // Thêm chi tiết đơn hàng
        for (Item item : cart) {
            Product product = item.getProduct();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(product.getId());
            orderDetail.setQuantity(item.getQuantity());
            orderDAO.saveOrderDetail(orderDetail);
        }

        // Xóa giỏ hàng sau khi checkout
        session.removeAttribute("cart");
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart/list.jsp");
        dispatcher.forward(request, response);
    }

    private int getIndex(String id, List<Item> cart) {
        if (cart == null) {
            return -1;
        }
        for (int i = 0; i < cart.size(); i++) {
            Product product = cart.get(i).getProduct();
            if (product.getId().equals(id)) { // So sánh ID sản phẩm
                return i;
            }
        }
        return -1;
    }
}