USE home_inventory_db;

INSERT INTO users (id, username, email, password, role) VALUES
(1, "Ludo", "$2a$12$KFnNNQ0WqY.y1mPSXgbcJODFQsYUPhJhkYqVDH9Rv8eb8gDXo2Q5a", "$2a$12$rDcGQ4UBh2NCqPQCpuL56eVi7J6iUHwJnZfelDp0pFcdFGhcaR8s6", "ADMIN"),
(2, "user", "user", "$2a$12$yCXbizMDRWwGgWejK4cK0O4hw8Rskq04ZGKmBkXXPVCL8p4yPMcqC", "USER"),
(3, "admin", "admin", "$2a$12$rDcGQ4UBh2NCqPQCpuL56eVi7J6iUHwJnZfelDp0pFcdFGhcaR8s6", "ADMIN");

INSERT INTO categories (id, description) VALUES
(1, "Maison"),
(2, "Extérieur"),
(3, "Jardin");

INSERT INTO fournisseur (id, description) VALUES
(1, "Castorama");

INSERT INTO facture (id, description) VALUES
(1, "A0001");
