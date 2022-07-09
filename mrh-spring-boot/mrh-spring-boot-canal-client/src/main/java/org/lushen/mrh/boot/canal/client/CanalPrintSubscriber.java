package org.lushen.mrh.boot.canal.client;

import org.lushen.mrh.boot.canal.client.core.CanalSubscriber;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;

/**
* 测试客户端
*
* @author hlm
*/
public class CanalPrintSubscriber implements CanalSubscriber {
 
    @Override
    public void subscribe(Message message) throws Exception {
 
        for(CanalEntry.Entry entry : message.getEntries()) {
 
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN
                    || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
 
            CanalEntry.RowChange rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            CanalEntry.EventType eventType = rowChage.getEventType();
 
            System.out.println("--------------------------------------------------");
            System.out.println("logfileName : " + entry.getHeader().getLogfileName());
            System.out.println("logfileOffset : " + entry.getHeader().getLogfileOffset());
            System.out.println("schemaName : " + entry.getHeader().getSchemaName());
            System.out.println("tableName : " + entry.getHeader().getTableName());
            System.out.println("eventType : " + eventType);
 
            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                System.out.println("-------------------before-----------------------");
                for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                    System.out.println(column.getName() + " : " + column.getValue() + ", update=" + column.getUpdated());
                }
                System.out.println("-------------------after-----------------------");
                for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                    System.out.println(column.getName() + " : " + column.getValue() + ", update=" + column.getUpdated());
                }
            }
 
        }
 
    }
 
}