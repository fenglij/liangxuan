CREATE DATABASE IF NOT EXISTS liangxuan DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE liangxuan;

CREATE TABLE IF NOT EXISTS lx_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    account VARCHAR(190) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    UNIQUE KEY uk_lx_user_account (account)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS lx_company (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    website VARCHAR(255) NOT NULL,
    product VARCHAR(500) NOT NULL,
    start_time CHAR(5) NOT NULL,
    end_time CHAR(5) NOT NULL,
    work_days TINYINT NOT NULL,
    likes INT NOT NULL DEFAULT 0,
    dislikes INT NOT NULL DEFAULT 0,
    submitted_by BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    KEY idx_lx_company_created_at (created_at),
    CONSTRAINT fk_lx_company_user FOREIGN KEY (submitted_by) REFERENCES lx_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS lx_company_vote (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    vote_type VARCHAR(16) NOT NULL,
    UNIQUE KEY uk_lx_company_vote_user (company_id, user_id),
    CONSTRAINT fk_lx_company_vote_company FOREIGN KEY (company_id) REFERENCES lx_company (id),
    CONSTRAINT fk_lx_company_vote_user FOREIGN KEY (user_id) REFERENCES lx_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
