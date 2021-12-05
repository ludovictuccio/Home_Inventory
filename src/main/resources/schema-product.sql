CREATE DATABASE home_inventory_db CHARACTER SET utf8mb4;
USE home_inventory_db;

CREATE TABLE fournisseur (
                id INT AUTO_INCREMENT NOT NULL,
                description VARCHAR (250) NOT NULL,
                PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sous_categories (
                id INT AUTO_INCREMENT NOT NULL,
                description VARCHAR (250) NOT NULL,
                PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;


CREATE TABLE categories (
                id INT AUTO_INCREMENT NOT NULL,
                description VARCHAR (250) NOT NULL,
                PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;


CREATE TABLE produit (
                id INT NOT NULL,
                categories_id INT NOT NULL,
                sous_categories_id INT NOT NULL,
                fournisseur_id INT NOT NULL,
                description VARCHAR (250) NOT NULL,
                date_achat DATE,
                lieu_achat VARCHAR (250),
                no_facture VARCHAR(250) ,
                quantite SMALLINT,
                pourcent_remise TINYINT,
                prix_achat_unitaire_ttc INT,
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
