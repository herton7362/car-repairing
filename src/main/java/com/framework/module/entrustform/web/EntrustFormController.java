package com.framework.module.entrustform.web;

import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.entrustform.service.EntrustFormService;
import com.framework.module.orderform.domain.OrderForm;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import com.kratos.common.PageParam;
import com.kratos.common.PageResult;
import com.kratos.kits.alipay.AliPayAPI;
import com.kratos.kits.alipay.AliPayResult;
import com.kratos.kits.wechat.WeChatAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(value = "委托书管理")
@RestController
@RequestMapping("/api/entrustForm")
public class EntrustFormController extends AbstractCrudController<EntrustForm> {
    private final EntrustFormService entrustFormService;
    private final WeChatAPI weChatAPI;
    private final AliPayAPI aliPayAPI;
    @Override
    protected CrudService<EntrustForm> getService() {
        return entrustFormService;
    }

    @Override
    public ResponseEntity<?> searchPagedList(PageParam pageParam, HttpServletRequest request) throws Exception {
        Map<String, String[]> param = request.getParameterMap();
        if(pageParam.isPageAble()) {
            PageResult<EntrustFormResult> page = entrustFormService.findAllTranslated(pageParam.getPageRequest(), param);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        List<EntrustFormResult> list = entrustFormService.findAllTranslated(param);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<? extends EntrustForm> getOne(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(entrustFormService.getOneTranslated(id), HttpStatus.OK);
    }

    /**
     * 生成派工单
     */
    @ApiOperation(value="生成派工单")
    @RequestMapping(value = "/dispatch/{formId}", method = RequestMethod.POST)
    public ResponseEntity<?> dispatch(@PathVariable String formId, @RequestBody DispatchParam dispatchParam) throws Exception {
        entrustFormService.dispatch(formId, dispatchParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 收款
     */
    @ApiOperation(value="收款")
    @RequestMapping(value = "/pay/{formId}", method = RequestMethod.POST)
    public ResponseEntity<?> pay(@PathVariable String formId, @RequestBody PayParam payParam) throws Exception {
        entrustFormService.pay(formId, payParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 竣工
     */
    @ApiOperation(value="竣工")
    @RequestMapping(value = "/finish/{formId}", method = RequestMethod.POST)
    public ResponseEntity<?> finish(@PathVariable String formId) throws Exception {
        entrustFormService.finish(formId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 确认
     */
    @ApiOperation(value="确认")
    @RequestMapping(value = "/confirm/{formId}", method = RequestMethod.POST)
    public ResponseEntity<?> confirm(@PathVariable String formId) throws Exception {
        entrustFormService.confirm(formId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 获取订单数量
     */
    @ApiOperation(value="获取订单数量")
    @RequestMapping(value = "/count/{memberId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Integer>> getOrderCounts(@PathVariable String memberId) throws Exception {
        return new ResponseEntity<>(entrustFormService.getOrderCounts(memberId), HttpStatus.OK);
    }

    /**
     * 微信获取预付订单
     */
    @ApiOperation(value="获取预付订单")
    @RequestMapping(value = "/wechat/unified", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> unified(@RequestBody EntrustForm entrustForm, HttpServletRequest request) throws Exception {
        return new ResponseEntity<>(weChatAPI.makeAppUnifiedOrder(entrustForm.getOrderNumber(), request, ((Double)(entrustForm.getCashPay()*100D)).intValue()), HttpStatus.OK);
    }

    /**
     * 微信获取预付订单
     */
    @ApiOperation(value="获取预付订单")
    @RequestMapping(value = "/wechat/web/unified", method = RequestMethod.GET)
    public void webUnified(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        Map<String, String[]> param = request.getParameterMap();
        Map<String, Object> map = weChatAPI.makeWebUnifiedOrder(param.get("orderNumber")[0], request, ((Double)(Double.valueOf(param.get("cashPay")[0])*100D)).intValue());
        System.out.println((String) map.get("mwebUrl"));
        response.addHeader("location", (String) map.get("mwebUrl"));
        response.setStatus(302);
    }

    /**
     * 支付宝获取预付订单
     */
    @ApiOperation(value="获取预付订单")
    @RequestMapping(value = "/ali/unified", method = RequestMethod.POST)
    public ResponseEntity<AliPayResult> unified(@RequestBody EntrustForm entrustForm) throws Exception {
        return aliPayAPI.getAliPayOrderId(entrustForm.getOrderNumber(), String.valueOf(entrustForm.getCashPay()), "鼎骏商城");
    }

    /**
     * 支付宝获取预付订单
     */
    @ApiOperation(value="获取预付订单")
    @RequestMapping(value = "/ali/web/unified", method = RequestMethod.GET)
    public void aliWebUnified(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        Map<String, String[]> param = request.getParameterMap();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(aliPayAPI.getWebAliPayForm(param.get("orderNumber")[0], param.get("cashPay")[0], "鼎骏商城"));//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Autowired
    public EntrustFormController(
            EntrustFormService entrustFormService,
            WeChatAPI weChatAPI,
            AliPayAPI aliPayAPI
    ) {
        this.entrustFormService = entrustFormService;
        this.weChatAPI = weChatAPI;
        this.aliPayAPI = aliPayAPI;
    }
}
