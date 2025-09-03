CREATE TABLE tb_park_lot (
                             idt_park_lot SERIAL PRIMARY KEY,
                             des_license_plate VARCHAR(10) NOT NULL,
                             dtt_parked_time TIMESTAMP WITHOUT TIME ZONE,
                             dtt_exit_time TIMESTAMP WITHOUT TIME ZONE,
                             cod_spot INTEGER,
                             num_total_minutes INTEGER,
                             num_amount_charge NUMERIC(5,2),
                             num_final_price NUMERIC(5,2),
                             num_base_price NUMERIC(5,2),
                             num_duration_limit_minutes INTEGER,
                             des_status VARCHAR(20)
);
