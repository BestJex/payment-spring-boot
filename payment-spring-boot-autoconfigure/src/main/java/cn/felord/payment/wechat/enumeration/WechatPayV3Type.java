package cn.felord.payment.wechat.enumeration;

import org.springframework.http.HttpMethod;

/**
 * The Wechat Pay V3 type.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public enum WechatPayV3Type {
    /**
     * 获取证书.
     */
    CERT(HttpMethod.GET, "%s/v3/certificates"),

    /**
     * 文件下载
     */
    FILE_DOWNLOAD(HttpMethod.GET,"%s/v3/billdownload/file"),

    /**
     * 微信公众号支付或者小程序支付.
     */
    JSAPI(HttpMethod.POST, "%s/v3/pay/transactions/jsapi"),

    /**
     * 微信扫码支付.
     */
    NATIVE(HttpMethod.POST, "%s/v3/pay/transactions/native"),

    /**
     * 微信APP支付.
     */
    APP(HttpMethod.POST, "%s/v3/pay/transactions/app"),

    /**
     * H5支付.
     */
    MWEB(HttpMethod.POST, "%s/v3/pay/transactions/h5"),
    /**
     * 关闭订单.
     */
    CLOSE(HttpMethod.POST, "%s/v3/pay/transactions/out-trade-no/{out_trade_no}/close"),
    /**
     * 微信支付订单号查询.
     */
    TRANSACTION_TRANSACTION_ID(HttpMethod.GET, "%s/v3/pay/transactions/id/{transaction_id}"),
    /**
     * 商户订单号查询.
     */
    TRANSACTION_OUT_TRADE_NO(HttpMethod.GET, "%s/v3/pay/transactions/out-trade-no/{out_trade_no}"),


    /**
     * 创建代金券批次API.
     */
    MARKETING_FAVOR_STOCKS_COUPON_STOCKS(HttpMethod.POST,"%s/v3/marketing/favor/coupon-stocks"),
    /**
     * 激活代金券批次API.
     */
    MARKETING_FAVOR_STOCKS_START(HttpMethod.POST,"%s/v3/marketing/favor/stocks/{stock_id}/start"),
    /**
     * 暂停代金券批次API.
     */
    MARKETING_FAVOR_STOCKS_PAUSE(HttpMethod.POST,"%s/v3/marketing/favor/stocks/{stock_id}/pause"),

    /**
     * 发放代金券API、根据商户号查用户的券.
     */
    MARKETING_FAVOR_USERS_COUPONS(HttpMethod.POST,"%s/v3/marketing/favor/users/{openid}/coupons"),
    /**
     * 重启代金券API.
     */
    MARKETING_FAVOR_STOCKS_RESTART(HttpMethod.POST,"%s/v3/marketing/favor/stocks/{stock_id}/restart"),
    /**
     * 条件查询批次列表API.
     */
    MARKETING_FAVOR_STOCKS(HttpMethod.GET, "%s/v3/marketing/favor/stocks"),
    /**
     * 查询批次详情API.
     */
    MARKETING_FAVOR_STOCKS_DETAIL(HttpMethod.GET, "%s/v3/marketing/favor/stocks/{stock_id}"),
    /**
     * 查询代金券详情API
     */
    MARKETING_FAVOR_USERS_COUPONS_DETAIL(HttpMethod.GET, "%s/v3/marketing/favor/users/{openid}/coupons/{coupon_id}"),
    /**
     * 查询代金券可用商户API.
     */
    MARKETING_FAVOR_STOCKS_MERCHANTS(HttpMethod.GET, "%s/v3/marketing/favor/stocks/{stock_id}/merchants"),
    /**
     * 查询代金券可用单品API.
     */
    MARKETING_FAVOR_STOCKS_ITEMS(HttpMethod.GET, "%s/v3/marketing/favor/stocks/{stock_id}/items"),
    /**
     * 下载批次核销明细API.
     */
    MARKETING_FAVOR_STOCKS_USE_FLOW(HttpMethod.GET, "%s/v3/marketing/favor/stocks/{stock_id}/use-flow"),
    /**
     * 下载批次退款明细API.
     */
    MARKETING_FAVOR_STOCKS_REFUND_FLOW(HttpMethod.GET, "%s/v3/marketing/favor/stocks/{stock_id}/refund-flow"),
    /**
     * 营销图片上传API.
     */
    MARKETING_IMAGE_UPLOAD(HttpMethod.POST, "%s/v3/marketing/favor/media/image-upload"),
    /**
     * 设置核销回调通知API.
     */
    MARKETING_FAVOR_CALLBACKS(HttpMethod.POST, "%s/v3/marketing/favor/callbacks");




    private final String pattern;
    private final HttpMethod method;

    WechatPayV3Type(HttpMethod method, String pattern) {
        this.method = method;
        this.pattern = pattern;
    }

    /**
     * Method string.
     *
     * @return the string
     */
    public HttpMethod method() {
        return this.method;
    }

    /**
     * Pattern string.
     *
     * @return the string
     */
    public String pattern() {
        return this.pattern;
    }


    /**
     * 默认支付URI.
     *
     * @param weChatServer the we chat server
     * @return the string
     */
    public String uri(WeChatServer weChatServer) {
        return  String.format(this.pattern,weChatServer.domain());
    }

}
