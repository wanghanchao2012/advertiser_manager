package com.emarbox.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emarbox.mapper.dsp.AccountInfoMapper;
import com.emarbox.mapper.dsp.ProjectSetMapper;
import com.emarbox.mapper.dsp.TtAdvertiserMapper;

import lombok.Data;

@Data
@Repository
public class MapperDspProvider {

	@Autowired
	ProjectSetMapper projectSetMapper;
	@Autowired
	TtAdvertiserMapper ttAdvertiserMapper;
	@Autowired
	AccountInfoMapper accountInfoMapper;

}
