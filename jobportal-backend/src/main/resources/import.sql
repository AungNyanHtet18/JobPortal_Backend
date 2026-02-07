INSERT INTO account (name, email, password, role, active, activated_at) VALUES ('Admin','admin@gmail.com','$2a$10$e0NRXkZNLmIxrVZ6hVfVQeL0u8Fv4JjXghqhcGdF8xO2gT1N0QG7y','Admin', true, NOW());

INSERT INTO account (email, name, password, role, active, activated_at) VALUES ('Aung','aung@gmail.com','$2a$10$uWJrtjbf1Y6W4JGf8ZI8l.VgkXlAK8Gx9yUF7/rh1r7uS1fz6IbUq','Applicant', true, NOW());
INSERT INTO account (email, name, password, role, active, activated_at) VALUES ('Sai','sai@gmail.com','$2a$10$KXYB4uXc5y8EsE9/1RO0D.FDjF7rG0W6nqQ8ZxVQ8uU7pXMbZ9s9a','Applicant', true, NOW());
INSERT INTO account (email, name, password, role, active, activated_at) VALUES ('Mya','mya@gmail.com','$2a$10$5iU7H4N1x9PxwG8KdQK8UuNnHgR3D3uNqjU8zVbD3sGvH0LzQ3yIu','CompanyAccount', true, NOW());
INSERT INTO account (email, name, password, role, active, activated_at) VALUES ('Thiri','thiri@gmail.com','$2a$10$Zk5H3L0mVxR8bW9KfPp8NuQeHgQ2D3fVbS7kYzC0xR1eT6LwP5bOq','CompanyAccount', true, NOW());
INSERT INTO account (email, name, password, role, active, activated_at) VALUES ('Kyaw','kyaw@gmail.com','$2a$10$M1tN5P3sQxG8fK9DqJp8RrLgHjR7D3nVbT6zC1xR0yH2uLwP9oIq','Applicant', true, NOW());

INSERT INTO applicant ( applicant_id, gender, skills, highest_educational_attainment, contact_detail, address, deleted, create_at, created_by) VALUES (2, 'Male', 'react, next js', 'computer science', '0954353535', 'Hmawbi Township, Yangon Division', false, NOW(), 'Aung');
INSERT INTO applicant (applicant_id, gender, skills, highest_educational_attainment, contact_detail, address, deleted, create_at, created_by) VALUES (3, 'Female', 'java, spring boot', 'software engineering', '0977889900', 'Insein Township, Yangon Division', false, NOW(), 'Sai');
INSERT INTO applicant (applicant_id, gender, skills, highest_educational_attainment, contact_detail, address, deleted, create_at, created_by) VALUES (6, 'Female', 'html, css, javascript', 'information technology', '0922334455', 'Kamayut Township, Yangon Division', false, NOW(), 'Kyaw');


INSERT INTO company (company_id, location, phone, website_url, description, create_at, created_by) VALUES (4,'Yangon, Myanmar', '0943535353','https://www.yuzanahotels.com','Diversified group with interests in construction, real estate, agriculture and hospitality.',NOW(),'Yuzana Company Limited');
INSERT INTO company (company_id, location, phone, website_url, description, create_at, created_by) VALUES (5,'Yangon, Myanmar','0944223344','https://www.maxmyanmar.com','Leading conglomerate involved in construction, trading, hospitality, and transportation across Myanmar.',NOW(),'Max Myanmar Group');

INSERT INTO job (position_name, job_description, salary, job_level, job_type, deleted, company_company_id, create_at, created_by) VALUES ('Backend Developer', 'Experience with Java, Spring Boot, and RESTful APIs', 600000.00, 'Senior', 'Remote', false, 5, NOW(), 'Max Myanmar Group');
INSERT INTO job (position_name, job_description, salary, job_level, job_type, deleted, company_company_id, create_at, created_by) VALUES ('Frontend Developer', 'Experience with React, Next.js, and CSS frameworks', 550000.00, 'Mid', 'Remote', false, 5, NOW(), 'Max Myanmar Group');
INSERT INTO job (position_name, job_description, salary, job_level, job_type, deleted, company_company_id, create_at, created_by) VALUES ('Fullstack Developer', 'Experience with Java, Spring Boot, React, and REST APIs', 650000.00, 'Senior', 'Onsite', false, 5, NOW(), 'Max Myanmar Group');
INSERT INTO job (position_name, job_description, salary, job_level, job_type, deleted, company_company_id, create_at, created_by) VALUES ('DevOps Engineer', 'Experience with Docker, Kubernetes, CI/CD pipelines, and cloud services', 700000.00, 'Senior', 'Hybrid', false, 5, NOW(), 'Max Myanmar Group');
INSERT INTO job (position_name, job_description, salary, job_level, job_type, deleted, company_company_id, create_at, created_by) VALUES ('QA Engineer', 'Experience with manual and automated testing, Selenium, and JUnit', 500000.00, 'Mid', 'Onsite', false, 5, NOW(), 'Max Myanmar Group');

