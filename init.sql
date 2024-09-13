-- 스키마 생성
CREATE SCHEMA IF NOT EXISTS ai;
CREATE SCHEMA IF NOT EXISTS company_product;
CREATE SCHEMA IF NOT EXISTS hub;
CREATE SCHEMA IF NOT EXISTS order_delivery;
CREATE SCHEMA IF NOT EXISTS slack;
CREATE SCHEMA IF NOT EXISTS "user";
CREATE SCHEMA IF NOT EXISTS "order";


-- 사용자 생성
CREATE USER ai_user WITH PASSWORD 'ai_user';
CREATE USER company_product_user WITH PASSWORD 'company_product_user';
CREATE USER hub_user WITH PASSWORD 'hub_user';
CREATE USER order_delivery_user WITH PASSWORD 'order_delivery_user';
CREATE USER slack_user WITH PASSWORD 'slack_user';
CREATE USER user_user WITH PASSWORD 'user_user';
CREATE USER order_user WITH PASSWORD 'order_user';

-- 권한 부여
GRANT ALL PRIVILEGES ON SCHEMA ai TO ai_user;
GRANT ALL PRIVILEGES ON SCHEMA company_product TO company_product_user;
GRANT ALL PRIVILEGES ON SCHEMA hub TO hub_user;
GRANT ALL PRIVILEGES ON SCHEMA order_delivery TO order_delivery_user;
GRANT ALL PRIVILEGES ON SCHEMA slack TO slack_user;
GRANT ALL PRIVILEGES ON SCHEMA "user" TO user_user;
GRANT ALL PRIVILEGES ON SCHEMA "order" TO order_user;
