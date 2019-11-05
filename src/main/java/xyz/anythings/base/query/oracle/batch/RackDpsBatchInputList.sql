SELECT BATCH_ID, INPUT_SEQ, ORDER_NO, BOX_ID, BOX_TYPE
     , SKU_QTY, PLAN_QTY, RESULT_QTY
     , DECODE(PLAN_QTY , RESULT_QTY , '1', '0' ) AS STATUS
  FROM (
	     SELECT BATCH_ID, INPUT_SEQ, ORDER_NO, BOX_ID, BOX_TYPE_CD AS BOX_TYPE
	         , COUNT(1) AS SKU_QTY
	         , SUM(PICK_QTY) AS PLAN_QTY 
	         , NVL(SUM(PICKED_QTY),0) AS RESULT_QTY
	      FROM (
	            SELECT BATCH_ID, ORDER_NO, INPUT_SEQ, BOX_ID, BOX_TYPE_CD
	                 , COM_CD, SKU_CD
	                 , SUM(PICK_QTY) AS PICK_QTY 
	                 , NVL(SUM(PICKED_QTY),0) AS PICKED_QTY
	              FROM JOB_INSTANCES
	             WHERE DOMAIN_ID = :domainId
	               AND BATCH_ID = :batchId
	               AND EQUIP_TYPE = :equipType
	               AND EQUIP_CD = :equipCd
--	               AND STATUS != 'BW'
	             GROUP BY BATCH_ID, ORDER_NO, INPUT_SEQ, BOX_ID, BOX_TYPE_CD, COM_CD, SKU_CD
	           )
	     GROUP BY BATCH_ID, ORDER_NO, INPUT_SEQ, BOX_ID, BOX_TYPE_CD  
       )