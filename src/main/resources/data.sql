DROP TABLE IF EXISTS SkuPrice;

CREATE TABLE SkuPrice
(
    skuId     VARCHAR(1) PRIMARY KEY,
    unitPrice DECIMAL NOT NULL
);

INSERT INTO SkuPrice (skuId, unitPrice)
VALUES ('A', 50),
       ('B', 30),
       ('C', 20),
       ('D', 15);

DROP TABLE IF EXISTS Promotion;

CREATE TABLE Promotion
(
    skuTypes     VARCHAR(255) NOT NULL,
    name         VARCHAR(255) NOT NULL,
    priority     int          NOT NULL,
    lotSize      int          NOT NULL,
    discountType VARCHAR(255) NOT NULL,
    value        DECIMAL      NOT NULL,
    active       BOOLEAN
);

INSERT INTO Promotion (skuTypes, name, priority, lotSize, discountType, value, active)
VALUES ('A', '3 of As for 130', 1, 3, 'FIXED_PRICE', 130, TRUE),
       ('B', '2 of Bs for 45', 1, 2, 'FIXED_PRICE', 45, TRUE),
       ('CD', 'C & D for 30', 2, 1, 'FIXED_PRICE', 30, TRUE);
