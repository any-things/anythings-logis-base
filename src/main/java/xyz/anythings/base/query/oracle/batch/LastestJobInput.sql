SELECT *
  FROM JOB_INPUTS
 WHERE DOMAIN_ID = :domainId
   AND BATCH_ID = :batchId
   AND INPUT_SEQ = (SELECT MAX(INPUT_SEQ) 
                      FROM JOB_INPUTS
                     WHERE DOMAIN_ID = :domainId
                       AND BATCH_ID = :batchId
                       AND EQUIP_TYPE = :equipType)
   AND EQUIP_TYPE = :equipType
