create domain t_correo varchar(50) not null constraint CHK_t_correo
    check (value similar to '[A-z]%@[A-z]%.[A-z]%');

-- DROP TABLE juego ;
create table jugador
(
	correo t_correo,
	foto varchar(70),
	nombre varchar(50) not null,
	apellido1 varchar(50),
	CONSTRAINT     pk_correo_jugador primary key (correo)
	
);
select * from jugador;

create table juego
(	id varchar (100),
	jugador1 varchar(50) not null,
	jugador2 varchar(50) not null,
	tablero text not null,
	CONSTRAINT     pk_id_juego primary key (id)


);
ALTER TABLE juego DROP COLUMN address;
ALTER TABLE juego ADD COLUMN tablero text not null;
/* union jugador y juego */
create table sesion 
(
	correo_j1 t_correo,
	correo_j2 t_correo,
	tablero varchar[],
	configuacion varchar[],
	CONSTRAINT    FK_correo_j1_sesion
		FOREIGN KEY (correo_j1) REFERENCES jugador,
	CONSTRAINT    FK_correo_j2_sesion
		FOREIGN KEY (correo_j2) REFERENCES jugador
	

);

--drop table juego
--INSERT INTO juego VALUES ('1','{Emilio, Bermudez, siuuu, e10000}');
--select * from juego;
--select * from juego WHERE id='1';
--select tablero from juego WHERE id='1';
--ALTER TABLE juego DROP COLUMN address;
--ALTER TABLE juego ADD COLUMN tablero text not null;

--ALTER TABLE juego ADD COLUMN jugador1 varchar(50) not null;
--ALTER TABLE juego ADD COLUMN jugador2 varchar(50) not null;

--select tablero from juego WHERE jugador1='Kevin Ubau' OR jugador2='Kevin Ubau';
--DELETE FROM juego;
