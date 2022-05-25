update organization set site_id = 207 where org_id in (select o.org_id 
from organization o
join organization_address oa on (o.org_id = oa.org_id and oa.address_type = 5)
left join comuni c on (oa.city = c.comune) 
where tipologia = 152 and c.codiceistatasl = '207');

update organization set site_id = 206 where org_id in (select o.org_id 
from organization o
join organization_address oa on (o.org_id = oa.org_id and oa.address_type = 5)
left join comuni c on (oa.city = c.comune) 
where tipologia = 152 and c.codiceistatasl = '206');

update organization set site_id = 205 where org_id in (select o.org_id 
from organization o
join organization_address oa on (o.org_id = oa.org_id and oa.address_type = 5)
left join comuni c on (oa.city = c.comune) 
where tipologia = 152 and c.codiceistatasl = '205');

update organization set site_id = 204 where org_id in (select o.org_id 
from organization o
join organization_address oa on (o.org_id = oa.org_id and oa.address_type = 5)
left join comuni c on (oa.city = c.comune) 
where tipologia = 152 and c.codiceistatasl = '204');

update organization set site_id = 203 where org_id in (select o.org_id 
from organization o
join organization_address oa on (o.org_id = oa.org_id and oa.address_type = 5)
left join comuni c on (oa.city = c.comune) 
where tipologia = 152 and c.codiceistatasl = '203');

update organization set site_id = 202 where org_id in (select o.org_id 
from organization o
join organization_address oa on (o.org_id = oa.org_id and oa.address_type = 5)
left join comuni c on (oa.city = c.comune) 
where tipologia = 152 and c.codiceistatasl = '202');

update organization set site_id = 201 where org_id in (select o.org_id 
from organization o
join organization_address oa on (o.org_id = oa.org_id and oa.address_type = 5)
left join comuni c on (oa.city = c.comune) 
where tipologia = 152 and c.codiceistatasl = '201');