CREATE TABLE users (
      id INT AUTO_INCREMENT PRIMARY KEY,
      user_id VARCHAR(15) UNIQUE NOT NULL,
      first_name VARCHAR(20) NOT NULL,
      last_name VARCHAR(50) NOT NULL,
      career VARCHAR(100) DEFAULT NULL,
      manager INT DEFAULT NULL,
      CONSTRAINT `users_to_users` FOREIGN KEY (`manager`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE projects (
      id INT AUTO_INCREMENT PRIMARY KEY,
      project_id VARCHAR(50) UNIQUE NOT NULL,
      project_name VARCHAR(100) NOT NULL,
      project_description VARCHAR(250) NULL
);

CREATE TABLE timesheets (
      id INT AUTO_INCREMENT PRIMARY KEY,
      project_id INT NOT NULL,
      user_id INT NOT NULL,
      date_from DATE NOT NULL,
      date_to DATE NOT NULL,
      duration_min int NOT NULL,
      comments VARCHAR(250) NULL,
      CONSTRAINT `timesheets_to_users`    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
      CONSTRAINT `timesheets_to_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO users (id, user_id, first_name, last_name, career, manager) VALUES
    (1, 'adangote', 'Aliko', 'Dangote', 'CEO', 1),
    (2, 'billy', 'Bill', 'Gates', 'CTO', 1),
    (3, 'falakija', 'Folrunsho', 'Alakija', 'Manager', 2),
    (4, 'alvaro', 'Alvaro', 'Alcedo Moreno', 'Architect', 3);

INSERT INTO projects (id, project_id, project_name, project_description) VALUES
    (1, 'PING', 'Ping', 'Ping microservice'),
    (2, 'SLCK-WEB', 'Slack Web interface', 'Web Interface for Slack Project'),
    (3, 'SLCK-CORE', 'Slack core services', 'Backend services for Slack Projects');

INSERT INTO timesheets (project_id, user_id, date_from, date_to, duration_min) VALUES
    (1, 1, TO_DATE('2021/07/12', 'YYYY/MM/DD'), TO_DATE('2021/07/12', 'YYYY/MM/DD'), 480),
    (1, 1, TO_DATE('2021/07/13', 'YYYY/MM/DD'), TO_DATE('2021/07/14', 'YYYY/MM/DD'), 960),
    (2, 1, TO_DATE('2021/07/15', 'YYYY/MM/DD'), TO_DATE('2021/07/15', 'YYYY/MM/DD'), 480),
    (2, 1, TO_DATE('2021/07/16', 'YYYY/MM/DD'), TO_DATE('2021/07/16', 'YYYY/MM/DD'), 480),
    (1, 2, TO_DATE('2021/07/12', 'YYYY/MM/DD'), TO_DATE('2021/07/16', 'YYYY/MM/DD'), 2400),
    (3, 3, TO_DATE('2021/07/12', 'YYYY/MM/DD'), TO_DATE('2021/07/16', 'YYYY/MM/DD'), 2400),
    (3, 4, TO_DATE('2021/07/12', 'YYYY/MM/DD'), TO_DATE('2021/07/16', 'YYYY/MM/DD'), 2400);