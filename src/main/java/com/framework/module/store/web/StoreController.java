package com.framework.module.store.web;

import com.framework.module.store.domain.Goods;
import com.framework.module.store.domain.Store;
import com.framework.module.store.service.StoreService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
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

@Api(value = "派工单管理")
@RestController
@RequestMapping("/api/store")
public class StoreController extends AbstractCrudController<Store> {
    private final StoreService storeService;
    @Override
    protected CrudService<Store> getService() {
        return storeService;
    }

    /**
     * 获取货物
     */
    @ApiOperation(value="获取货物")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "currentPage", value = "当前页数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页页数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序属性，多个用逗号隔开", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "排序方向，多个用逗号隔开", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/goods/{storeId}", method = RequestMethod.GET)
    public ResponseEntity<?> getGoods(@PathVariable String storeId,
                                                @ModelAttribute PageParam pageParam,
                                                HttpServletRequest request) throws Exception {
        Map<String, String[]> param = request.getParameterMap();
        if(pageParam.isPageAble()) {
            PageResult<Goods> pageResult = storeService.getGoods(pageParam.getPageRequest(), storeId);
            return new ResponseEntity<>(pageResult, HttpStatus.OK);
        }
        List<Goods> goodses = storeService.getGoods(storeId);
        return new ResponseEntity<>(goodses, HttpStatus.OK);
    }

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }
}
