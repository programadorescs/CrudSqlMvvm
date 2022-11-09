create database demoDb;
go
use demoDb;
go

create table producto(
id integer primary key identity(1,1),
codigoBarra nvarchar(100) not null unique,
descripcion nvarchar(250) not null,
costo decimal(13, 2) not null,
precio decimal(13, 2) not null,
stock int not null
);
go

create table pedido(
id integer primary key identity(1,1),
fecha nvarchar(100) not null,
cliente nvarchar(250),
total decimal(13, 2) not null,
estado nvarchar(20) default 'vigente'
);
go

create table detalle_pedido(
id integer primary key identity(1,1),
idpedido int not null REFERENCES Pedido(id),
idproducto int not null REFERENCES Producto(id),
costo decimal(13, 2) not null,
precio decimal(13, 2) not null,
stock int not null
);
go