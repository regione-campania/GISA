update "access" set site_id = (select contact.site_id FROM contact WHERE "access".user_id = contact.user_id);

