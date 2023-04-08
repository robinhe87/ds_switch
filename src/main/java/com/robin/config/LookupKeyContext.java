package com.robin.config;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hehongbing
 * @date 2023/4/8
 */
public final class LookupKeyContext {

    private static volatile String currentDataSource = DataSourceTypeEnum.MASTER.name();

    private static ReentrantLock reentrantLock = new ReentrantLock();

    private static ThreadLocal<String> dataSource = ThreadLocal.withInitial(() -> currentDataSource);

    public static String getDataSource() {
        return dataSource.get();
    }

    public static void swithDataSource() {
        try {
            reentrantLock.tryLock();
            if (DataSourceTypeEnum.MASTER.name().equals(getDataSource())) {
                dataSource.set(DataSourceTypeEnum.SLAVE.name());
            } else {
                dataSource.set(DataSourceTypeEnum.MASTER.name());
            }
        } finally {
            currentDataSource = getDataSource();
            reentrantLock.unlock();
        }
    }

    public static void clear() {
        dataSource.remove();
    }

}
