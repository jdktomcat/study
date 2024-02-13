package com.jdktomcat.demo.dead.lock.restart.transaction.mapper;

import org.apache.ibatis.annotations.Select;

public interface TransactionMapper {

    @Select("SELECT TRX_ID FROM INFORMATION_SCHEMA.INNODB_TRX  WHERE TRX_MYSQL_THREAD_ID = CONNECTION_ID()")
    String getCurrentTransactionId();
}
