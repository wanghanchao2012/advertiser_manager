package com.emarbox.entity.balance;import javax.persistence.Table;import lombok.Data;/******************************************************************************* * javaBeans * pig_advertiser_deposit_log --> PigAdvertiserDepositLog  * <table explanation> * @author 2019-02-18 18:37:03 *  */	@Data@Table(name="pig_advertiser_deposit_log")public class PigAdvertiserDepositLog implements java.io.Serializable {	/**	 * 	 */	private static final long serialVersionUID = 1L;	//field	/**  **/	private Long id;	/**  **/	private Long advertiserId;	/**  **/	private String depositAmount;	/** 客户名称 **/	private String customerName;	/** 充值时间 **/	private Long depositTime;	/** 创建人 **/	private String createUser;	/** 创建时间 **/	private Long createTime;	/** 修改人 **/	private String updateUser;	/** 修改时间 **/	private Long updateTime;	}