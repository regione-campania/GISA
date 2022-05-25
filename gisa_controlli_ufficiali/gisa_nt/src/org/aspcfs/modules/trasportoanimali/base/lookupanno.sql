CREATE TABLE lookup_anno
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  short_description character varying(300),
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_anno_pkey PRIMARY KEY (code)
)
WITH (OIDS=FALSE);
ALTER TABLE lookup_anno OWNER TO postgres;

INSERT INTO lookup_anno(
            code, description, short_description, default_item, "level", 
            enabled)
    VALUES (1, 2008, 2008, FALSE, 0, 
            TRUE);


INSERT INTO lookup_anno(
            code, description, short_description, default_item, "level", 
            enabled)
    VALUES (2, 2009, 2009, FALSE, 1, 
            TRUE);

            

INSERT INTO lookup_anno(
            code, description, short_description, default_item, "level", 
            enabled)
    VALUES (3, 2010, 2010, FALSE, 2, 
            TRUE);

            

INSERT INTO lookup_anno(
            code, description, short_description, default_item, "level", 
            enabled)
    VALUES (4, 2011, 2011, FALSE, 3, 
            TRUE);
            

INSERT INTO lookup_anno(
            code, description, short_description, default_item, "level", 
            enabled)
    VALUES (5, 2012, 2012, FALSE, 4, 
            TRUE);
            

INSERT INTO lookup_anno(
            code, description, short_description, default_item, "level", 
            enabled)
    VALUES (6, 2013, 2013, FALSE, 5, 
            TRUE);

            

INSERT INTO lookup_anno(
            code, description, short_description, default_item, "level", 
            enabled)
    VALUES (7, 2014, 2014, FALSE, 6, 
            TRUE);

            

INSERT INTO lookup_anno(
            code, description, short_description, default_item, "level", 
            enabled)
    VALUES (8, 2015, 2015, FALSE, 7, 
            TRUE);