package com.emarbox.dto.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageQueryInfo implements Serializable {

	private static final long serialVersionUID = -6134160585264707945L;

	/** 当前页 */
	private int currentPage;

	/** 页面显示数量 1 2 3 4 5 */
	private int pageCount = 5;

	/** 每页显示条数，默认10条 */
	private int pageSize = 10;

	/** 总数量 */
	private int recordCount;

	private int offset;

	private int totalPage;
	
	public PageQueryInfo() {
		super();
	}

	public PageQueryInfo(int currentPage, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		if (recordCount % pageSize == 0) {
			totalPage = recordCount / pageSize;
		} else {
			totalPage = recordCount / pageSize + 1;
		}
		return totalPage;
	}

	public int getCurrentPage() {
		if (currentPage <= 0) {
			currentPage = 1;
		}
		return currentPage;
	}

	public int getOffset() {
		if (currentPage > 0) {
			offset = (currentPage - 1) * pageSize;
		} else {
			offset = 0;
		}
		return offset;
	}


}
