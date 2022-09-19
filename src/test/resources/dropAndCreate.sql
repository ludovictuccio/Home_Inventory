DROP DATABASE IF EXISTS home_inventory_db_test;
CREATE DATABASE IF NOT EXISTS home_inventory_db_test CHARACTER SET utf8mb4;
USE home_inventory_db_test;

CREATE TABLE fournisseur (
                id BIGINT NOT NULL AUTO_INCREMENT,
                description VARCHAR (250) NOT NULL,
                PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sous_categories (
                id BIGINT NOT NULL AUTO_INCREMENT,
                description VARCHAR (250) NOT NULL,
                PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE categories (
                id BIGINT NOT NULL AUTO_INCREMENT,
                description VARCHAR (250) NOT NULL,
                PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE facture (
                id BIGINT NOT NULL AUTO_INCREMENT,
                description VARCHAR (250) NOT NULL,
                PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE produit (
                id BIGINT NOT NULL AUTO_INCREMENT,
                categories_id BIGINT NOT NULL,
                sous_categories_id BIGINT NOT NULL,
                fournisseur_id BIGINT NOT NULL,
                facture_id BIGINT NOT NULL,
                description VARCHAR (250) NOT NULL,
                date_achat DATE,
                lieu_achat VARCHAR (250),
                quantite SMALLINT,
                pourcent_remise DECIMAL(3,2),
                prix_achat_unitaire_ttc DECIMAL(6,2),
                prix_achat_total_avec_remise_ttc DECIMAL(6,2),
                commentaire VARCHAR (250),
                PRIMARY KEY (id, categories_id, sous_categories_id, fournisseur_id, facture_id)
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

ALTER TABLE produit ADD CONSTRAINT facture_produit_fk
FOREIGN KEY (facture_id)
REFERENCES facture (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

CREATE TABLE users (
				id BIGINT NOT NULL AUTO_INCREMENT,
				username VARCHAR(250) NOT NULL,
                email VARCHAR(250) NOT NULL,
				password VARCHAR(250) NOT NULL,
				role VARCHAR(250) NOT NULL,
				PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

