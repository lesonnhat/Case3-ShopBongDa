-- Stored procedure để lấy tất cả sản phẩm
delimiter //
create procedure sp_get_all_products()
begin
    select * from products;
end //
delimiter ;

-- Stored procedure để chèn sản phẩm mới
delimiter //
create procedure sp_insert_product(
    in _id varchar(20),
    in _name varchar(200),
    in _price decimal(10,2),
    in _origin varchar(200),
    in _imageUrl varchar(200),
    in _category varchar(20)
)
begin
    insert into products(id, name, price, origin, imageUrl, category) 
    values(_id, _name, _price, _origin, _imageUrl, _category);
end //
delimiter ;

-- Stored procedure để tìm sản phẩm theo id
delimiter //
create procedure sp_find_product_by_id(
    in _id varchar(20)
)
begin
    select * from products
    where id = _id;
end //
delimiter ;

-- Stored procedure để cập nhật sản phẩm
delimiter //
create procedure sp_update_product(
    in _id varchar(20),
    in _name varchar(200),
    in _price decimal(10,2),
    in _origin varchar(200),
    in _imageUrl varchar(200),
    in _category varchar(20)
)
begin
    update products
    set name = _name, price = _price, origin = _origin, imageUrl = _imageUrl, category = _category
    where id = _id;
end //
delimiter ;

-- Stored procedure để chèn đơn hàng mới
delimiter //
create procedure sp_insert_order(
    in _order_date datetime,
    in _total_price decimal(10,2),
    out _order_id int
)
begin
    insert into orders(order_date, total_price) values(_order_date, _total_price);
    set _order_id = LAST_INSERT_ID();
end //
delimiter ;

-- Gọi stored procedure sp_insert_order
set @id = -1;
call sp_insert_order('2022-3-3', 29, @id);
select @id;

-- Stored procedure để chèn chi tiết đơn hàng
delimiter //
create procedure sp_insert_order_detail(
    in _order_id int,
    in _product_id varchar(20),
    in _quantity int
)
begin
    -- Kiểm tra xem order_id và product_id có tồn tại không
    if exists (select 1 from orders where id = _order_id) and 
       exists (select 1 from products where id = _product_id) then
        insert into order_detail(order_id, product_id, quantity) 
        values(_order_id, _product_id, _quantity);
    else
        signal sqlstate '45000'
        set message_text = 'Invalid order_id or product_id';
    end if;
end //
delimiter ;