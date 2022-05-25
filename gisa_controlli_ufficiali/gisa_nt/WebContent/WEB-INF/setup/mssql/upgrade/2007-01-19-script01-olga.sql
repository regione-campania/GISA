ALTER TABLE document_store ADD public_store BIT DEFAULT 0 NOT NULL;
UPDATE document_store SET public_store = 0;
