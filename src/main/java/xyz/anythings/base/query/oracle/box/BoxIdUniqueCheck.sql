#if($uniqueScope == 'G')
SELECT COUNT(1) AS DUP_CNT
  FROM JOB_INPUTS
 WHERE DOMAIN_ID = :domainId
   AND BOX_ID = :boxId
   AND BOX_TYPE = :boxType
#end
#if($uniqueScope == 'D')
SELECT COUNT(1) AS DUP_CNT
  FROM JOB_INPUTS X
     , (SELECT DOMAIN_ID, ID
          FROM JOB_BATCHES
         WHERE (DOMAIN_ID, JOB_DATE) 
                in ( SELECT DOMAIN_ID, JOB_DATE
                       FROM JOB_BATCHES
                      WHERE ID = :batchId ) 
       ) Y
 WHERE X.DOMAIN_ID = Y.DOMAIN_ID
   AND X.BATCH_ID = Y.ID
   AND X.DOMAIN_ID = :domainId
   AND X.BOX_ID = :boxId
   AND X.BOX_TYPE = :boxType
#end
#if($uniqueScope == 'B')
SELECT COUNT(1) AS DUP_CNT
  FROM JOB_INPUTS
 WHERE DOMAIN_ID = :domainId
   AND BATCH_ID = :batchId
   AND BOX_ID = :boxId
   AND BOX_TYPE = :boxType
#end