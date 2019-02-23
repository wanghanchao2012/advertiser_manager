package com.emarbox.service;

import com.emarbox.dto.AdvertiserInfoQuery;
import com.emarbox.dto.common.AdvertiserOptionItemVo;
import com.emarbox.dto.common.ResponseResult;
import com.emarbox.entity.AdvertiserInfo;
import com.emarbox.entity.CertificateSource;
import com.emarbox.entity.ProjectCertificate;
import com.rainerhahnekamp.sneakythrow.Sneaky;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AdvertiserInfoService extends BaseInfoService {

    @Value("${account.advertiser.image.upload.url}")
    private String directory;


    public ResponseResult<List<AdvertiserInfo>> getPage(AdvertiserInfoQuery query) {
        ResponseResult.ResponseResultBuilder<List<AdvertiserInfo>> builder = ResponseResult.builder();
        List<AdvertiserInfo> listByPage = Collections.emptyList();
        Integer pageSize = daoProvider.getAdvertiserInfoDao().getCountByPage(query);
        if (pageSize.intValue() > 0) {
            query.getPage().setRecordCount(pageSize);
            listByPage = this.daoProvider.getAdvertiserInfoDao().getListByPage(query);
        }
        builder.data(listByPage).page(query.getPage());
        return builder.build().forOk();
    }

    public List<AdvertiserOptionItemVo> getAdvertiserOptionsEdit() {
        List<AdvertiserOptionItemVo> optionItemVoList = Collections.emptyList();
        List<AdvertiserInfo> advertiserAll = daoProvider.getAdvertiserInfoDao().getAdvertiserAll();
        if (CollectionUtils.isNotEmpty(advertiserAll)) {
            optionItemVoList = advertiserAll.stream().map(e -> AdvertiserOptionItemVo.builder().licenseCode(e.getCreditCode()).value(e.getId()).text(e.getAdvertiserName()).realValue(e.getAdvertiserName()).build().preAppendValue()).collect(Collectors.toList());
        }
        return optionItemVoList;
    }

    public List<AdvertiserOptionItemVo> getAdvertiserOptions() {
        return preAppendAllAdvertiser(getAdvertiserOptionsEdit());

    }

    public AdvertiserInfo getAdvertiserInfo(Long advertiserId) {
        AdvertiserInfo advertiserInfoQuery = new AdvertiserInfo();
        advertiserInfoQuery.setId(advertiserId);
        AdvertiserInfo advertiserInfo = null;
        List<AdvertiserInfo> advertiserInfoList = daoProvider.getAdvertiserInfoDao().getAdvertiserInfoList(advertiserInfoQuery);
        if (CollectionUtils.isNotEmpty(advertiserInfoList)) {
            advertiserInfo = advertiserInfoList.get(0);
        }
        List<ProjectCertificate> projectCertificateList = daoProvider.getProjectCertificateDao().getProjectCertificateList(ProjectCertificate.builder().advertiserId(advertiserId).build());
        if (CollectionUtils.isNotEmpty(projectCertificateList)) {
            List<CertificateSource> sourceList = projectCertificateList.stream().filter(e -> Objects.nonNull(e.getFilePath())).filter(e -> Objects.nonNull(e.getCertificateId())).map(e -> CertificateSource.builder().code(e.getCertificateId().intValue()).imgPath(e.getFilePath()).build()).collect(Collectors.toList());
            advertiserInfo.setCertificateSource(sourceList);
        }
        return advertiserInfo;
    }

    @Transactional
    public boolean edit(AdvertiserInfo record) {
        record.setUpdateTime(new Date());
        record.setUpdateUser(Objects.nonNull(record.getCurrentUserId()) ? record.getCurrentUserId().toString() : null);
        daoProvider.getAdvertiserInfoDao().edit(record);
        saveCertficate(false, record);
        return true;
    }

    @Transactional
    public boolean add(AdvertiserInfo record) {
        record.setCreateTime(new Date());
        record.setCreateUser(Objects.nonNull(record.getCurrentUserId()) ? record.getCurrentUserId().toString() : null);
        daoProvider.getAdvertiserInfoDao().add(record);
        saveCertficate(true, record);
        return true;
    }

    public void saveCertficate(boolean isAdd, AdvertiserInfo record) {
        Example delExample = new Example(ProjectCertificate.class);
        delExample.createCriteria().andEqualTo("advertiserId",record.getId());
        daoProvider.getProjectCertificateDao().delete(delExample);
        List<ProjectCertificate> certificateList = record.getCertificateSource().stream().filter(source -> Objects.nonNull(source.getImgPath())).map(source -> {
            ProjectCertificate certificate = new ProjectCertificate();
            String imgName = source.getImgPath().substring(source.getImgPath().lastIndexOf("/") + 1, source.getImgPath().length());
            File imageFile = new File(directory.concat("/").concat(imgName));
            String imageSignature = Sneaky.sneaked(() -> DigestUtils.md5DigestAsHex(Sneaky.sneaked(() -> new FileInputStream(imageFile)).get())).get();
            certificate.setSignature(imageSignature);
            BufferedImage image = Sneaky.sneaked(() -> ImageIO.read(Sneaky.sneaked(() -> new FileInputStream(imageFile)).get())).get();
            certificate.setFileWidth(Long.parseLong(String.valueOf(image.getWidth())));
            certificate.setFileHeight(Long.parseLong(String.valueOf(image.getHeight())));
            certificate.setFilePath(source.getImgPath());
            certificate.setFileSize(imageFile.length() / 1024.0);
            certificate.setAdvertiserId(record.getId());
            certificate.setCreateUser(Objects.nonNull(record.getCurrentUserId())?String.valueOf(record.getCurrentUserId()):null);
            certificate.setCreateTime(new Date());
            certificate.setCertificateId(source.getCode().longValue());
            return certificate;
        }).collect(Collectors.toList());
        daoProvider.getProjectCertificateDao().insertBatch(certificateList);
    }


}
