友盟多渠道打包
http://stormzhang.com/devtools/2015/01/15/android-studio-tutorial6/
第一步 在AndroidManifest.xml里配置PlaceHolder
<meta-data
    android:name="UMENG_CHANNEL"
    android:value="${UMENG_CHANNEL_VALUE}" />
第二步 在build.gradle设置productFlavors
android {  
    productFlavors {
        xiaomi {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "xiaomi"]
        }
        _360 {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "_360"]
        }
        baidu {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "baidu"]
        }
        wandoujia {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "wandoujia"]
        }
    }  
}
或者批量修改

android {  
    productFlavors {
        xiaomi {}
        _360 {}
        baidu {}
        wandoujia {}
    }  

    productFlavors.all { 
        flavor -> flavor.manifestPlaceholders = [UMENG_CHANNEL_VALUE: name] 
    }
}
很简单清晰有没有？直接执行 ./gradlew assembleRelease ， 然后就可以静静的喝杯咖啡等待打包完成吧。


Android Studio中创建keystore
1.点击Build ,在下拉框中选择 "Generate Signed APK"
2.选择 "Create new"
3.按照里面的内容填写即可，注意最后文件的扩展名变为".jks",而不是以前的".keystore".





