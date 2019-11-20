#if($mapColumn == 'ORDER') -- 주문 기준 맵핑 
    #if($orderType == 'OT') -- 단수 / 단포 
    #else -- 합포 및 기타 등등 
        SELECT ORDER_NO
          FROM (
                SELECT ORDER_NO
                FROM (
                        SELECT ORDER_NO
                            , COUNT(DISTINCT SKU_CD) AS SKU_CNT
                            , SUM(PICK_QTY) AS PICK_CNT
                        FROM JOB_INSTANCES
                        WHERE DOMAIN_ID = :domainId
                        AND BATCH_ID = :batchId
                        AND INPUT_SEQ = 0
                        AND STATUS = 'W'
                        #if($boxTypeCd)
                        AND BOX_TYPE_CD = :boxTypeCd
                        #end                
                        #if($orderType)
                        AND ORDER_TYPE = :orderType
                        #end                
                        GROUP BY ORDER_NO  
                    )
                ORDER BY SKU_CNT ASC , PICK_CNT ASC
            )
        WHERE ROWNUM = 1 
    #end
#elseif($mapColumn == 'SKU') -- 상품 기준 맵핑 
#elseif($mapColumn == 'SHOP') -- 샵 기준 맵핑 
#end