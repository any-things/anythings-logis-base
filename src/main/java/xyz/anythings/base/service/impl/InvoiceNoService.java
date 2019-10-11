package xyz.anythings.base.service.impl;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.service.api.IInvoiceNoService;

/**
 * IInvoiceNoService 기본 구현
 * 
 * @author shortstop
 */
@Component
public class InvoiceNoService implements IInvoiceNoService {

	@Override
	public int generateInvoiceNo(Long domainId, String stageCd, String comCd, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String nextInvoiceId(JobBatch batch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String nextInvoiceId(BoxPack box) {
		// TODO Auto-generated method stub
		return null;
	}

}
