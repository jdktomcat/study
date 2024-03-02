
package com.jdktomcat.demo.redis.delay.keyspace.notify.constants;


/**
 * @author : aleck
 * @Description: 订单常量
 * @date : 2021年03月05日 12:41
 */
public class RedisConstants {
    /**
     * 会员在24小时内可撮合提款次数，默认不限制次数
     */
    public  static final String withdrawMatchCountIn24 = "R_W_M_C_";
    /**
     * 会员在24小时内提款撮合，允许未确认次数，默认不限制次数
     */
    public  static final String withdrawUnConfirmCountIn24 = "R_W_UC_C_";
    /**
     * 会员在24小时内可撮合的充值次数，默认不限制次数
     */
    public  static final String  depositMatchCountIn24 = "R_D_M_C_";
    /**
     * 会员在24小时内充值撮合，允许未确认次数，默认不限制次数
     */
    public  static final String depositUnConfirmCountIn24 ="R_D_UC_C_";

    /**
     * 会员在24小时内充值，可取消的次数，默认不限制次数
     */
    public  static final String depositCancelCountIn24 = "R_D_C_C_";

    /**
     * 提款未撮合订单的有效期，单位为分钟，默认3分钟
     */
    public  static final String withdrawUnMatchValidTime ="R_W_UM_VT_";

    /**
     * 撮合成功订单超时时限，单位为小时，默认6小时
     */
    public  static final String matchUpSuccessTimeout = "R_M_S_T_";


    /**
     * 提款订单反查前缀
     */
    public static final String WITHDRAW_ORDER_REVIEW ="MWR_";
    /**
     * 充值订单反查前缀
     */
    public static final String DEPOSIT_ORDER_REVIEW ="MDR_";

    /**
     * 提款订单redis排序前缀 外部极速+手动优先
     */
    public static final long WITHDRAW_ORDER_TIME_ONE_PREFIX = 10000000000000L;

    /**
     * 提款订单redis排序前缀 内部极速+手动优先
     */
    public static final long WITHDRAW_ORDER_TIME_TWO_PREFIX = 20000000000000L;

    /**
     * 提款订单redis排序前缀 外部极速
     */
    public static final long WITHDRAW_ORDER_TIME_THREE_PREFIX = 40000000000000L;

    /**
     * 提款订单redis排序前缀 内部极速
     */
    public static final long WITHDRAW_ORDER_TIME_FOUR_PREFIX = 80000000000000L;


    /**
     * 提款订单-零钱redis排序前缀
     */
    public static final long WITHDRAW_913_ORDER_PREFIX = 99999L;

    /**
     * 通知提款订单状态变化缓存键值前缀:order:type:change:record:{平台订单号}
     */
    public static final String ORDER_TYPE_CHANGE_RECORD_CACHE_KEY_PREFIX = "order:type:change:record:%s";
}
