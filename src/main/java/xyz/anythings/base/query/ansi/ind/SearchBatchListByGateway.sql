select
  *
from (
	select 
		j.id, j.job_type, j.equip_type, j.equip_cd, j.status 
	from 
		cells c
		inner join indicators i on c.domain_id = i.domain_id and c.ind_cd = i.ind_cd
		inner join job_batches j on c.domain_id = j.domain_id and c.equip_cd = j.equip_cd
	where 
		c.domain_id = :domainId
		and j.stage_cd = :stageCd
		and c.equip_type = 'Rack'
		and j.equip_type = 'Rack'
		and i.gw_cd = :gwCd
		and j.status = 'RUN'
)
group by 
  id, job_type, equip_type, equip_cd, status
order by
  equip_cd asc