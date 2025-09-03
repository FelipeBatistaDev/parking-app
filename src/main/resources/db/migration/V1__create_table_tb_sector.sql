CREATE TABLE tb_sector (
                           idt_sector VARCHAR(1) PRIMARY KEY,
                           num_base_price NUMERIC(10, 2),
                           num_max_capacity INTEGER,
                           hor_open_hour TIME,
                           hor_close_hour TIME,
                           num_duration_limit_minutes INTEGER
);
