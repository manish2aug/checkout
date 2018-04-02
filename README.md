# Anatwine checkout

An app to calculate final amount for cart by applying different promotions using command line

## Getting Started

The project can be downloaded from github https://github.com/manish2aug/checkout

### Prerequisites

Following are the prerequisites
* java 1.8
* maven
* git

### How to run (including running tests)
```
git clone https://github.com/manish2aug/checkout.git
cd checkout
mvn clean test install
```

### How to use
```
After running steps mentioned in "How to run" section, please run following commands

1. cd target/anatwine/
2. java -jar anatwine.jar AnatwineBasket Trouser

Note: To test expired Trouser offer, change system date to any date of after 10 Apr and tun the above mentioned command 
```

This project runs the following scripts in an in-memory database (h2)
```
CREATE TABLE ITEM (ID INT PRIMARY KEY, NAME VARCHAR(255) UNIQUE, UNIT_PRICE DECIMAL(20, 2));
INSERT INTO ITEM VALUES(1, 'Jacket', '49.9');
INSERT INTO ITEM VALUES(2, 'Trouser', '35.50');
INSERT INTO ITEM VALUES(3, 'Shirt', '12.50');
INSERT INTO ITEM VALUES(4, 'Tie', '9.50');
```

So the valid items to use as program arguments are Jacket, Trouser, Shirt and Tie

System is currently having following promotions:

* **Trouser on 10% off - valid till 10 Apr 2018** 
* **On buying two Shirts a Tie can be bought on 50% off**


