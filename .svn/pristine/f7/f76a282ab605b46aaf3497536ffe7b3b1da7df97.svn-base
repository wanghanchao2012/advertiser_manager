package com.emarbox.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emarbox.dto.common.ResponseResult;
import com.emarbox.dto.nonstandard.NonstandardHtmlDataVo;
import com.emarbox.dto.nonstandard.NonstandardInfoQuery;
import com.emarbox.dto.nonstandard.NonstandardInfoVo;
import com.emarbox.dto.nonstandard.NonstandardProjectChannelVo;
import com.emarbox.entity.nonstandard.NonstandardAd;
import com.emarbox.service.NonstandardAdService;

@CrossOrigin
@Controller
@RequestMapping("/nonstandard")
public class NonstandardInfoController {

    @Autowired
    NonstandardAdService nonstandardAdService;

    @RequestMapping("/index")
    public String indexHtml(HashMap<String,Object> map){
    	return "index";
    }

    @RequestMapping("/monitor")
    public String monitorHtml(HashMap<String,Object> map){
    	return "monitor";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResponseResult<List<NonstandardAd>> list() {
        return nonstandardAdService.getList();
    }
    
    @RequestMapping("/projectChannel")
    @ResponseBody
    public ResponseResult<NonstandardProjectChannelVo> getProjectChannel(){
        return nonstandardAdService.getProjectChannel();
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public ResponseResult<List<String>> save(@RequestBody NonstandardInfoQuery query){
    	String saveType = query.getSaveType();
    	if(saveType.equals("1")){
    		return nonstandardAdService.creativeTracking(query);
    	}else if(saveType.equals("2")){
    		return nonstandardAdService.save(query);
    	}else{
    		return null;
    	}
    }
    
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseResult<NonstandardInfoVo> edit(@RequestBody NonstandardInfoQuery query){
    	return nonstandardAdService.edit(query);
    }

    @RequestMapping("/toutiao")
    @ResponseBody
    public ResponseResult<NonstandardHtmlDataVo> toutiao(@RequestBody NonstandardInfoQuery query){
    	return nonstandardAdService.toutiao(query);
    }

}
