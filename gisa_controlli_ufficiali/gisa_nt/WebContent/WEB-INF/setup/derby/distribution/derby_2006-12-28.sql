CREATE TABLE lookup_account_stage (
  code INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT '0',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT '1',
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE organization
  ADD COLUMN stage_id INTEGER REFERENCES lookup_account_stage(code);

UPDATE permission SET "active" = '1' WHERE permission = 'product-catalog-product';
