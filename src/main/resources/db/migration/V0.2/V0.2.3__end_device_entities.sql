CREATE TABLE IF NOT EXISTS customer.end_device (
  uuid UUID PRIMARY KEY NOT NULL,
  description TEXT,
  published TIMESTAMP,
  self_link_href TEXT,
  up_link_href TEXT,
  updated TIMESTAMP,
  type TEXT,
  utc_number TEXT,
  serial_number TEXT,
  lot_number TEXT,
  purchase_price BIGINT,
  critical BOOLEAN,
  electronic_address_id BIGINT REFERENCES customer.electronic_address ON DELETE CASCADE,
  manufactured_date BIGINT,
  purchase_date BIGINT,
  received_date BIGINT,
  installation_date BIGINT,
  removal_date BIGINT,
  retired_date BIGINT,
  acceptance_type TEXT,
  acceptance_success BOOLEAN,
  acceptance_date_time BIGINT,
  initial_condition TEXT,
  initial_loss_of_life SMALLINT,
  status_id BIGINT REFERENCES customer.status ON DELETE CASCADE,
  is_virtual BOOLEAN,
  is_pan BOOLEAN,
  install_code TEXT,
  amr_system TEXT
);
