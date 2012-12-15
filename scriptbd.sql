DROP TABLE IF EXISTS ruta CASCADE;
CREATE TABLE ruta (
	id_ruta VARCHAR(10),
	tipo_ruta VARCHAR(20),
	paradas VARCHAR(2000),
	PRIMARY KEY (id_ruta)
);

DROP TABLE IF EXISTS bus CASCADE;
CREATE TABLE bus (
	id_bus VARCHAR(10),
	tipo_bus VARCHAR(20),
	carroceria VARCHAR(40),
	motor VARCHAR(40),
	capacidad_parados INTEGER,
	capacidad_sillas INTEGER,
	ruta_asignada VARCHAR(10),
	FOREIGN KEY (ruta_asignada) REFERENCES ruta(id_ruta),
	PRIMARY KEY (id_bus)
);

DROP TABLE IF EXISTS empleado CASCADE;
CREATE TABLE empleado (
	id_empleado VARCHAR(20),
	nombre_empleado VARCHAR(100),
	fecha_nac DATE,
	estrato_empleado integer,
	genero_empleado VARCHAR(10),
	cargo VARCHAR(30),
	direccion_empleado VARCHAR(40),
	salario INTEGER,
	telefono_empleado VARCHAR(15),
	PRIMARY KEY (id_empleado)
);

DROP TABLE IF EXISTS estacion CASCADE;
CREATE TABLE estacion (
	id_estacion VARCHAR(10),
	nombre_estacion VARCHAR(40),
	tipo_estacion VARCHAR(20),
	direccion_estacion VARCHAR(40),
	empleado_encargado VARCHAR(10),
	FOREIGN KEY (empleado_encargado) REFERENCES empleado(id_empleado),
	PRIMARY KEY (id_estacion)
);

DROP SEQUENCE IF EXISTS tarjeta_seq CASCADE;
CREATE SEQUENCE tarjeta_seq
	INCREMENT BY 1
	NO MAXVALUE
	MINVALUE 1000000
	CACHE 1;

DROP TABLE IF EXISTS tarjeta CASCADE;
CREATE TABLE tarjeta (
	pin_tarjeta INTEGER DEFAULT nextval('tarjeta_seq'::regclass) NOT NULL,
	tipo_tarjeta VARCHAR(20),
	estado_tarjeta VARCHAR(20),
	saldo INTEGER,
	PRIMARY KEY (pin_tarjeta)
);

DROP TABLE IF EXISTS pasajero CASCADE;
CREATE TABLE pasajero (
	id_pasajero VARCHAR(20),
	nombre_pasajero VARCHAR(100),
	estrato_pasajero integer,
	direccion_pasajero VARCHAR(40),
	telefono_pasajero VARCHAR(15),
	genero_pasajero VARCHAR(10),
	PRIMARY KEY (id_pasajero)
);

DROP TABLE IF EXISTS turno CASCADE;
CREATE TABLE turno (
	id_empleado VARCHAR(20),
	id_ruta VARCHAR(10),
	id_bus VARCHAR(10),
	fecha DATE,
	jornada VARCHAR(10),
	FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
	FOREIGN KEY (id_ruta) REFERENCES ruta(id_ruta),
	FOREIGN KEY (id_bus) REFERENCES bus(id_bus),
	UNIQUE (id_empleado, fecha, jornada),
	PRIMARY KEY (id_bus, fecha, jornada)
);

DROP SEQUENCE IF EXISTS quejas_y_reclamos_seq CASCADE;
CREATE SEQUENCE quejas_y_reclamos_seq
	INCREMENT BY 1
	NO MAXVALUE
	NO MINVALUE
	CACHE 1;
DROP TABLE IF EXISTS quejas_y_reclamos CASCADE;
CREATE TABLE quejas_y_reclamos (
	id_pasajero VARCHAR(20),
	id_estacion VARCHAR(10),
	id_empleado VARCHAR(20),
	no_ticket INTEGER DEFAULT nextval('quejas_y_reclamos_seq'::regclass) NOT NULL,
	estado VARCHAR(20),
	descripcion VARCHAR(150),
	fecha DATE,
	FOREIGN KEY (id_pasajero) REFERENCES pasajero(id_pasajero),
	FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
	FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
	PRIMARY KEY (no_ticket)
);

DROP TABLE IF EXISTS medidas_quejas_y_reclamos CASCADE;
CREATE TABLE medidas_quejas_y_reclamos (
	no_ticket INTEGER,
	medida_tomada VARCHAR(30),
	FOREIGN KEY (no_ticket) REFERENCES quejas_y_reclamos(no_ticket),
	PRIMARY KEY (no_ticket, medida_tomada)
);


DROP SEQUENCE IF EXISTS recarga_seq CASCADE;
CREATE SEQUENCE recarga_seq
	INCREMENT BY 1
	NO MAXVALUE
	NO MINVALUE
	CACHE 1;
DROP TABLE IF EXISTS recarga CASCADE;
CREATE TABLE recarga (
	consecutivo INTEGER DEFAULT nextval('recarga_seq'::regclass) NOT NULL,
	pin_tarjeta INTEGER,
	id_estacion VARCHAR(10),
	id_empleado VARCHAR(20),
	fecha TIMESTAMP,
	valor INTEGER,
	FOREIGN KEY (pin_tarjeta) REFERENCES tarjeta(pin_tarjeta),
	FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
	FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
	PRIMARY KEY (consecutivo)
);

DROP TABLE IF EXISTS venta_tarjeta CASCADE;
CREATE TABLE venta_tarjeta (
	pin_tarjeta INTEGER,
	id_empleado VARCHAR(20),
	id_estacion VARCHAR(10),
	id_pasajero VARCHAR(20),
	fecha DATE,
	FOREIGN KEY (pin_tarjeta) REFERENCES tarjeta(pin_tarjeta),
	FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
	FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
	FOREIGN KEY (id_pasajero) REFERENCES pasajero(id_pasajero),
	PRIMARY KEY (pin_tarjeta)
);

DROP TABLE IF EXISTS tarjeta_se_usa_en_ruta CASCADE;
CREATE TABLE tarjeta_se_usa_en_ruta (
	pin_tarjeta INTEGER,
	id_ruta VARCHAR(10),
	fecha TIMESTAMP,
	FOREIGN KEY (pin_tarjeta) REFERENCES tarjeta(pin_tarjeta),
	FOREIGN KEY (id_ruta) REFERENCES ruta(id_ruta),
	PRIMARY KEY (pin_tarjeta, id_ruta, fecha)
);

DROP TABLE IF EXISTS tarjeta_se_usa_en_estacion CASCADE;
CREATE TABLE tarjeta_se_usa_en_estacion (
	pin_tarjeta INTEGER,
	id_estacion VARCHAR(10),
	fecha TIMESTAMP,
	FOREIGN KEY (pin_tarjeta) REFERENCES tarjeta(pin_tarjeta),
	FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
	PRIMARY KEY (pin_tarjeta, id_estacion, fecha)
);

DROP TABLE IF EXISTS ubicaciones_paradas CASCADE;
CREATE TABLE ubicaciones_paradas (
	nombre_parada VARCHAR(100),
	posicionx INTEGER,
	posiciony INTEGER,
	PRIMARY KEY (nombre_parada)
);