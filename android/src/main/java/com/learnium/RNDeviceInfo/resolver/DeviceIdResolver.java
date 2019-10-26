package com.learnium.RNDeviceInfo.resolver;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DeviceIdResolver {

    private final Context context;

    public DeviceIdResolver(Context context){
        this.context = context;
    }

    public String getInstanceIdSync() {
        try {
            return getFirebaseInstanceId();
        } catch (ClassNotFoundException e) {
            System.err.println("N/A: Add com.google.firebase:firebase-iid to your project.");
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("N/A: Unsupported version of com.google.firebase:firebase-iid in your project.");
        }

        try {
            return getGmsInstanceId();
        } catch (ClassNotFoundException e) {
            System.err.println("N/A: Add com.google.android.gms.iid to your project.");
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("N/A: Unsupported version of com.google.android.gms.iid in your project.");
            e.printStackTrace();
        }

        return "unknown";
    }

    String getGmsInstanceId() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?> clazz = Class.forName("com.google.android.gms.iid.InstanceID");
        Method method = clazz.getDeclaredMethod("getInstance", Context.class);
        Object obj = method.invoke(null, context);
        Method method1 = obj.getClass().getMethod("getId");
        return (String) method1.invoke(obj);
    }

    String getFirebaseInstanceId() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?> clazz = Class.forName("com.google.firebase.iid.FirebaseInstanceId");
        Method method = clazz.getDeclaredMethod("getInstance");
        Object obj =method.invoke(null);
        Method method1 = obj.getClass().getMethod("getId");
        return (String) method1.invoke(obj);
    }
}
