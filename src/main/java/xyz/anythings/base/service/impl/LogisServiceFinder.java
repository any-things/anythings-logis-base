package xyz.anythings.base.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.service.api.IBatchService;
import xyz.anythings.base.service.api.IInstructionService;
import xyz.anythings.base.service.api.IInvoiceNoService;
import xyz.anythings.base.service.api.IPreprocessService;
import xyz.anythings.base.service.api.IReceiveBatchService;
import xyz.anythings.base.service.api.ISkuSearchService;
import xyz.anythings.base.service.api.IStockService;

/**
 * 작업 유형에 따른 서비스를 찾아주는 컴포넌트
 * 
 * @author shortstop
 */
@Component
public class LogisServiceFinder implements BeanFactoryAware {

	/**
	 * BeanFactory
	 */
	protected BeanFactory beanFactory;
	/**
	 * 배치 서비스
	 */
	@Autowired
	private BatchService batchService;
	/**
	 * 주문 수신 서비스
	 */
	@Autowired
	private ReceiveBatchService receiveBatchService;
	/**
	 * 상품 조회 서비스
	 */
	@Autowired
	private SkuSearchService skuSearchService;
	/**
	 * 재고 서비스
	 */
	@Autowired
	private StockService stockService;
	/**
	 * 송장 번호 서비스
	 */
	@Autowired
	private InvoiceNoService invoiceNoService;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	/**
	 * 배치의 작업 유형에 따른 배치 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @return
	 */
	public IBatchService getBatchService() {
		return this.batchService;
	}
	
	/**
	 * 수신 유형에 따른 주문 수신 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param batchReceiverName
	 * @return
	 */
	public IReceiveBatchService getReceiveBatchService() {
		return this.receiveBatchService;
	}
	
	/**
	 * 배치의 작업 유형에 따른 주문 가공 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param batch
	 * @return
	 */
	public IPreprocessService getPreprocessService(JobBatch batch) {
		return this.getPreprocessService(batch.getJobType());
	}
	
	/**
	 * 작업 유형에 따른 주문 가공 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	public IPreprocessService getPreprocessService(String jobType) {
		String preprocessSvcType = jobType.toLowerCase() + "PreprocessService";
		return (IPreprocessService)this.beanFactory.getBean(preprocessSvcType);
	}
	
	/**
	 * 배치의 작업 유형에 따른 작업 지시 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param batch
	 * @return
	 */
	public IInstructionService getInstructionService(JobBatch batch) {
		return this.getInstructionService(batch.getJobType());
	}
	
	/**
	 * 작업 유형에 따른 작업 지시 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	public IInstructionService getInstructionService(String jobType) {
		String instSvcType = jobType.toLowerCase() + "InstructionService";
		return (IInstructionService)this.beanFactory.getBean(instSvcType);
	}
	
	/**
	 * 상품 조회 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @return
	 */
	public ISkuSearchService getSkuSearchService() {
		return this.skuSearchService;
	}
	
	/**
	 * 재고 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @return
	 */
	public IStockService getStockService() {
		return this.stockService;
	}
	
	/**
	 * 송장 번호 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @return
	 */
	public IInvoiceNoService getInvoiceNoService() {
		return this.invoiceNoService;
	}
	
	/**
	 * 배치의 작업 유형에 따른 분류 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param batch
	 * @return
	 */
	/*public IAssortService getAssortService(JobBatch batch) {
		return this.getAssortService(batch.getJobType());
	}*/
	
	/**
	 * 작업 정보로 분류 서비스를 찾아 리턴
	 * 
	 * @param job
	 * @return
	 */
	/*public IAssortService getAssortService(JobProcess job) {
		return this.getAssortService(job.getJobType());
	}*/
	
	/**
	 * 박스 정보로 분류 서비스를 찾아 리턴
	 * 
	 * @param box
	 * @return
	 */
	/*public IAssortService getAssortService(BoxResult box) {
		return this.getAssortService(box.getJobType());
	}*/
	
	/**
	 * 작업 유형에 따른 분류 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	/*public IAssortService getAssortService(String jobType) {
		String assortSvcType = jobType.toLowerCase() + "AssortService";
		return (IAssortService)this.beanFactory.getBean(assortSvcType);
	}*/

}
