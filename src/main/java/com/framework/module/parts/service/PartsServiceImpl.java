package com.framework.module.parts.service;

import com.framework.module.parts.domain.Parts;
import com.framework.module.parts.domain.PartsImage;
import com.framework.module.parts.domain.PartsRepository;
import com.framework.module.parts.web.PartsResult;
import com.framework.module.parts.web.PartsSaveParam;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.CrudService;
import com.kratos.common.PageRepository;
import com.kratos.common.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class PartsServiceImpl extends AbstractCrudService<Parts> implements PartsService {
    private final PartsRepository partsRepository;
    private final PartsImageService partsImageService;
    @Override
    protected PageRepository<Parts> getRepository() {
        return partsRepository;
    }

    @Override
    public void save(PartsSaveParam partsSaveParam) throws Exception {
        Parts parts = new Parts();
        BeanUtils.copyProperties(partsSaveParam, parts);
        super.save(parts);
        PartsImage partsImage = new PartsImage();
        if(partsSaveParam.getPartsCoverImage() != null) {
            BeanUtils.copyProperties(partsSaveParam.getPartsCoverImage(), partsImage);
            partsImage.setPartsId(parts.getId());
            partsImage.setSortNumber(0);
            partsImageService.save(partsImage);
        }
        if(partsSaveParam.getPartsAttached1Image() != null) {
            partsImage = new PartsImage();
            BeanUtils.copyProperties(partsSaveParam.getPartsAttached1Image(), partsImage);
            partsImage.setPartsId(parts.getId());
            partsImage.setSortNumber(1);
            partsImageService.save(partsImage);
        }
        if(partsSaveParam.getPartsAttached2Image() != null) {
            partsImage = new PartsImage();
            BeanUtils.copyProperties(partsSaveParam.getPartsAttached2Image(), partsImage);
            partsImage.setPartsId(parts.getId());
            partsImage.setSortNumber(2);
            partsImageService.save(partsImage);
        }
        if(partsSaveParam.getPartsAttached3Image() != null) {
            partsImage = new PartsImage();
            BeanUtils.copyProperties(partsSaveParam.getPartsAttached3Image(), partsImage);
            partsImage.setPartsId(parts.getId());
            partsImage.setSortNumber(3);
            partsImageService.save(partsImage);
        }
        if(partsSaveParam.getPartsAttached3Image() != null) {
            partsImage = new PartsImage();
            BeanUtils.copyProperties(partsSaveParam.getPartsAttached4Image(), partsImage);
            partsImage.setPartsId(parts.getId());
            partsImage.setSortNumber(4);
            partsImageService.save(partsImage);
        }
    }

    @Override
    public PageResult<PartsResult> findAllTranslated(PageRequest pageRequest, Map<String, String[]> param) throws Exception {
        Page<Parts> page = partsRepository.findAll(this.getSpecificationForAllEntities(param), pageRequest);
        PageResult<PartsResult> pageResult = new PageResult<>();
        pageResult.setSize(page.getSize());
        pageResult.setTotalElements(page.getTotalElements());
        pageResult.setContent(translateResults(page.getContent()));
        return pageResult;
    }

    @Override
    public List<PartsResult> findAllTranslated(Map<String, String[]> param) throws Exception {
        return translateResults(partsRepository.findAll(this.getSpecificationForAllEntities(param)));
    }

    @Override
    public PartsResult findOneTranslated(String id) throws Exception {
        return translateResult(super.findOne(id));
    }

    private PartsResult translateResult(Parts parts) throws Exception {
        PartsResult partsResult = new PartsResult();
        BeanUtils.copyProperties(parts, partsResult);
        PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE, new Sort(Sort.Direction.ASC, "sortNumber"));
        Map<String, String[]> param = new HashMap<>();
        param.put("partsId", new String[]{parts.getId()});
        PageResult<PartsImage> goodsImagePageResult = partsImageService.findAll(pageRequest, param);
        List<PartsImage> goodsImages = goodsImagePageResult.getContent();
        if(goodsImages != null) {
            PartsImage partsImage = new PartsImage();
            if(goodsImages.size() > 0) {
                BeanUtils.copyProperties(goodsImages.get(0), partsImage);
                partsResult.setPartsCoverImage(partsImage);
            }
            if(goodsImages.size() > 1) {
                partsImage = new PartsImage();
                BeanUtils.copyProperties(goodsImages.get(1), partsImage);
                partsResult.setPartsAttached1Image(partsImage);
            }
            if(goodsImages.size() > 2) {
                partsImage = new PartsImage();
                BeanUtils.copyProperties(goodsImages.get(2), partsImage);
                partsResult.setPartsAttached2Image(partsImage);
            }
            if(goodsImages.size() > 3) {
                partsImage = new PartsImage();
                BeanUtils.copyProperties(goodsImages.get(3), partsImage);
                partsResult.setPartsAttached3Image(partsImage);
            }
            if(goodsImages.size() > 4) {
                partsImage = new PartsImage();
                BeanUtils.copyProperties(goodsImages.get(4), partsImage);
                partsResult.setPartsAttached4Image(partsImage);
            }
        }

        return partsResult;
    }

    private List<PartsResult> translateResults(List<Parts> partsList) throws Exception {
        List<PartsResult> partsResults = new ArrayList<>();
        for (Parts parts : partsList) {
            partsResults.add(this.translateResult(parts));
        }
        return partsResults;
    }

    @Autowired
    public PartsServiceImpl (
            PartsRepository partsRepository,
            PartsImageService partsImageService
    ) {
        this.partsRepository = partsRepository;
        this.partsImageService = partsImageService;
    }
}
