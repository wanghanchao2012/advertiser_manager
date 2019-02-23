package com.emarbox.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.emarbox.entity.nonstandard.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.druid.util.StringUtils;
import com.emarbox.dao.NonstandardAdDao;
import com.emarbox.dto.common.ResponseResult;
import com.emarbox.dto.nonstandard.NonstandardDataQuery;
import com.emarbox.dto.nonstandard.NonstandardHtmlDataVo;
import com.emarbox.dto.nonstandard.NonstandardInfoQuery;
import com.emarbox.dto.nonstandard.NonstandardInfoVo;
import com.emarbox.dto.nonstandard.NonstandardProjectChannelVo;
import com.emarbox.entity.EmarboxProject;
import com.emarbox.mapper.auth.EmarboxProjectMapper;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class NonstandardAdService {
	
	@Value("${nonstandard.list.preview}")
	private String imgPreviewUrl;
	@Value("${nonstandard.tracking.base}")
	private String trackingUrl;
	@Value("${nonstandard.tracking.clickUrl}")
	private String clkUrl;
	@Value("${nonstandard.tracking.etc}")
	private String etc;
	@Value("${nonstandard.tracking.impUrl}")
	private String impUrl;
	@Value("${nonstandard.tracking.impKey}")
	private String impKey;

    @Autowired
    NonstandardAdDao nonstandardAdDao;

    @Autowired
    EmarboxProjectMapper emarboxProjectMapper;

	@Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult<List<NonstandardAd>> getList() {
        ResponseResult.ResponseResultBuilder<List<NonstandardAd>> builder = ResponseResult.builder();
        List<NonstandardAd> list = nonstandardAdDao.getList();
        for(NonstandardAd ad : list){
        	ad.setViewUrl(imgPreviewUrl);
        }
        
        builder.data(list);
        return builder.build().forOk();
    }

    public ResponseResult<NonstandardProjectChannelVo> getProjectChannel(){
    	NonstandardProjectChannelVo npc = new NonstandardProjectChannelVo();
    	ResponseResult.ResponseResultBuilder<NonstandardProjectChannelVo> builder = ResponseResult.builder();
    	//处理项目的下拉框内容初始化数据
    	List<EmarboxProject> pList = emarboxProjectMapper.selectAll();
    	if(null != pList && pList.size() > 0){
    		List<Map<String,String>> project = new ArrayList<Map<String,String>>();
    		for(EmarboxProject p : pList){
    			Map<String,String> m = new HashMap<String,String>();
    			m.put("id", p.getId().toString());
    			m.put("name", p.getProjectName());
    			project.add(m);
    		}
    		npc.setProject(project);
    	}
    	
    	//处理渠道下拉框内容初始化
    	List<NonstandardMedia> mList = nonstandardAdDao.getAdMediaAll();
    	if(null != mList && mList.size() > 0){
    		List<Map<String,String>> channel = new ArrayList<Map<String,String>>();
    		for(NonstandardMedia m : mList){
    			Map<String,String> mm = new HashMap<String,String>();
    			mm.put("id", m.getId().toString());
    			mm.put("name", m.getMediaName());
    			channel.add(mm);
    		}
    		npc.setChannel(channel);
    	}
        
        builder.data(npc);
        return builder.build().forOk();
    }
    
    @Transactional
    public ResponseResult<List<String>> creativeTracking(NonstandardInfoQuery query){
    	ResponseResult.ResponseResultBuilder<List<String>> builder = ResponseResult.builder();
    	List<String> urlList = new ArrayList<String>();
    	if(null != query){
    		//首先验证source是否存在
    		List<NonstandardDataQuery> ndqList = query.getAdData();
    		if(null != ndqList && ndqList.size() > 0){
    			for(NonstandardDataQuery ndq : ndqList){
    				String trafficSource = creativeSource(query,ndq);
    				NonstandardTracking ntQuery = new NonstandardTracking();
    				ntQuery.setTrafficSource(trafficSource);
    				
    				NonstandardTracking nt = nonstandardAdDao.queryNonstandardTracking(ntQuery);
    				if(null != nt){//不为null，则表示存在，只需要返回查询结果的url即可
    					urlList.add(nt.getTrafficUrl());
    				}else{//等于null，则需要创建
    					//保存广告位基本信息
    		    		NonstandardAd ad = new NonstandardAd();
    		    		ad.setLandingpageName(query.getLandingpageName());
    		    		ad.setLandingpageUrl(query.getLandingpageUrl());
    		    		ad.setAdType(Integer.valueOf(query.getAdType()));
    		    		ad.setProjectId(Long.valueOf(query.getProjectId()));
    		    		ad.setChannelId(Long.valueOf(query.getChannelId()));
    		    		ad.setAdStatus(0);//设置为“未保存”状态
    		    		ad.setCreateTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
    		    		ad = nonstandardAdDao.saveNonstandardAd(ad);
    		    		
    		    		//保存广告位中广告的详细信息
    		    		NonstandardAdInfo adi = new NonstandardAdInfo();
    		    		//保存默认的活动，创意，广告
    		        	Map<String,String> ccMap =  createUrlId(query);
    		    		adi.setAdId(ad.getId());
    		    		adi.setCampaignId(Long.valueOf(ccMap.get("campaignId")));
    		    		adi.setCreativeId(Long.valueOf(ccMap.get("creativeId")));
    		    		adi.setAdType(Integer.valueOf(query.getAdType()));
    		    		//拼接点击链接进行保存
    		    		adi.setClickUrl(creativeClickUrl(query,ndq,ccMap));
    		    		//拼接展现链接进行保存
    		    		adi.setImpUrl(creativeImpressionUrl(query,ccMap));
    		    		adi.setAdDescribe(ndq.getDescribe());
    		    		adi.setImgUrl(ndq.getImgUrl());
    		    		adi.setTitle(ndq.getTitle());
    		    		adi.setAdStatus(0);//设置为“未保存”状态
    		    		adi.setCreateTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
    		    		nonstandardAdDao.saveNonstandardAdInfo(adi);
    		    		
    		    		NonstandardTracking nti = new NonstandardTracking();
    		    		nti.setAdId(ad.getId());
    		    		nti.setChannelId(ad.getChannelId());
    		    		nti.setProjectId(ad.getProjectId());
    		    		nti.setAdStatus(0);//设置为“未保存”状态
    		    		nti.setTrafficSource(trafficSource);
    		    		nti.setTrafficUrl(trackingUrl+"a="+ad.getChannelId().toString()+"&p="+ad.getProjectId().toString()+"&source="+trafficSource);
    		    		nti.setCreateTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
    		    		nonstandardAdDao.saveNonstandardTracking(nti);
    		    		urlList.add(nti.getTrafficUrl());
    				}
    			}
    		}
    	}
    	
    	builder.data(urlList);
        return builder.build().forOk();
    }

    /**
     * 创建默认的活动，广告，创意信息
     * @param query
     * @return
     */
    @Transactional
    public Map<String,String> createUrlId(NonstandardInfoQuery query){
    	Map<String,String> urlMap = new HashMap<String,String>();
    	//得到account_id账号
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectId(Long.valueOf(query.getProjectId()));
		Long accountId =  nonstandardAdDao.queryParentUserId(projectInfo);

    	//保存默认活动信息
    	AdvertiserCampaign ac = new AdvertiserCampaign();
    	ac.setCampaignName(query.getLandingpageName());
    	ac.setProjectId(Long.valueOf(query.getProjectId()));
    	ac.setAccountId(accountId);
    	ac.setCampaignType("RTB");
    	ac.setProductType("LINK");
    	ac.setDailyBudget(99999999.00);
    	ac.setBiddingType("SMOOTH");
    	ac.setCampaignStatus("NORMAL");
    	ac.setCreateUser("admin");
    	ac.setCreateTime(new Date());
    	nonstandardAdDao.getAdvertiserCampaignMapper().insertUseGeneratedKeys(ac);
    	
    	//保存默认广告信息
    	AdvertiserCampaignIdSupplier aci = new AdvertiserCampaignIdSupplier();
    	nonstandardAdDao.getAdvertiserCampaignIdSupplierMapper().insertUseGeneratedKeys(aci);
    	ZoneId zoneId = ZoneId.systemDefault();
    	AdvertiserAd aa = new AdvertiserAd();
    	aa.setAdId(aci.getId());
    	aa.setProductUrl(query.getLandingpageUrl());
    	aa.setAccountId(accountId);
    	aa.setProjectId(Long.valueOf(query.getProjectId()));
    	aa.setStartTime(new Date());
    	aa.setEndTime(Date.from(LocalDateTime.now().minus(365, ChronoUnit.DAYS).atZone(zoneId).toInstant()));
    	aa.setScheduleTime("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
    	aa.setFeeType("CPM");
    	aa.setChargeType("CHARGE_CPM");
    	aa.setOptimizationGoal("ORDER");
    	aa.setBidPrice(1.0);
    	aa.setAdName(query.getLandingpageName());
    	aa.setCampaignId(ac.getId());
    	aa.setAdStatus("NORMAL");
    	nonstandardAdDao.getAdvertiserAdMapper().insertUseGeneratedKeys(aa);
    	
    	//保存默认创意信息
    	AdvertiserAdCreative aac = new AdvertiserAdCreative();
    	aac.setAccountId(Long.valueOf(query.getProjectId()));
    	aac.setAdId(aci.getId());
    	aac.setAdCreativeTemplateId(1004L);
    	aac.setCreativeName(query.getLandingpageName());
    	aac.setCreativeStatus("NORMAL");
    	nonstandardAdDao.getAdvertiserAdCreativeMapper().insertUseGeneratedKeys(aac);
    	
    	urlMap.put("campaignId", ac.getId().toString());
    	urlMap.put("creativeId", aac.getId().toString());
    	urlMap.put("adId",aci.getId().toString());
    	
    	return urlMap;
    }
    
    /**
     * 创建点击链接
     * @param query
     * @param ndq
     * @return
     */
    public String creativeClickUrl(NonstandardInfoQuery query,NonstandardDataQuery ndq,Map<String,String> ccMap){
    	StringBuffer clickUrl = new StringBuffer();
    	
    	try {
			clickUrl.append(clkUrl);
			clickUrl.append("info=").append(ccMap.get("adId")).append("_").append(ccMap.get("creativeId")).append("__").append(query.getProjectId()).append("____1105_CPC_______").append(query.getChannelId()).append("_");
			String url = ndq.getClickUrl();
			if(url.indexOf("?")<0){
				clickUrl.append("&url=").append(URLEncoder.encode(ndq.getClickUrl()+"?"+etc, "utf-8"));
			}else{
				clickUrl.append("&url=").append(URLEncoder.encode(ndq.getClickUrl()+"&"+etc, "utf-8"));
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	return clickUrl.toString();
    }
    
    /**
     * 创建展现链接
     * @return
     */
    public String creativeImpressionUrl(NonstandardInfoQuery query,Map<String,String> ccMap){
    	StringBuffer impressionUrl = new StringBuffer();
    	StringBuffer info = new StringBuffer();
    	try {
    		info = info.append(ccMap.get("adId")).append("_").append(ccMap.get("creativeId")).append("__").append(query.getProjectId()).append("_{requestID}__{requestTime}_1105__1_________");
    		String sign = DigestUtils.md5DigestAsHex((info.toString() + "_" + impKey).getBytes());
			impressionUrl.append(impUrl);
			impressionUrl.append("info=").append(info).append("&c=1&sign=").append(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return impressionUrl.toString();
    }
    
    @Transactional
    public ResponseResult<List<String>> save(NonstandardInfoQuery query){
    	ResponseResult.ResponseResultBuilder<List<String>> builder = ResponseResult.builder();
    	List<String> urlList = new ArrayList<String>();
    	if(null != query){
    		//首先验证source是否存在
    		List<NonstandardDataQuery> ndqList = query.getAdData();
    		if(null != ndqList && ndqList.size() > 0){
    			for(NonstandardDataQuery ndq : ndqList){
    				String trafficSource = creativeSource(query,ndq);
    				NonstandardTracking ntQuery = new NonstandardTracking();
    				ntQuery.setTrafficSource(trafficSource);
    				
    				NonstandardTracking nt = nonstandardAdDao.queryNonstandardTracking(ntQuery);
    				if(null != nt){//不为空表示可以继续保存操作
    					nt.setAdStatus(1);
    					nt.setUpdateTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
    					nonstandardAdDao.getNonstandardTrackingMapper().updateByPrimaryKey(nt);
    					
    					NonstandardAd ad = new NonstandardAd();
    					ad.setId(nt.getAdId());
    					ad.setAdStatus(1);
    					ad.setUpdateTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
    					nonstandardAdDao.getNonstandardAdMapper().updateByPrimaryKey(ad);
    					
    					NonstandardAdInfo adi = new NonstandardAdInfo();
    					adi.setAdId(nt.getAdId());
    					List<NonstandardAdInfo> naiList = nonstandardAdDao.getNonstandardAdInfoMapper().select(adi);
    					if(null != naiList && naiList.size() > 0){
    						NonstandardAdInfo nditemp = naiList.get(0);
    						nditemp.setAdStatus(1);
    						nditemp.setUpdateTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
    						nonstandardAdDao.getNonstandardAdInfoMapper().updateByPrimaryKey(nditemp);
    					}
    					
    					urlList.add(nt.getTrafficUrl());
    				}else{
    					return builder.build().forFail(-1, " Tracking链接没有创建，无法保存 !");
    				}
    			}
    		}else{
    			return builder.build().forFail(-1, " 数据查询为空 !");
    		}
    	}
    	
    	builder.data(urlList);
        return builder.build().forOk();
    }
    
    public ResponseResult<NonstandardInfoVo> edit(NonstandardInfoQuery query){
    	ResponseResult.ResponseResultBuilder<NonstandardInfoVo> builder = ResponseResult.builder();
    	//查询数据
    	NonstandardAd ad = new NonstandardAd();
    	ad.setId(Long.valueOf(query.getAdId()));
    	List<NonstandardAd> adList= nonstandardAdDao.getNonstandardAdMapper().select(ad);
    	
    	NonstandardAdInfo adi = new NonstandardAdInfo();
    	adi.setAdId(Long.valueOf(query.getAdId()));
    	List<NonstandardAdInfo> adilist = nonstandardAdDao.getNonstandardAdInfoMapper().select(adi);
    	
    	NonstandardTracking nti = new NonstandardTracking();
    	nti.setAdId(Long.valueOf(query.getAdId()));
    	List<NonstandardTracking> ntiList = nonstandardAdDao.getNonstandardTrackingMapper().select(nti);
    	
    	//组织返回数据
    	List<NonstandardDataQuery> adData = new ArrayList<NonstandardDataQuery>();
    	if(null != adilist && adilist.size() > 0){
    		for(NonstandardAdInfo n : adilist){
    			NonstandardDataQuery ndq = new NonstandardDataQuery();
    			ndq.setClickUrl("");
    			ndq.setDescribe(n.getAdDescribe());
    			ndq.setImgUrl(n.getImgUrl());
    			ndq.setTitle(n.getTitle());

    			String cUrl = n.getClickUrl();
    			if(StringUtil.isNotEmpty(cUrl)){
					try {
						cUrl = URLDecoder.decode(cUrl.substring(cUrl.indexOf("url=")+4),"UTF-8");
						cUrl = cUrl.substring(0,cUrl.indexOf("etc_n=")-1);
						ndq.setClickUrl(cUrl);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

    			adData.add(ndq);
    		}
    	}
    	
    	List<String> trackingUrl = new ArrayList<String>();
    	if(null != ntiList && ntiList.size() > 0){
    		for(NonstandardTracking n : ntiList){
    			trackingUrl.add(n.getTrafficUrl());
    		}
    	}
    	
    	NonstandardInfoVo niVo = new NonstandardInfoVo();
    	if(null != adList && adList.size() > 0){
    		NonstandardAd adTemp = adList.get(0);
        	niVo.setProjectId(adTemp.getProjectId().toString());
        	niVo.setChannelId(adTemp.getChannelId().toString());
        	niVo.setAdType(adTemp.getAdType().toString());
        	niVo.setLandingpageName(adTemp.getLandingpageName());
        	niVo.setLandingpageUrl(adTemp.getLandingpageUrl());
    	}
    	niVo.setAdData(adData);
    	niVo.setTrackingUrl(trackingUrl);

    	builder.data(niVo);
        return builder.build().forOk();
    }
    
    public ResponseResult<NonstandardHtmlDataVo> toutiao(NonstandardInfoQuery query){
    	ResponseResult.ResponseResultBuilder<NonstandardHtmlDataVo> builder = ResponseResult.builder();
    	//验证参数是否正确
    	String trafficSource = query.getTrafficSource();
    	if(StringUtils.isEmpty(trafficSource)){
    		return builder.build().forFail(-1, " 缺少必须的参数 ");
    	}
    	NonstandardHtmlDataVo vo = new NonstandardHtmlDataVo();
    	List<String> toutiaoData = new ArrayList<String>();
    	for(int i=0;i<Integer.valueOf(query.getNewsNumber());i++){
    		int key = new Random().nextInt(500);
    		toutiaoData.add(stringRedisTemplate.opsForHash().get("TT_CONTENT_INFO", "content_"+key).toString());
    	}
    	
    	List<NonstandardAdInfo> adData = new ArrayList<NonstandardAdInfo>();
    	NonstandardTracking ntQuery = new NonstandardTracking();
		ntQuery.setTrafficSource(query.getTrafficSource());
		NonstandardTracking nt = nonstandardAdDao.queryNonstandardTracking(ntQuery);
		if(null != nt){//不为空表示可以继续保存操作
			NonstandardAdInfo adi = new NonstandardAdInfo();
			adi.setAdId(nt.getAdId());
			List<NonstandardAdInfo> naiList = nonstandardAdDao.getNonstandardAdInfoMapper().select(adi);
			if(null != naiList && naiList.size() > 0){
				for(NonstandardAdInfo nai:naiList){
					adData.add(nai);
				}
			}
		}
		vo.setAdData(adData);
		vo.setToutiaoData(toutiaoData);
    	
    	builder.data(vo);
        return builder.build().forOk();
    }
    /**
     * 创建保存和验证用的source
     * @param query
     * @return
     */
    private String creativeSource(NonstandardInfoQuery query,NonstandardDataQuery ndq){
    	String projectId = query.getProjectId();
    	String channelId = query.getChannelId();
    	String adType = query.getAdType();
    	if("4".equals(adType)){//图文
    		String imgUrl = ndq.getImgUrl();
    		String title = ndq.getTitle();
    		String describe = ndq.getDescribe();
    		String clickUrl = ndq.getClickUrl();
    		return DigestUtils.md5DigestAsHex((projectId+channelId+imgUrl+title+describe+clickUrl).getBytes());
    	}
    	return "";
    }

    public static void main(String[] age){
    	String url = "http://172.16.105.36:18700/dsp/impression?123=345";
    	if(url.indexOf("?")<0){
    		url = url + "?etc_n=aaa";
		}else{
    		url = url + "&etc_n=aaa";
		}
		System.out.println("url=== "+ url);

    	String curl = url.substring(0,url.indexOf("etc_n=")-1);
		System.out.println("curl=== "+curl);

	}

}
