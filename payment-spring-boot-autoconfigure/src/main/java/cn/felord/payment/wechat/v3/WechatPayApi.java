package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.PayParams;
import cn.felord.payment.wechat.v3.model.TransactionQueryParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * The type Wechat pay api.
 *
 * @author Dax
 * @since 16 :15
 */
public class WechatPayApi extends AbstractApi {

    /**
     * Instantiates a new Wechat pay api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatPayApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * APP下单API
     *
     * @param payParams the pay params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> appPay(PayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.APP, payParams)
                .function(this::payFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * JSAPI/小程序下单API
     *
     * @param payParams the pay params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> jsPay(PayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.JSAPI, payParams)
                .function(this::payFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * Native下单API
     *
     * @param payParams the pay params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> nativePay(PayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.NATIVE, payParams)
                .function(this::payFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * H5下单API
     *
     * @param payParams the pay params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> h5Pay(PayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MWEB, payParams)
                .function(this::payFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> payFunction(WechatPayV3Type type, PayParams payParams) {
        WechatPayProperties.V3 v3 = this.container().getWechatMeta(tenantId()).getV3();
        payParams.setAppid(v3.getAppId());
        payParams.setMchid(v3.getMchId());
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        return post(uri, payParams,tenantId());
    }

    /**
     * 微信支付订单号查询API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryTransactionById(TransactionQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.TRANSACTION_TRANSACTION_ID, params)
                .function(this::queryTransactionFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 商户订单号查询API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryTransactionByOutTradeNo(TransactionQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.TRANSACTION_OUT_TRADE_NO, params)
                .function(this::queryTransactionFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> queryTransactionFunction(WechatPayV3Type type, TransactionQueryParams params) {
        WechatPayProperties.V3 v3 = this.container().getWechatMeta(tenantId()).getV3();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("mchid", params.getMchId());

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(params.getTransactionIdOrOutTradeNo())
                .toUri();
        return RequestEntity.get(uri).header("Pay-TenantId",tenantId()).build();
    }

}