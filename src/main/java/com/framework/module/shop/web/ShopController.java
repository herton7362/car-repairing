package com.framework.module.shop.web;

import com.framework.module.shop.domain.Shop;
import com.framework.module.shop.service.ShopService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import com.kratos.common.PageParam;
import com.kratos.common.PageResult;
import com.kratos.module.auth.service.OauthClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "店铺管理")
@RestController
@RequestMapping("/api/shop")
public class ShopController extends AbstractCrudController<Shop> {
    private final ShopService shopService;
    @Override
    protected CrudService<Shop> getService() {
        return shopService;
    }

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
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> searchPagedListAll(@ModelAttribute PageParam pageParam, HttpServletRequest request) throws Exception {
        Map<String, String[]> param = request.getParameterMap();
        if(pageParam.isPageAble()) {
            PageResult<Shop> page = shopService.findAllEveryEntities(pageParam.getPageRequest(), param);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        List<Shop> list = shopService.findAllEveryEntities(param);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService  = shopService;
    }
}
