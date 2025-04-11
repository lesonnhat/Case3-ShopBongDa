create database shopbongda;
use shopbongda;

create table products (
id varchar(20) not null primary key,
name varchar(200) not null,
price decimal(10,2) not null,
origin varchar(200) not null,
imageUrl varchar(200) not null,
category varchar(20) not null
);

insert into products
values ("AO001","Áo sân nhà Aston Villa",1250,"England","images/ao-aston.jpg","ao"),
("AO002","Áo sân nhà Barcelona",1690,"Spain","images/ao-barca.jpg","ao"),
("AO003","Áo sân nhà Inter Milan",1100,"Italy","images/ao-inter.jpg","ao"),
("AO004","Áo sân nhà Juventus",1100,"Italy","images/ao-juven.jpg","ao"),
("GIAY001","Giày đá bóng Adidas",340,"Germany","images/giay-adidas.jpg","giay"),
("GIAY002","Giày đá bóng Akka",160,"Vietnam","images/giay-akka.jpg","giay"),
("GIAY003","Giày đá bóng Lining",500,"China","images/giay-lining.jpg","giay"),
("GIAY004","Giày đá bóng Mizuno",770,"Japan","images/giay-mizuno.jpg","giay"),
("TUI001","Balo Adidas - Blue",1690,"Germany","images/tui-das-blue.jpg","tui"),
("TUI002","Balo Adidas - Mix Color",1890,"Germany","images/tui-das-color.jpg","tui"),
("TUI003","Balo Adidas - Red",1690,"Germany","images/tui-das-red.jpg","tui"),
("TUI004","Túi New Balance",2230,"USA","images/tui-new-gray.jpg","tui");

select * from products;

-- Tạo bảng orders
create table orders (
    id int not null auto_increment primary key,
    order_date datetime not null,
    total_price decimal(10,2) not null
);

-- Tạo bảng order_detail
create table order_detail (
    id int not null auto_increment primary key,
    order_id int not null,
    product_id varchar(20) not null,
    quantity int not null,
    foreign key (order_id) references orders(id),
    foreign key (product_id) references products(id)
);

UPDATE products
SET imageUrl = 'images/tui-das-red.jpg'
WHERE id = 'TUI003';

create table users (
id int primary key auto_increment,
username varchar(200) not null unique,
password varchar(200) not null,
email varchar(200) not null unique,
role varchar(20) default 'user'
);

insert into users (username, password, email, role)
values ('admin', '1235', 'admin@gmail.com', 'admin'),
    ('customer', '1235', 'customer@gmail.com', 'user');
    
select * from users;