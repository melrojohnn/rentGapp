-- Test data for H2 database (compatible syntax)
-- This file is only loaded for tests

-- Insert test customers
INSERT INTO tb_customer (account_id, usr, email, passkey, created_at, updated_at) VALUES
('test-customer-1', 'Test Customer', 'test@example.com', 'hashed_password_test', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert test internal users
INSERT INTO tb_internal_user (account_id, usr, email, passkey, role, created_at, updated_at) VALUES
('test-admin-1', 'Admin User', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert test plans
INSERT INTO tb_plan (plan_name, description, price, duration, plan_type, created_at, updated_at) VALUES
('Test Plan', 'Test plan for testing', 100.00, 'THREE_MONTHS', 'BASIC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
