UPDATE BOX_ITEMS X
   SET X.STATUS = 'A'
     , X.PICKED_QTY = (SELECT Y.PICKED_QTY FROM ORDERS Y 
                        WHERE Y.DOMAIN_ID = X.DOMAIN_ID
                          AND Y.ID = X.ORDER_ID)
 WHERE X.DOMAIN_ID = :domainId
   AND X.BOX_PACK_ID = :boxPackId
   AND X.ORDER_ID in (:orderIds)
 