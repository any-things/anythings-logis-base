SELECT
	S.ID, S.COM_CD, S.SKU_CD, S.SKU_NM, NVL(S.SKU_BARCD, ' ') AS SKU_BARCD, NVL(S.BOX_BARCD, ' ') AS BOX_BARCD, S.BOX_IN_QTY, S.SKU_WT
FROM
	SKU S INNER JOIN
	(
		SELECT 
			DISTINCT COM_CD, SKU_CD 
		FROM 
			JOB_INSTANCES 
		WHERE 
			DOMAIN_ID = :domainId 
			AND BATCH_ID IN (SELECT ID FROM JOB_BATCHES WHERE DOMAIN_ID = :domainId AND BATCH_GROUP_ID = :batchGroupId AND STATUS = 'RUN') 
			#if($stationCd)
			AND STATION_CD = :stationCd 
			#end
			#if($statuses)
			AND STATUS IN (:statuses) 
			#end
	) J
	ON S.DOMAIN_ID = J.DOMAIN_ID AND S.COM_CD = J.COM_CD AND S.SKU_CD = J.SKU_CD
WHERE
	O.DOMAIN_ID = :domainId
	#if($skuCd)
	AND O.SKU_CD = :skuCd
	#end
	#if($skuBarcd)
	O.SKU_BARCD = :skuBarcd
	#end
	#if($skuBarcd2)
	O.SKU_BARCD2 = :skuBarcd2
	#end
	#if($skuBarcd3)
	O.SKU_BARCD3 = :skuBarcd3
	#end
	#if($boxBarcd)
	O.BOX_BARCD = :skuBarcd
	#end