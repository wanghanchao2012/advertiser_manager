package com.emarbox.entity.balance;import java.util.Date;import javax.persistence.Table;import lombok.Data;/******************************************************************************* * javaBeans * pig_project_deposit_log --> PigProjectDepositLog  * <table explanation> * @author 2019-02-18 18:39:45 *  */	@Data@Table(name = "pig_project_deposit_log")public class PigProjectDepositLog implements java.io.Serializable {	/**	 * 	 */	private static final long serialVersionUID = -4384705243472918360L;	//field	/**  **/	private Long id;	/**  **/	private Long projectId;	/**  **/	private String depositAmount;	/** 客户名称 **/	private String customerName;	/** 充值时间 **/	private Date depositTime;	/** 创建人 **/	private String createUser;	/** 创建时间 **/	private Date createTime;	/** 修改人 **/	private String updateUser;	/** 修改时间 **/	private Date updateTime;}