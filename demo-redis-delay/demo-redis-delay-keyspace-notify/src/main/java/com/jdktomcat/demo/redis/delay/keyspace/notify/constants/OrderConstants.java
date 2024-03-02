
package com.jdktomcat.demo.redis.delay.keyspace.notify.constants;

/**
 * @author : aleck
 * @Description: 订单常量
 * @date : 2021年03月05日 12:41
 */
public class OrderConstants {

    /** ----------------订单常量------------------------ **/
    /**
     * 订单已创建
     */
    public static final int O_STATUS_CREATE = 0;

    /**
     * 订单处理中（撮合成功）
     */
    public static final int O_STATUS_MATCHED = 1;

    /**
     * 订单待确认 --  充值已确认
     */
    public static final int O_STATUS_WAIT_CONFIRM = 10;

    /**
     * 订单已确认
     */
    public static final int O_STATUS_CONFIRM = 4;

    /**
     * 订单已取消
     */
    public static final int O_STATUS_CANCEL = 7;

    /**
     * 订单未撮合-过期转自动
     */
    public static final int O_STATUS_EXPIED_AUTO = 5;

    /**
     * 订单未撮合-超时未匹配
     */
    public static final int O_STATUS_EXPIRED_UNMATCHED = 501;

    /**
     * 匹配成功的订单-过期未确认
     */
    public static final int O_STATUS_MATCHED_EXPIED = 6;

    /**
     * 匹配成功的订单-过期已确认
     */
    public static final int O_STATUS_MATCHED_EXPIED_CONFIRM = 604;

    /**
     * 匹配成功的订单-过期已取消
     */
    public static final int O_STATUS_MATCHED_EXPIED_CANCEL = 607;

    /**
     * 提款订单 - 已优先确认
     */
    public static final int O_STATUS_CONFIRM_PRIOR=40;

    /**
     * 订单处理中(撮合成功)-过期转手动
     */
//    public static final int O_STATUS_MATCHED_EXPIED_MANUAL = 611;

    /**
     * 订单超时时间KEY：用于设置订单未撮合的超时时间
     */
//    public static final String W_O_VALIDATE_KEY = "w_o_validate";

    /**
     * 订单撮合成功超时时间KEY：用于设置订单撮合成功的超时时间
     */
//    public static final String W_O_UNMATCHED_TIMEOUT_KEY = "w_o_unmatched_timeout";

    /**
     * 二次匹配
     */
    public static final int O_STATUS_TWO_MATCHED = 11;

    /**
     * 重推匹配
     */
    public static final int O_STATUS_REMATCH = 110;

    /**
     * 重推二次匹配
     */
    public static final int O_STATUS_REMATCH_SECOND = 111;

    /**
     * 订单匹配成功，取消转普通
     */
    public static final int O_STATUS_CANCEL_AUTO = 71;


    /** ----------------订单撮合配置常量------------------------ **/
    /**
     * 未撮合订单的有效期，单位为分钟，默认15分钟
     */
    public static final int WITHDRAW_UNMATCH_VALID_TIME = 15;

    /**
     * 存款订单确认超时时限，单位为分钟，默认120 分钟
     */
    public static final int MATCHUP_SUCCESS_DEPOSIT_TIMEOUT = 120;


    /**
     * 提款订单确认超时时间，单位为分钟，默认120 分钟
     */
    public static final int MATCHUP_SUCCESS_WITHDRAW_TIMEOUT = 120;

    /**
     * 未撮合的超时订单Redis前缀
     */
    public static final String UN_MATCH_WITHDRAW_PREFIX = "UEW_";

    /**
     * 已撮合的超时订单Redis前缀  （提款订单）
     */
    public static final String MATCH_WITHDRAW_PREFIX = "MEW_";

    /**
     * 已撮合的超时订单Redis前缀 （充值订单）
     */
    public static final String MATCH_DEPOSIT_PREFIX = "MED_";


    public static final String MATCH_INCR_KEY = "INCR_";

    ////////////add 2021-7-3 aleck ////////////
    /**
     * 未撮合的超时订单Redis前缀
     */
    public static final String U_M_WITHDRAW_PREFIX = "UEWN_";

    public static final String CACHE_UNMATCHED_ORDER = "CUO_";

    /**
     * 撮合池监控页面，按 规则+金额 统计拆单订单数
     */
    public static final String MONITOR_SPLIT_ORDER_COUNT = "MNT_SOC";

    /**
     * 撮合池监控页面，按 规则+金额 统计未拆单订单数
     */
    public static final String MONITOR_NOT_SPLIT_ORDER_COUNT = "MNT_N_SOC";

    /**
     * 未撮合的最优先订单Redis前缀
     */
    public static final String M_P_WITHDRAW_PREFIX = "MPWP_";

    /**
     * 推送订单时锁单key
     */
    public static final String ORDER_PUSH_NOTIFY_LOCK = "o_p_n_lock";

    public static final String P_W_ORDER_SYMBOL_PREFIX = "P_WOS_";

    public static final String M_P_W_ORDER_SYMBOL_PREFIX = "MP_WOS_";

    public static final String P_WITHDRAW_PREFIX = "P_W_P_";

    /**
     *
     *  提款撮合池Redis的订单数量前缀
     */
    public static final String WITHDRAW_MATCH_POOL_C_PREFIX = "WMP_C_";


    /**
     * 提款金额池低位线值
     */
    public static long LOW_LEVEL_VALUE = 1000000;
    /**
     * 提款金额池高位线值
     */
    public static long HIGH_LEVEL_VALUE =2000000;
    /**
     * 提款金额冷却池
     */
    public static long COOL_LEVEL_VALUE =3000000;

    public static final String STEP_SIZE_RULES = "STEP_";

    /**
     * 零钱支付前缀
     */
    public static final String UAMT_PREFIX = "UEWD_";
    /**
     * 会员锁前缀
     */
    public static final String UEWF_PREFIX = "UEWF_";
}
