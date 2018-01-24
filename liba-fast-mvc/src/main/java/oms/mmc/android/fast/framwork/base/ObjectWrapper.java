package oms.mmc.android.fast.framwork.base;

import oms.mmc.android.fast.framwork.bean.TplBase;

public class ObjectWrapper extends TplBase {
    private Object object;
    private Object object2;
    private Object object3;

    public ObjectWrapper() {

    }

    public ObjectWrapper(Object object) {
        this.object = object;
    }

    public ObjectWrapper(int viewType, Object object) {
        this.object = object;
        this.viewType = viewType;
    }

    public ObjectWrapper(Object object, Object object2) {
        this.object = object;
        this.object2 = object2;
    }

    public ObjectWrapper(int viewType, Object object, Object object2) {
        this.object = object;
        this.viewType = viewType;
        this.object2 = object2;
    }

    public ObjectWrapper(Object object, Object object2, Object object3) {
        this.object = object;
        this.object2 = object2;
        this.object3 = object3;
    }

    public ObjectWrapper(int viewType, Object object, Object object2, Object object3) {
        this.viewType = viewType;
        this.object = object;
        this.object2 = object2;
        this.object3 = object3;
    }

    public Object getObject3() {
        return object3;
    }

    public void setObject3(Object object3) {
        this.object3 = object3;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject2() {
        return object2;
    }

    public void setObject2(Object object2) {
        this.object2 = object2;
    }
}
