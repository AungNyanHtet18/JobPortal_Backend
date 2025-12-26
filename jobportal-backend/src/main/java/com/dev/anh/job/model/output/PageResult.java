package com.dev.anh.job.model.output;

import java.util.List;

public record PageResult<T> (
	List<T> list,	
	PageInfo pageInfo) {

}
