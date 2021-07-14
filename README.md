# Promotion Engine implementation using Spring boot

This is a simple Rest API developed using Spring boot framework to implement promotion engine for shopping cart checkout
process.

# Problem Statement
We need you to implement a simple promotion engine for a checkout process. Our Cart contains a list of single character
SKU ids (A, B, C.	) over which the promotion engine will need to run.

The promotion engine will need to calculate the total order value after applying the 2 promotion types

•	buy 'n' items of a SKU for a fixed price (3 A's for 130)

•	buy SKU 1 & SKU 2 for a fixed price ( C + D = 30 )

The promotion engine should be modular to allow for more promotion types to be added at a later date
(e.g. a future promotion could be x% of a SKU unit price). For this coding exercise you can assume that the promotions
will be mutually exclusive; in other words if one is applied the other promotions will not apply
Test Setup

Unit price for SKU IDs

A	50

B	30

C	20

D	15

Active Promotions

3 of A's for 130

2 of B's for 45 C & D for 30
