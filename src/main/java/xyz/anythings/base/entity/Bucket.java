package xyz.anythings.base.entity;

import xyz.anythings.sys.util.OrmUtil;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.BeanUtil;

@Table(name = "tb_bucket", idStrategy = GenerationRule.UUID, uniqueFields="domainId,bucketCd", indexes = {
	@Index(name = "ix_tb_bucket_0", columnList = "bucket_cd,domain_id", unique = true)
})
public class Bucket extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 112349584531776092L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "bucket_cd", nullable = false, length = 30)
	private String bucketCd;

	@Column (name = "bucket_nm", nullable = false, length = 100)
	private String bucketNm;

	@Column (name = "bucket_color", nullable = false, length = 10)
	private String bucketColor;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBucketCd() {
		return bucketCd;
	}

	public void setBucketCd(String bucketCd) {
		this.bucketCd = bucketCd;
	}

	public String getBucketNm() {
		return bucketNm;
	}

	public void setBucketNm(String bucketNm) {
		this.bucketNm = bucketNm;
	}

	public String getBucketColor() {
		return bucketColor;
	}

	public void setBucketColor(String bucketColor) {
		this.bucketColor = bucketColor;
	}
	
	/**
	 * 도메인 ID, bucketCd로 버킷 조회
	 * 
	 * @param domainId
	 * @param bucketCd
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Bucket findByBucketCd(Long domainId, String bucketCd, boolean exceptionWhenEmpty) {
		Query query = OrmUtil.newConditionForExecution(domainId);
		query.addFilter("bucketCd", bucketCd);
		Bucket bucket = BeanUtil.get(IQueryManager.class).selectByCondition(Bucket.class, query);
		
		if(bucket == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.Bucket", bucketCd);
		}
		
		return bucket;
	}
	
}
