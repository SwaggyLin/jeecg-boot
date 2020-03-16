package org.jeecg.modules.tbk.util;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.NTbkShop;
import com.taobao.api.request.TbkActivitylinkGetRequest;
import com.taobao.api.request.TbkDgOptimusMaterialRequest;
import com.taobao.api.request.TbkShopGetRequest;
import com.taobao.api.request.TbkSpreadGetRequest;
import com.taobao.api.request.TbkSpreadGetRequest.TbkSpreadRequest;
import com.taobao.api.response.TbkActivitylinkGetResponse;
import com.taobao.api.response.TbkDgOptimusMaterialResponse;
import com.taobao.api.response.TbkShopGetResponse;
import com.taobao.api.response.TbkSpreadGetResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author linminfeng
 * @Date 2019-12-16 16:34
 */
public class TbkTools {
    public static final String APP_KEY="28184597";
    public static final String SECRET="34023a74a1351e0c1353da2ae836a5f8";
    public static final String API_URL="https://eco.taobao.com/router/rest";

    /**
     * 淘宝客-公用-长链转短链
     * 输入一个原始的链接，转换得到指定的传播方式，如二维码，淘口令，短连接； 现阶段只支持短连接
     * @param longUrl
     * @return
     * @throws ApiException
     */
    public static String getTbkShortUrl(String longUrl) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(API_URL, APP_KEY, SECRET);
        TbkSpreadGetRequest req = new TbkSpreadGetRequest();
        List<TbkSpreadRequest> list = new ArrayList<TbkSpreadRequest>();
        TbkSpreadRequest obj = new TbkSpreadRequest();
        list.add(obj);
        obj.setUrl(longUrl);
        req.setRequests(list);
        TbkSpreadGetResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
        return rsp.getResults().get(0).getContent();
    }

    /**
     * 淘宝联盟官方活动推广API-媒体，从官方活动列表页取出“官方活动ID”，支持二方、三方
     * @return
     */
    public static String getTbkActivityLink() throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(API_URL, APP_KEY, SECRET);
        TbkActivitylinkGetRequest req = new TbkActivitylinkGetRequest();
        req.setPlatform(1L);
        req.setUnionId("demo");
        req.setAdzoneId(109875200336L);
        req.setPromotionSceneId(1577771853635L);
        req.setSubPid("mm_741960020_1177650241_109875200336");
        //req.setRelationId("23");
        TbkActivitylinkGetResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
        return null;
    }

    /**
     * 获取订单详情
     * @return
     */
    public static String getTbkOrderDetail(){
        TaobaoClient client = new DefaultTaobaoClient(API_URL, APP_KEY, SECRET);
//        TbkOrderDetailsGetRequest req = new TbkOrderDetailsGetRequest();
//        req.setQueryType(1L);
//        req.setPositionIndex("2222_334666");
//        req.setPageSize(20L);
//        req.setMemberType(2L);
//        req.setTkStatus(12L);
//        req.setEndTime("2019-04-23 12:28:22");
//        req.setStartTime("2019-04-05 12:18:22");
//        req.setJumpType(1L);
//        req.setPageNo(1L);
//        req.setOrderScene(1L);
//        TbkOrderDetailsGetResponse rsp = client.execute(req);
//        System.out.println(rsp.getBody());
        return null;
    }

    /**
     * 根据关键词获取淘宝客店铺
     * @return
     * @throws ApiException
     */
    public static List<NTbkShop> getTbkShop() throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(API_URL, APP_KEY, SECRET);
        TbkShopGetRequest req = new TbkShopGetRequest();
        req.setFields("user_id,shop_title,shop_type,seller_nick,pict_url,shop_url");
        req.setQ("游戏");
        req.setSort("commission_rate_des");
        req.setIsTmall(false);
        req.setStartCredit(1L);
        req.setEndCredit(20L);
        req.setStartCommissionRate(1L);
        req.setEndCommissionRate(10000L);
        req.setStartTotalAction(1L);
        req.setEndTotalAction(100L);
        req.setStartAuctionCount(1L);
        req.setEndAuctionCount(200L);
        req.setPlatform(1L);
        req.setPageNo(1L);
        req.setPageSize(20L);
        TbkShopGetResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
        return rsp.getResults();
    }

    /**
     * 通用物料推荐，传入官方公布的物料id，可获取指定物料
     * @throws ApiException
     */
    public static void getTbkOptimusMaterial() throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(API_URL, APP_KEY, SECRET);
        TbkDgOptimusMaterialRequest req = new TbkDgOptimusMaterialRequest();
        req.setPageSize(20L);
        req.setAdzoneId(109875200336L);
        req.setPageNo(1L);
        req.setMaterialId(3756L);
//        req.setDeviceValue("xxx");
//        req.setDeviceEncrypt("MD5");
//        req.setDeviceType("IMEI");
//        req.setContentId(323L);
//        req.setContentSource("xxx");
//        req.setItemId(33243L);
        TbkDgOptimusMaterialResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
    }
    public static void main(String path[]) throws Exception {
        getTbkOptimusMaterial();
    }
}
