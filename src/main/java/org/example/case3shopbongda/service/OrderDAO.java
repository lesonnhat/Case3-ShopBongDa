package org.example.case3shopbongda.service;

import org.example.case3shopbongda.model.Order;
import org.example.case3shopbongda.model.OrderDetail;

import java.sql.*;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OrderDAO implements IDAO<Order> {
    @Override
    public List<Order> findAll() {
        return Collections.emptyList();
    }

    @Override
    public List<Order> findAllWithStoreProcedure() {
        return Collections.emptyList();
    }

    @Override
    public void save(Order entity) throws SQLException {

    }

    @Override
    public void saveWithStoreProcedure(Order entity) throws SQLException {

    }

    public int saveOrder(Order order) throws SQLException {
        String query = "{CALL sp_insert_order(?,?,?)}";
        int orderId = -1;
        try (Connection connection = DBConnection.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query);) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date orderDate = simpleDateFormat.parse(order.getOrderDate());
            callableStatement.setDate(1, new java.sql.Date(orderDate.getTime()));
            callableStatement.setDouble(2, order.getTotalPrice());
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.executeUpdate();
            orderId = callableStatement.getInt(3);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            printSQLException(e);
        }
        return orderId;
    }

    public void saveOrderDetail(OrderDetail orderDetail) throws SQLException {
        String query = "{CALL sp_insert_order_detail(?,?,?)}";
        try (Connection connection = DBConnection.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query);) {
            callableStatement.setInt(1, orderDetail.getOrderId());
            callableStatement.setString(2, orderDetail.getProductId());
            callableStatement.setInt(3, orderDetail.getQuantity());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Order findById(String id) {
        return null;
    }

    @Override
    public Order findByIdWithStoreProcedure(String id) {
        return null;
    }

    @Override
    public boolean update(Order entity) throws SQLException {
        return false;
    }

    @Override
    public boolean updateWithStoreProcedure(Order entity) throws SQLException {
        return false;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}