package com.emarbox.dao;

import com.emarbox.entity.nonstandard.*;
import com.emarbox.mapper.nonstandard.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Data
@Repository
public class NonstandardAdDao {

    @Autowired
    private NonstandardAdInfoMapper nonstandardAdInfoMapper;
    @Autowired
    private NonstandardAdMapper nonstandardAdMapper;
    @Autowired
    private NonstandardMediaMapper nonstandardMediaMapper;
    @Autowired
    private NonstandardTrackingMapper nonstandardTrackingMapper;
    
    @Autowired
    private AdvertiserCampaignMapper advertiserCampaignMapper;
    @Autowired
    private AdvertiserCampaignIdSupplierMapper advertiserCampaignIdSupplierMapper;
    @Autowired
    private AdvertiserAdMapper advertiserAdMapper;
    @Autowired
    private AdvertiserAdCreativeMapper advertiserAdCreativeMapper;

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    public List<NonstandardAd> getList() {
    	Example example = new Example(NonstandardAd.class);
    	example.setOrderByClause("id desc");
        return nonstandardAdMapper.selectByExample(example);
    }
    
    public List<NonstandardMedia> getAdMediaAll(){
    	return nonstandardMediaMapper.selectAll();
    }
    
    public NonstandardTracking queryNonstandardTracking(NonstandardTracking nt){
    	List<NonstandardTracking> list = nonstandardTrackingMapper.select(nt);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }

    public Long queryParentUserId(ProjectInfo projectInfo){
        List<ProjectInfo> list = projectInfoMapper.select(projectInfo);
        if(null != list && list.size() > 0){
            return list.get(0).getParentUserId();
        }
        return null;
    }
    
    public NonstandardAd saveNonstandardAd(NonstandardAd ad){
    	nonstandardAdMapper.insertUseGeneratedKeys(ad);
    	return ad;
    }
    
    public int saveNonstandardAdInfo(NonstandardAdInfo nai){
    	return nonstandardAdInfoMapper.insert(nai);
    }
    
    public int saveNonstandardTracking(NonstandardTracking nt){
    	return nonstandardTrackingMapper.insert(nt);
    }

}
