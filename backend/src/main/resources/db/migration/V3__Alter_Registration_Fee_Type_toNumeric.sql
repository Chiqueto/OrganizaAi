ALTER TABLE tournaments
ALTER COLUMN registration_fee TYPE numeric(10,2) USING registration_fee::numeric(10,2);
ALTER TABLE tournaments
ALTER COLUMN registration_fee SET NOT NULL;
ALTER TABLE tournaments
ALTER COLUMN registration_fee SET DEFAULT 0.00;