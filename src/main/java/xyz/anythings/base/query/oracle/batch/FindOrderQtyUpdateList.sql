SELECT ID, ORDER_QTY, PICKED_QTY, STATUS
  FROM (
        SELECT ID
             , ORDER_QTY
             , STATUS 
             , PICKED_QTY
             , SUM(ORDER_QTY) OVER ( ORDER BY ORDER_QTY, ID ) AS SUM_QTY
          FROM (
                SELECT ID, BATCH_ID, ORDER_NO, SKU_CD
                     , NVL(PICKED_QTY, 0) AS PICKED_QTY
                     , (ORDER_QTY - NVL(PICKED_QTY, 0) ) AS ORDER_QTY
                     , STATUS
                  FROM ORDERS
                 WHERE DOMAIN_ID = :domainId
                   AND BATCH_ID = :batchId
                   AND ORDER_NO = :orderNo
                   AND COM_CD = :comCd
                   AND SKU_CD = :skuCd
               )
         WHERE ORDER_QTY > 0
       )
 WHERE SUM_QTY <= :resQty
 ORDER BY SUM_QTY