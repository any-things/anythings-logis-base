package xyz.anythings.base.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.service.api.IAssortService;
import xyz.anythings.base.service.api.IBatchService;
import xyz.anythings.base.service.api.IBoxingService;
import xyz.anythings.base.service.api.IClassificationService;
import xyz.anythings.base.service.api.IDeviceService;
import xyz.anythings.base.service.api.IIndicationService;
import xyz.anythings.base.service.api.IInstructionService;
import xyz.anythings.base.service.api.IInvoiceNoService;
import xyz.anythings.base.service.api.IJobConfigProfileService;
import xyz.anythings.base.service.api.IJobStatusService;
import xyz.anythings.base.service.api.IPreprocessService;
import xyz.anythings.base.service.api.IReceiveBatchService;
import xyz.anythings.base.service.api.ISkuSearchService;
import xyz.anythings.base.service.api.IStockService;
import xyz.elidom.util.ValueUtil;

/**
 * 작업 유형에 따른 서비스를 찾아주는 컴포넌트
 * 
 * @author shortstop
 */
@Component
public class LogisServiceDispatcher implements BeanFactoryAware {

	/**
	 * BeanFactory
	 */
	protected BeanFactory beanFactory;
	/**
	 * 주문 수신 서비스
	 */
	@Autowired
	private ReceiveBatchService receiveBatchService;
	/**
	 * 설정 셋 서비스
	 */
	@Autowired
	private JobConfigProfileService configSetService;
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
	/**
	 * 장비 서비스
	 */
	@Autowired
	private DeviceService deviceService;
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	/**
	 * 주문 수신 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param batchReceiverName
	 * @return
	 */
	public IReceiveBatchService getReceiveBatchService() {
		return this.receiveBatchService;
	}
	
	/**
	 * 설정 셋 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @return
	 */
	public IJobConfigProfileService getConfigSetService() {
		return this.configSetService;
	}
	
	/**
	 * 배치 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @return
	 */
	public IBatchService getBatchService(JobBatch batch) {
		return this.getBatchService(batch.getJobType());
	}
	
	/**
	 * 작업 유형에 따른 배치 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	public IBatchService getBatchService(JobInstance job) {
		String batchSvcType = job.getJobType().toLowerCase() + "BatchService";
		return (IBatchService)this.beanFactory.getBean(batchSvcType);
	}
	
	/**
	 * 작업 유형에 따른 배치 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	public IBatchService getBatchService(String jobType) {
		String batchSvcType = jobType.toLowerCase() + "BatchService";
		return (IBatchService)this.beanFactory.getBean(batchSvcType);
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
	 * 장비 서비스를 찾아서 리턴
	 * 
	 * @return
	 */
	public IDeviceService getDeviceService() {
		return this.deviceService;
	}
	
	/**
	 * 배치의 작업 유형에 따른 분류 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param batch
	 * @return
	 */
	public IClassificationService getClassificationService(JobBatch batch) {
		return this.getClassificationService(batch.getJobType());
	}
	
	/**
	 * 작업 정보로 분류 서비스를 찾아 리턴
	 * 
	 * @param job
	 * @return
	 */
	public IClassificationService getClassificationService(JobInstance job) {
		return this.getClassificationService(job.getJobType());
	}
	
	/**
	 * 박스 정보로 분류 서비스를 찾아 리턴
	 * 
	 * @param box
	 * @return
	 */
	public IClassificationService getClassificationService(BoxPack box) {
		return this.getClassificationService(box.getJobType());
	}
	
	/**
	 * 작업 유형에 따른 분류 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	public IClassificationService getClassificationService(String jobType) {
		// FIXME 아래 분기하는 것 외 다른 방법 찾기
		String svcType = ValueUtil.isEqualIgnoreCase(jobType, LogisConstants.JOB_TYPE_DPS) ? "PickService" : "AssortService";
		String classSvcType = jobType.toLowerCase() + svcType;
		return (IClassificationService)this.beanFactory.getBean(classSvcType);
	}
	
	/**
	 * 작업 정보로 박싱 서비스를 찾아 리턴
	 * 
	 * @param batch
	 * @return
	 */
	public IBoxingService getBoxingService(JobBatch batch) {
		return this.getBoxingService(batch.getJobType());
	}
	
