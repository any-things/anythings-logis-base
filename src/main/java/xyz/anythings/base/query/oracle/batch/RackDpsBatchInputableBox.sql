SELECT COUNT(DISTINCT(ORDER_NO)) AS INPUTABLE_BOX
  FROM JOB_INSTANCES
 WHERE DOMAIN_ID = 1000
   AND (BATCH_ID, EQUIP_TYPE, EQUIP_CD ) 
        in ( SELECT BATCH_ID, 'Rack', RACK_CD
               FROM RACKS
              WHERE DOMAIN_ID = :domainId
                AND RACK_CD = :rackCd
                AND ACTIVE_FLAG = 1)
   AND STATUS IN ('BW','W') -- 박스 요청 대기 / 박스 맵핑 대기 