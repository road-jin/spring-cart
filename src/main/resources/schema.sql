CREATE TABLE PRODUCT(
    id      BIGINT          NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20)     NOT NULL,
    image   VARCHAR(1000)   NOT NULL,
    price   BIGINT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE MEMBER(
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    email       VARCHAR(50)     NOT NULL UNIQUE,
    password    VARCHAR(256)    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE CART_ITEM(
    id          BIGINT  NOT NULL AUTO_INCREMENT,
    product_id  BIGINT  NOT NULL,
    owner_id    BIGINT  NOT NULL,
    PRIMARY KEY (id)
);
