package com.emarbox.dto.common;

import com.emarbox.enums.ErrorInfos;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResponseResult<T> extends ResponseDisputeResult {

	private T data;

	private PageQueryInfo page;

	public T dataContinuable() {
		return data;
	}

	public ResponseResult<T> forOk() {
		this.code = 0;
		this.message = "ok";
		return this;
	}

	public ResponseResult<T> forFail(Integer code, String msg) {
		this.code = code;
		this.message = msg;
		return this;
	}

	public ResponseResult<T> forFail(ErrorInfos errorInfos) {
		this.code = errorInfos.getCode();
		this.message = errorInfos.getDesc();
		return this;
	}

	public ResponseResult(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResponseResult(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
