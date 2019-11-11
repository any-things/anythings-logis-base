UPDATE JOB_INSTANCES X
   SET X.INPUT_SEQ = :inputSeq
     , X.BOX_ID = :boxId
     , X.COLOR_CD = :colorCd
     , X.STATUS = :status
     , X.INPUT_AT = :inputAt
     , X.BOX_PACK_ID = :boxPackId
     , X.UPDATER_ID = :userId
 WHERE X.DOMAIN_ID = :domainId
   AND X.BATCH_ID = :batchId
   AND X.EQUIP_TYPE = :equipType
   AND X.ORDER_NO = :orderNo
   AND EXISTS ( SELECT 1
                  FROM CELLS Y
                 WHERE Y.DOMAIN_ID = X.DOMAIN_ID
                   AND Y.EQUIP_TYPE = X.EQUIP_TYPE
                   AND Y.EQUIP_CD = X.EQUIP_CD
                   AND Y.CELL_CD = X.SUB_EQUIP_CD
                   AND Y.STATION_CD = :stationCd )