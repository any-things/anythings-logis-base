select 
	j.id, j.job_type, j.equip_type, j.equip_cd, j.status 
from 
	cells c
	inner join indicators i on c.domain_id = i.domain_id and c.ind_cd = i.ind_cd
	inner join racks r on c.domain_id = r.domain_id and c.equip_cd = r.rack_cd
	inner join job_batches j on r.domain_id = j.domain_id and r.rack_cd = j.equip_cd
where 
	c.domain_id = :domainId
	and r.stage_cd = :stageCd
	and c.equip_type = 'RACK'
	and j.equip_type = 'RACK'
	and i.gw_cd = :gwCd
	and j.status = 'RUN'
order by
	c.equip_cd asc