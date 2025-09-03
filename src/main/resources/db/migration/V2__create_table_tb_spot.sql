CREATE TABLE tb_spot (
                         idt_spot INTEGER PRIMARY KEY,
                         cod_sector VARCHAR(1),
                         num_lat NUMERIC(10, 6),
                         num_lng NUMERIC(10, 6),
                         bol_occupied BOOLEAN,
                         CONSTRAINT fk_spot_sector FOREIGN KEY (cod_sector)
                             REFERENCES tb_sector (idt_sector)
);
