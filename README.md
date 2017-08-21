# NoobCoach

## 简介
轻量级的Android性能监控工具 支持fps cpu 内存监控

### 初始化
```java
    public class App extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            NoobCoach.startDefaultMeter(this);
        }
    }
```

## 备注
   注意权限添加：<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
   fps监控规则:
        绿：掉帧率<5%
        黄：掉帧率5%-20%
        红：掉帧率>20%