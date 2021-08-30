# Promotion Engine implementation using Spring boot

This is a simple Rest API developed using Spring boot framework to implement promotion engine for shopping cart checkout
process.

# Problem Statement

We need you to implement a simple promotion engine for a checkout process. Our Cart contains a list of single character
SKU ids (A, B, C.    ) over which the promotion engine will need to run.

The promotion engine will need to calculate the total order value after applying the 2 promotion types

• buy 'n' items of a SKU for a fixed price (3 A's for 130)

• buy SKU 1 & SKU 2 for a fixed price ( C + D = 30 )

The promotion engine should be modular to allow for more promotion types to be added at a later date
(e.g. a future promotion could be x% of a SKU unit price). For this coding exercise you can assume that the promotions
will be mutually exclusive; in other words if one is applied the other promotions will not apply Test Setup

Unit price for SKU IDs

A 50

B 30

C 20

D 15

Active Promotions

3 of A's for 130

2 of B's for 45 C & D for 30

## How to Run

* Clone this repository
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by below command in service folder

```
        mvn spring-boot:run
```

* Check the stdout or boot_example.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
2021-07-14 16:00:27.596  INFO 15840 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-07-14 16:00:27.615  INFO 15840 --- [           main] c.e.p.PromotionEngineApplication         : Started PromotionEngineApplication in 7.938 seconds (JVM running for 8.826)
```

API documentation in exposed at http://localhost:8080/swagger-ui.html

## About the Service

The service is just a simple shopping cart checkout REST service. It uses an in-memory database (H2) to store the data.
You can call the /promotionengine/v1/checkout endpoint with the StockKeepingUnits and corresponding quantity as payload
The API response object is a Shopping Cart Object with content type as application/json

The promotion engine logic works on the Unit prices and Promotion objects stored in H2 database

Promotion object has following attributes:

skuTypes : String containing the (SKU)s on which this promotion is applicable

name     : Name of the promotion

priority : priority of the promotion, when more than one promotion is applicable to particular SKU Id Only the promotion

with highest priority among those will be applied.

lotSize  : the number of items that should be present in cart for the (SKU)s, so that this promotion is applicable

discountType : FIXED_PRICE,PERCENTAGE,DEDUCTION as of now only FIXED price had been implemented, rest can be implemented
using same oBject structure, bu changing the calculation logic in CheckoutServiceImpl

value   : discount value -> absolute value for FIXED_PRICE, percentage value for PERCENTAGE, absolute value for
DEDUCTION

active  : whether the promotion is active

### Database scripts for the data model and test data

```
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
```

### Sample Payload

```
{
    "stockKeepingUnits": [
        {
            "skuId": "A",
            "quantity": 3
        },
        {
            "skuId": "B",
            "quantity": 5
        },
        {
            "skuId": "C",
            "quantity": 1
        },
        {
            "skuId": "D",
            "quantity": 2
        }
    ]
}
```

### Sample Response

```
{
    "shoppingCartItems": [
        {
            "sku": {
                "skuId": "A",
                "quantity": 3
            },
            "cartItemPrice": 130,
            "promotionName": "3 of As for 130"
        },
        {
            "sku": {
                "skuId": "B",
                "quantity": 4
            },
            "cartItemPrice": 90,
            "promotionName": "2 of Bs for 45"
        },
        {
            "sku": {
                "skuId": "B",
                "quantity": 1
            },
            "cartItemPrice": 30,
            "promotionName": null
        },
        {
            "sku": {
                "skuId": "C",
                "quantity": 1
            },
            "cartItemPrice": 0,
            "promotionName": "C & D for 30"
        },
        {
            "sku": {
                "skuId": "D",
                "quantity": 1
            },
            "cartItemPrice": 15,
            "promotionName": null
        },
        {
            "sku": {
                "skuId": "D",
                "quantity": 1
            },
            "cartItemPrice": 30,
            "promotionName": "C & D for 30"
        }
    ],
    "priceBeforeDiscount": 350,
    "discount": 55,
    "totalPrice": 295
}
```

### curl command with sample payload

```
curl -L -X POST 'http://localhost:8080/promotionengine/v1/checkout' \
-H 'Content-Type: application/json' \
--data-raw '{
    "stockKeepingUnits": [
        {
            "skuId": "A",
            "quantity": 3
        },
        {
            "skuId": "B",
            "quantity": 5
        },
        {
            "skuId": "C",
            "quantity": 1
        },
        {
            "skuId": "D",
            "quantity": 2
        }
    ]
}'
```

# Questions and Comments: pallavmaurya@gmail.com

