DROP DATABASE IF EXISTS home_inventory_db_test;
CREATE DATABASE home_inventory_db_test CHARACTER SET utf8mb4;
USE home_inventory_db_test;

CREATE TABLE fournisseur (
                id INT NOT NULL AUTO_INCREMENT,
                description VARCHAR (250) NOT NULL,
                PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sous_categories (
                id INT NOT NULL AUTO_INCREMENT,
                description VARCHAR (250) NOT NULL,
                PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE categories (
                id INT NOT NULL AUTO_INCREMENT,
                description VARCHAR (250) NOT NULL,
                PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE produit (
                id INT NOT NULL AUTO_INCREMENT,
                categories_id INT NOT NULL,
                sous_categories_id INT NOT NULL,
                fournisseur_id INT NOT NULL,
                description VARCHAR (250) NOT NULL,
                date_achat DATE,
                lieu_achat VARCHAR (250),
                no_facture VARCHAR(250) ,
                quantite SMALLINT,
                pourcent_remise DECIMAL(3,2),
                prix_achat_unitaire_ttc DECIMAL(6,2),
                commentaire VARCHAR (250),
                document_photo VARCHAR (250),
                PRIMARY KEY (id, categories_id, sous_categories_id, fournisseur_id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

ALTER TABLE produit ADD CONSTRAINT fournisseur_produit_fk
FOREIGN KEY (fournisseur_id)
REFERENCES fournisseur (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE produit ADD CONSTRAINT sous_categories_produit_fk
FOREIGN KEY (sous_categories_id)
REFERENCES sous_categories (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE produit ADD CONSTRAINT categories_produit_fk
FOREIGN KEY (categories_id)
REFERENCES categories (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

CREATE TABLE users (
				id INTEGER NOT NULL AUTO_INCREMENT,
				username VARCHAR(250) NOT NULL,
                email VARCHAR(250) NOT NULL,
				password VARCHAR(250) NOT NULL,
				role VARCHAR(250) NOT NULL,
				PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO users (id, username, email, password, role) VALUES
(1, "Ludo", "$2a$12$KFnNNQ0WqY.y1mPSXgbcJODFQsYUPhJhkYqVDH9Rv8eb8gDXo2Q5a", "$2a$12$rDcGQ4UBh2NCqPQCpuL56eVi7J6iUHwJnZfelDp0pFcdFGhcaR8s6", "ADMIN"),
(2, "user", "user", "$2a$12$yCXbizMDRWwGgWejK4cK0O4hw8Rskq04ZGKmBkXXPVCL8p4yPMcqC", "USER"),
(3, "admin", "admin", "$2a$12$rDcGQ4UBh2NCqPQCpuL56eVi7J6iUHwJnZfelDp0pFcdFGhcaR8s6", "ADMIN");

