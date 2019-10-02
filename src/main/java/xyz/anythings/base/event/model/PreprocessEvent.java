//package xyz.anythings.base.event.model;
//
//import java.util.Map;
//
//import xyz.anythings.base.entity.JobBatch;
//import xyz.anythings.sys.event.model.AnyEvent;
//
///**
// * 주문 가공 완료 이벤트
// * 
// * @author shortstop
// */
//public class PreprocessEvent extends AnyEvent {
//
//	/**
//	 * 작업 배치
//	 */
//	private JobBatch batch;
//
//	public PreprocessEvent() {
//	}
//	
//	public PreprocessEvent(Long domainId, JobBatch batch, Map<String, ?> properties) {
//		super();
//		this.setDomainId(domainId);
//		this.setBatch(batch);
//		this.setProperties(properties);
//	}
//	
//	public JobBatch getBatch() {
//		return batch;
//	}
//
//	public void setBatch(JobBatch batch) {
//		this.batch = batch;
//		this.eventType = (batch != null) ? batch.getJobType() : null;
//	}
//	
//}
