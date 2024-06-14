package com.example.allreader.utils.entity;

/**
 * Author: Eccentric
 * Created on 2024/6/14 16:01.
 * Description: com.example.allreader.utils.entity.EventMessage
 */
public class EventMessage {
    private int sortMethodId;
    private int orderMethodId;

    public EventMessage(int sortMethodId, int orderMethodId) {
        this.sortMethodId = sortMethodId;
        this.orderMethodId = orderMethodId;
    }

    public int getSortMethodId() {
        return sortMethodId;
    }

    public void setSortMethodId(int sortMethodId) {
        this.sortMethodId = sortMethodId;
    }

    public int getOrderMethodId() {
        return orderMethodId;
    }

    public void setOrderMethodId(int orderMethodId) {
        this.orderMethodId = orderMethodId;
    }
}
