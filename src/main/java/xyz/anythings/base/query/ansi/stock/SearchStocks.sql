select 
	* 
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
	#if($comCd)
	and com_cd = :comCd
	#end
	#if($skuCd)
	and sku_cd = :skuCd
	#end
	#if($binIndex)
	and bin_index = :binIndex
	#end
	#if($fixedFlag)
	and fixed_flag = :fixedFlag
	#end
	#if($activeFlag)
	and active_flag = :activeFlag
	#end
	#if($status)
	and status = :status
	#end
	#if($minStockQty)
	and min_stock_qty < :minStockQty
	#end
	#if($maxStockQty)
	and max_stock_qty < :maxStockQty
	#end