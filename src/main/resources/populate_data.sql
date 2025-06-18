-- Inserir clientes (tb_customer)
INSERT INTO tb_customer (account_id, usr, email, passkey, created_at, updated_at) VALUES
('1d6f3a9c-4c70-4e5b-9b9b-c06e9749e2f9', 'João Silva', 'joao.silva@email.com', 'hashed_password_joao', NOW(), NOW()),
('3b74d1f7-12b4-40bc-8b9f-7b1a1d9e51d4', 'Maria Oliveira', 'maria.oliveira@email.com', 'hashed_password_maria', NOW(), NOW()),
('7f9d4120-9a21-4d47-8546-0e4c2e74d1ae', 'Carlos Santos', 'carlos.santos@email.com', 'hashed_password_carlos', NOW(), NOW());

-- Inserir laptops (tb_laptop)
INSERT INTO tb_laptop (brand, created_at, item_id, model, updated_at, cpu, gpu, hd, ram) VALUES
('Dell', NOW(), 'item-0001', 'XPS 15', NOW(), 'Intel i7', 'NVIDIA RTX 3050', '512GB SSD', '16GB'),
('Apple', NOW(), 'item-0002', 'MacBook Air', NOW(), 'Apple M1', NULL, '256GB SSD', '8GB'),
('Lenovo', NOW(), 'item-0003', 'ThinkPad X1', NOW(), 'Intel i5', 'Intel UHD', '1TB HDD', '8GB');

-- Inserir tablets (tb_tablet)
INSERT INTO tb_tablet (brand, created_at, item_id, model, updated_at, camera, hd, screen) VALUES
('Apple', NOW(), 'item-0101', 'iPad Air', NOW(), '12MP', '256GB SSD', '10.9 inches'),
('Samsung', NOW(), 'item-0102', 'Galaxy Tab S7', NOW(), '13MP', '128GB SSD', '11 inches'),
('Amazon', NOW(), 'item-0103', 'Fire HD 10', NOW(), '5MP', '64GB SSD', '10.1 inches');

-- Inserir smartphones (tb_smartphone)
INSERT INTO tb_smartphone (brand, created_at, item_id, model, updated_at, camera, hd, screen) VALUES
('Apple', NOW(), 'item-0201', 'iPhone 13', NOW(), '12MP', '128GB', '6.1 inches'),
('Samsung', NOW(), 'item-0202', 'Galaxy S21', NOW(), '64MP', '256GB', '6.2 inches'),
('Google', NOW(), 'item-0203', 'Pixel 6', NOW(), '50MP', '128GB', '6.4 inches');

-- Inserir planos (tb_plan) - Agora incluindo a coluna duration e plan_type obrigatórias
INSERT INTO tb_plan (plan_name, description, price, duration, plan_type, created_at, updated_at) VALUES
('Basic 3 Meses', 'Somente 1 equipamento (laptop, tablet ou smartphone) - 3 meses', 100.00, 'THREE_MONTHS', 'BASIC', now(), now()),
('Standard 6 Meses', '2 equipamentos (qualquer combinação de laptop, tablet, smartphone) - 6 meses', 200.00, 'SIX_MONTHS', 'STANDARD', now(), now()),
('Premium 12 Meses', '3 equipamentos (laptop, tablet e smartphone) - 12 meses', 300.00, 'TWELVE_MONTHS', 'PREMIUM', now(), now());

-- Inserir usuários internos (tb_internal_user)
INSERT INTO tb_internal_user (account_id, usr, email, passkey, role, created_at, updated_at) VALUES
('3fa85f64-5717-4562-b3fc-2c963f66afa6', 'Admin User', 'admin@example.com', 'senha_criptografada_aqui', 'ADMIN', now(), now()),
('7c9e6679-7425-40de-944b-e07fc1f90ae7', 'Support User', 'support@example.com', 'senha_criptografada_aqui', 'SUPPORT', now(), now()),
('2f1e7b14-67e6-4e12-b8fa-5f93efb7d7e1', 'Sales User', 'sales@example.com', 'senha_criptografada_aqui', 'SALES', now(), now());

UPDATE tb_internal_user SET account_id = '3fa85f64-5717-4562-b3fc-2c963f66afa6' WHERE usr = 'Admin User';
UPDATE tb_internal_user SET account_id = '7c9e6679-7425-40de-944b-e07fc1f90ae7' WHERE usr = 'Support User';
UPDATE tb_internal_user SET account_id = '2f1e7b14-67e6-4e12-b8fa-5f93efb7d7e1' WHERE usr = 'Sales User';

UPDATE tb_customer
SET account_id = '1d6f3a9c-4c70-4e5b-9b9b-c06e9749e2f9'
WHERE account_id = 'acc-0001';

UPDATE tb_customer
SET account_id = '3b74d1f7-12b4-40bc-8b9f-7b1a1d9e51d4'
WHERE account_id = 'acc-0002';

UPDATE tb_customer
SET account_id = '7f9d4120-9a21-4d47-8546-0e4c2e74d1ae'
WHERE account_id = 'acc-0003';
