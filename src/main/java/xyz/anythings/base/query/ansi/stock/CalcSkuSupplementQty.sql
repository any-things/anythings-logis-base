select 
    sku_cd, sku_nm, sku_barcd, order_qty, alloc_qty, picked_qty, stock_qty, 
    case when (order_qty - alloc_qty - stock_qty) < 0 then 0 else order_qty - alloc_qty - stock_qty end as input_qty 
from (
    select 
        a.sku_cd, 
        a.sku_nm, 
        b.sku_barcd,
        c.order_qty,
        nvl(a.pick_qty, 0) as alloc_qty,
        nvl(a.picked_qty, 0) as picked_qty,
        nvl(b.stock_qty, 0) as stock_qty
    from
        (select
            j.sku_cd, j.sku_nm, 
            nvl(sum(j.pick_qty), 0) as pick_qty, 
            nvl(sum(j.picked_qty), 0) as picked_qty
        from
            job_instances j inner join job_batches b on j.batch_id = b.id
        where
            j.domain_id = :domainId
            and b.job_type = 'DPS'
            and b.status = 'RUN'
            and j.order_type = 'MT'
            and j.com_cd = :comCd
            and j.sku_cd = :skuCd
	        #if($equipType)
	        and j.equip_type = :equipType
	        #end
        	#if($equipCd)
        	and j.equip_cd = :equipCd
        	#end
        group by
            j.sku_cd, j.sku_nm) a

        left outer join 
        
        (select sku_cd, sku_barcd, stock_qty from (
            select
                 sku_cd, sku_barcd, sum(load_qty) as stock_qty
            from
                stocks
            where 
                domain_id = :domainId
                and com_cd = :comCd
                and sku_cd = :skuCd
		        #if($equipType)
		        and equip_type = :equipType
		        #end
	        	#if($equipCd)
	        	and equip_cd = :equipCd
	        	#end
            group by
                sku_cd, sku_barcd
        ) where
            stock_qty > 0) b
            
        on a.sku_cd = b.sku_cd

		left outer join

        (select
            o.sku_cd, o.sku_nm, sum(o.order_qty) as order_qty
        from
			job_batches j inner join orders o on j.id = o.batch_id
        where
            j.domain_id = :domainId
			and j.job_type = 'DPS'
			and j.status = 'RUN'
			and o.order_type = 'MT'
			and o.com_cd = :comCd
	        and o.sku_cd = :skuCd
	        #if($equipType)
	        and o.equip_type = :equipType
	        #end
	        #if($equipCd)
	        and o.equip_cd = :equipCd
	        #end
        group by
            o.sku_cd, o.sku_nm) c
            	            
        on a.sku_cd = c.sku_cd
)