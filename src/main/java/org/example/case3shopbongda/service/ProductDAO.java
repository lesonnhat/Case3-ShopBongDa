package org.example.case3shopbongda.service;

import org.example.case3shopbongda.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IDAO<Product> {

    private static final String SELECT_ALL_PRODUCTS = "select * from product";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (Id, Name, Price, Origin, ImageUrl, Category) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_PRODUCT_BY_ID = "select * from product where id = ?";
    private static final String UPDATE_PRODUCT_SQL = "update product set name = ?, price = ?, orgin = ?, imageUrl = ?, category = ? where id = ?;";

    public ProductDAO() {
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<Product>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String origin = rs.getString("origin");
                String imageUrl = rs.getString("imageUrl");
                String category = rs.getString("category");
                products.add(new Product(id, name, price, origin, imageUrl, category));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    @Override
    public List<Product> findAllWithStoreProcedure() {
        List<Product> products = new ArrayList<Product>();
        String query = "{CALL sp_get_all_products()}";

        try (Connection connection = DBConnection.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String origin = rs.getString("origin");
                String imageUrl = rs.getString("imageUrl");
                String category = rs.getString("category");
                products.add(new Product(id, name, price, origin, imageUrl, category));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    @Override
    public void save(Product product) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getOrigin());
            preparedStatement.setString(4, product.getImageUrl());
            preparedStatement.setString(5, product.getCategory());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void saveWithStoreProcedure(Product product) throws SQLException {
        String query = "{CALL sp_insert_product(?,?)}";
        try (Connection connection = DBConnection.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query);) {
            callableStatement.setString(1, product.getName());
            callableStatement.setDouble(2, product.getPrice());
            callableStatement.setString(3, product.getOrigin());
            callableStatement.setString(4, product.getImageUrl());
            callableStatement.setString(5, product.getCategory());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Product findById(String id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setCategory(rs.getString("category"));
                System.out.println("Found product: " + product.getName() + ", ID: " + id);
                return product;
            } else {
                System.out.println("No product found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product findByIdWithStoreProcedure(String id) {
        String query = "{CALL sp_find_product_by_id(?)}";
        Product product = null;
        try (Connection connection = DBConnection.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query);) {
            callableStatement.setString(1, id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double price = Double.parseDouble(rs.getString("price"));
                String origin = rs.getString("origin");
                String imageUrl = rs.getString("imageUrl");
                String category = rs.getString("category");
                product = new Product(id, name, price, origin, imageUrl, category);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        boolean rowUpdated = false;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL);) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getOrigin());
            statement.setString(4, product.getImageUrl());
            statement.setString(5, product.getCategory());
            statement.setString(6, product.getId());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    @Override
    public boolean updateWithStoreProcedure(Product product) throws SQLException {
        String query = "{CALL sp_update_product(?,?,?,?,?)}";
        boolean rowUpdated;
        try (Connection connection = DBConnection.getConnection();
             CallableStatement statement = connection.prepareCall(query);) {
            statement.setString(1, product.getId());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getOrigin());
            statement.setString(5, product.getImageUrl());
            statement.setString(6, product.getCategory());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
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