select
	stock.*
from (
	select
		s.equip_type,
		s.equip_cd,
		s.sku_cd,
		s.sku_nm,
		s.order_qty,
		s.alloc_qty,
		s.picked_qty,
		s.stock_qty,
		case 
			when (s.order_qty - s.alloc_qty - s.stock_qty) < 0 then 0
			else s.order_qty - s.alloc_qty - s.stock_qty
		end as need_stock_qty
	from (
		select
			a.equip_type,
			a.equip_cd,
			a.sku_cd, a.sku_nm,
			c.order_qty,
			COALESCE(a.pick_qty, 0) as alloc_qty,
			COALESCE(a.picked_qty, 0) as picked_qty,
			COALESCE(b.stock_qty, 0) as stock_qty
		from
			(select
				j.equip_type,
				j.equip_cd,
				j.sku_cd,
				j.sku_nm,
				COALESCE(sum(j.pick_qty), 0) as pick_qty,
				COALESCE(sum(j.picked_qty), 0) as picked_qty
			from
				job_instances j inner join job_batches b on j.batch_id = b.id
			where
				j.domain_id = :domainId
				and b.job_type = 'DPS'
				and b.status = 'RUN'
				and j.order_type = 'MT'
				#if($equipType)
				and j.equip_type = :equipType
				#end
				#if($equipCd)
				and j.equip_cd = :equipCd
				#end
				#if($skuCd)
				and j.sku_cd like '%'||:skuCd||'%'
				#end
			group by
				j.equip_type, j.equip_cd, j.sku_cd, j.sku_nm) a

			left outer join

			(select 
				i.equip_type, i.equip_cd, i.sku_cd, i.stock_qty
			from (
				select
					equip_type, equip_cd, sku_cd, sum(load_qty) as stock_qty
				from
					stocks
				where 
					domain_id = :domainId
					#if($equipType)
					and equip_type = :equipType
					#end
					#if($equipCd)
					and equip_cd = :equipCd
					#end
					#if($skuCd)
					and sku_cd like '%'||:skuCd||'%'
					#end
				group by
					equip_type, equip_cd, sku_cd
			) i 
			where
				i.stock_qty > 0) b

			on a.equip_type = b.equip_type and a.equip_cd = b.equip_cd and a.sku_cd = b.sku_cd

			left outer join

			(select
				j.equip_type, o.sku_cd, o.sku_nm, sum(o.order_qty) as order_qty
			from
				job_batches j inner join orders o on j.id = o.batch_id
			where
				j.domain_id = :domainId
				and j.job_type = 'DPS'
				and j.status = 'RUN'
				and o.order_type = 'MT'
				#if($equipType)
				and o.equip_type = :equipType
				#end
				#if($skuCd)
				and o.sku_cd like '%'||:skuCd||'%'
				#end
			group by
				j.equip_type, o.sku_cd, o.sku_nm) c

			on a.equip_type = c.equip_type and a.sku_cd = c.sku_cd
	) s
) stock
order by 
	stock.equip_type,
	stock.equip_cd,
	stock.need_stock_qty desc,
	stock.order_qty desc,
	stock.sku_cd