	/**
	 * 작업 정보로 박싱 서비스를 찾아 리턴
	 * 
	 * @param job
	 * @return
	 */
	public IBoxingService getBoxingService(JobInstance job) {
		return this.getBoxingService(job.getJobType());
	}
	
	/**
	 * 박스 정보로 박싱 서비스를 찾아 리턴
	 * 
	 * @param box
	 * @return
	 */
	public IBoxingService getBoxingService(BoxPack box) {
		return this.getBoxingService(box.getJobType());
	}
	
	/**
	 * 작업 유형에 따른 박싱 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	public IBoxingService getBoxingService(String jobType) {
		String boxingSvcType = jobType.toLowerCase() + "BoxingService";
		return (IBoxingService)this.beanFactory.getBean(boxingSvcType);
	}
	
	/**
	 * 작업 정보로 분류 서비스를 찾아 리턴
	 * 
	 * @param job
	 * @return
	 */
	public IAssortService getPickService(JobInstance job) {
		return this.getPickService(job.getJobType());
	}
	
	/**
	 * 박스 정보로 분류 서비스를 찾아 리턴
	 * 
	 * @param box
	 * @return
	 */
	public IAssortService getPickService(BoxPack box) {
		return this.getPickService(box.getJobType());
	}
	
	/**
	 * 작업 유형에 따른 분류 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	public IAssortService getPickService(String jobType) {
		String assortSvcType = jobType.toLowerCase() + "PickingService";
		return (IAssortService)this.beanFactory.getBean(assortSvcType);
	}
	
	/**
	 * 작업 정보로 분류 서비스를 찾아 리턴
	 * 
	 * @param job
	 * @return
	 */
	public IAssortService getAssortService(JobInstance job) {
		return this.getAssortService(job.getJobType());
	}
	
	/**
	 * 배치 정보로 분류 서비스를 찾아 리턴
	 * 
	 * @param batch
	 * @return
	 */
	public IAssortService getAssortService(JobBatch batch) {
		return this.getAssortService(batch.getJobType());
	}
	
	/**
	 * 작업 유형에 따른 분류 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	public IAssortService getAssortService(String jobType) {
		String assortSvcType = jobType.toLowerCase() + "AssortService";
		return (IAssortService)this.beanFactory.getBean(assortSvcType);
	}

	/**
	 * 배치 작업에 따라작업 상태 서비스 컴포넌트를 찾아 리턴
	 * 
	 * @param batch
	 * @return
	 */
	public IJobStatusService getJobStatusService(JobBatch batch) {
		return this.getJobStatusService(batch.getJobType());
	}
	
	/**
	 * 작업 유형에 따른 작업 상태 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	public IJobStatusService getJobStatusService(String jobType) {
		String jobStatusSvcType = jobType.toLowerCase() + "JobStatusService";
		return (IJobStatusService)this.beanFactory.getBean(jobStatusSvcType);
	}
	
	/**
	 * 배치 작업에 따라 표시기 점,소등 서비스 컴포넌트를 찾아 리턴
	 * 
	 * @param batch
	 * @return
	 */
	public IIndicationService getIndicationService(JobBatch batch) {
		return this.getIndicationService(batch.getJobType());
	}
	
	/**
	 * 작업에 따라 표시기 점,소등 서비스 컴포넌트를 찾아 리턴
	 * 
	 * @param job
	 * @return
	 */
	public IIndicationService getIndicationService(JobInstance job) {
		return this.getIndicationService(job.getJobType());
	}
	
	/**
	 * 작업 유형에 따른 표시기 점,소등 서비스 컴포넌트를 찾아서 리턴
	 * 
	 * @param jobType
	 * @return
	 */
	public IIndicationService getIndicationService(String jobType) {
		String indicationSvcType = jobType.toLowerCase() + "IndicationService";
		return (IIndicationService)this.beanFactory.getBean(indicationSvcType);
	}

}
