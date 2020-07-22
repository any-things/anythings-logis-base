SELECT
	S.ID, S.COM_CD, S.SKU_CD, S.SKU_NM, COALESCE(S.SKU_BARCD, ' ') AS SKU_BARCD, COALESCE(S.BOX_BARCD, ' ') AS BOX_BARCD, S.BOX_IN_QTY, S.SKU_WT
FROM
	SKU S INNER JOIN
	(
		SELECT 
			DISTINCT DOMAIN_ID, COM_CD, SKU_CD 
		FROM 
			JOB_INSTANCES 
		WHERE 
			DOMAIN_ID = :domainId 
			AND BATCH_ID = :batchId 
			#if($stationCd)
			AND CELL_CD IN (SELECT CELL_CD FROM CELLS WHERE DOMAIN_ID = :domainId AND STATION_CD = :stationCd) 
			#end
			#if($statuses)
			AND STATUS IN (:statuses) 
			#end
	) J
	ON S.DOMAIN_ID = J.DOMAIN_ID AND S.COM_CD = J.COM_CD AND S.SKU_CD = J.SKU_CD
WHERE
	S.DOMAIN_ID = :domainId
	AND (S.SKU_CD = :skuCd OR S.SKU_BARCD = :skuCd OR S.SKU_BARCD2 = :skuCd OR S.SKU_BARCD3 = :skuCd OR S.BOX_BARCD = :skuCd)