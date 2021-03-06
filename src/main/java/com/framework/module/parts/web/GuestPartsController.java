package com.framework.module.parts.web;

import com.framework.module.parts.service.PartsService;
import com.kratos.common.PageParam;
import com.kratos.common.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "配件查询")
@RestController
@RequestMapping("/parts")
public class GuestPartsController {
    private final PartsService partsService;
    /**
     * 查询
     */
    @ApiOperation(value="分页如果不传页数则按照条件查询", notes = "可以传查询条件例：name=张三，查询条件使用or分割")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "currentPage", value = "当前页数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页页数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序属性，多个用逗号隔开", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "排序方向，多个用逗号隔开", dataType = "String", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> searchPagedList(@ModelAttribute PageParam pageParam, HttpServletRequest request) throws Exception {
        Map<String, String[]> param = request.getParameterMap();
        if(pageParam.isPageAble()) {
            PageResult<PartsResult> page = partsService.findAllTranslated(pageParam.getPageRequest(), param);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        List<PartsResult> list = partsService.findAllTranslated(param);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 查询一个
     */
    @ApiOperation(value="查询一个")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PartsResult> getOne(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(partsService.findOneTranslated(id), HttpStatus.OK);
    }

    @Autowired
    public GuestPartsController(PartsService partsService) {
        this.partsService = partsService;
    }
}
