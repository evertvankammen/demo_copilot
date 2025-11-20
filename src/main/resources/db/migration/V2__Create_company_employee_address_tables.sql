-- Create companies table
CREATE TABLE companies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(1000),
    industry VARCHAR(255) NOT NULL,
    registration_number VARCHAR(255) UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Create addresses table
CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255),
    zip_code VARCHAR(50) NOT NULL,
    country VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Create employees table
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(50),
    position VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    salary DECIMAL(15, 2),
    hire_date DATE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    company_id BIGINT NOT NULL,
    address_id BIGINT,
    CONSTRAINT fk_employee_company FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT fk_employee_address FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- Create indexes
CREATE INDEX idx_employee_email ON employees(email);
CREATE INDEX idx_employee_company ON employees(company_id);

-- Insert sample data
INSERT INTO companies (name, description, industry, registration_number, active, created_at, updated_at)
VALUES 
    ('Tech Solutions Inc', 'Leading technology solutions provider', 'Technology', 'REG-001', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Global Finance Corp', 'International financial services company', 'Finance', 'REG-002', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Healthcare Plus', 'Comprehensive healthcare services', 'Healthcare', 'REG-003', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO addresses (street, city, state, zip_code, country, created_at, updated_at)
VALUES 
    ('123 Main Street', 'New York', 'NY', '10001', 'USA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('456 Oak Avenue', 'San Francisco', 'CA', '94102', 'USA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('789 Pine Road', 'Boston', 'MA', '02101', 'USA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('321 Elm Street', 'Chicago', 'IL', '60601', 'USA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('654 Maple Drive', 'Seattle', 'WA', '98101', 'USA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO employees (first_name, last_name, email, phone_number, position, department, salary, hire_date, active, created_at, updated_at, company_id, address_id)
VALUES 
    ('John', 'Doe', 'john.doe@techsolutions.com', '+1-555-0101', 'Senior Developer', 'Engineering', 95000.00, '2020-01-15', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1),
    ('Jane', 'Smith', 'jane.smith@techsolutions.com', '+1-555-0102', 'Product Manager', 'Product', 110000.00, '2019-06-20', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2),
    ('Mike', 'Johnson', 'mike.johnson@globalfinance.com', '+1-555-0103', 'Financial Analyst', 'Finance', 85000.00, '2021-03-10', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3),
    ('Sarah', 'Williams', 'sarah.williams@globalfinance.com', '+1-555-0104', 'Senior Accountant', 'Accounting', 90000.00, '2018-09-01', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 4),
    ('David', 'Brown', 'david.brown@healthcareplus.com', '+1-555-0105', 'Medical Director', 'Medical', 150000.00, '2017-11-15', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 5);
