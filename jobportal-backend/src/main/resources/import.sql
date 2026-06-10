INSERT INTO account (name, email, password, role, active, activated_at) VALUES ('Admin','admin@gmail.com','$2a$10$e0NRXkZNLmIxrVZ6hVfVQeL0u8Fv4JjXghqhcGdF8xO2gT1N0QG7y','Admin', true, NOW());


INSERT INTO account (email, name, password, role, active, activated_at) VALUES ('Mya','mya@gmail.com','$2a$10$5iU7H4N1x9PxwG8KdQK8UuNnHgR3D3uNqjU8zVbD3sGvH0LzQ3yIu','CompanyAccount', true, NOW());
INSERT INTO account (email, name, password, role, active, activated_at) VALUES ('Thiri','thiri@gmail.com','$2a$10$Zk5H3L0mVxR8bW9KfPp8NuQeHgQ2D3fVbS7kYzC0xR1eT6LwP5bOq','CompanyAccount', true, NOW());


INSERT INTO company (company_id, location, phone, website_url, description, create_at, created_by) VALUES (2,'Yangon, Myanmar', '0943535353','https://www.yuzanahotels.com','Diversified group with interests in construction, real estate, agriculture and hospitality.',NOW(),'Yuzana Company Limited');
INSERT INTO company (company_id, location, phone, website_url, description, create_at, created_by) VALUES (3,'Yangon, Myanmar','0944223344','https://www.maxmyanmar.com','Leading conglomerate involved in construction, trading, hospitality, and transportation across Myanmar.',NOW(),'Max Myanmar Group');

INSERT INTO job (position_name, job_description, salary, job_level, job_type, deleted, company_company_id, create_at, created_by) VALUES ('Backend Developer', 'Experience with Java, Spring Boot, and RESTful APIs', 600000.00, 'Senior', 'Remote', false, 2, NOW(), 'Max Myanmar Group');
INSERT INTO job (position_name, job_description, salary, job_level, job_type, deleted, company_company_id, create_at, created_by) VALUES ('Frontend Developer', 'Experience with React, Next.js, and CSS frameworks', 550000.00, 'Mid', 'Remote', false, 3, NOW(), 'Max Myanmar Group');
INSERT INTO job (position_name, job_description, salary, job_level, job_type, deleted, company_company_id, create_at, created_by) VALUES ('Fullstack Developer', 'Experience with Java, Spring Boot, React, and REST APIs', 650000.00, 'Senior', 'Onsite', false, 2, NOW(), 'Max Myanmar Group');
INSERT INTO job (position_name, job_description, salary, job_level, job_type, deleted, company_company_id, create_at, created_by) VALUES ('DevOps Engineer', 'Experience with Docker, Kubernetes, CI/CD pipelines, and cloud services', 700000.00, 'Senior', 'Hybrid', false, 3, NOW(), 'Max Myanmar Group');
INSERT INTO job (position_name, job_description, salary, job_level, job_type, deleted, company_company_id, create_at, created_by) VALUES ('QA Engineer', 'Experience with manual and automated testing, Selenium, and JUnit', 500000.00, 'Mid', 'Onsite', false, 3, NOW(), 'Max Myanmar Group');

