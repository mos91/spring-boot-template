CREATE SEQUENCE IF NOT EXISTS quote_tbl_seq;

CREATE SEQUENCE IF NOT EXISTS template_tbl_seq;

CREATE TABLE quote_tbl (
  id BIGINT DEFAULT nextval('quote_tbl_seq') PRIMARY KEY NOT NULL,
  type VARCHAR(255),
  quote VARCHAR(255)
);

CREATE TABLE template_tbl (
  id BIGINT DEFAULT nextval('template_tbl_seq') PRIMARY KEY NOT NULL,
  content VARCHAR(255)
)
