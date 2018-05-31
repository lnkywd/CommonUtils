# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#指定压缩级别
-optimizationpasses 5

#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers

#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#把混淆类中的方法名也混淆了
-useuniqueclassmembernames

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

#将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute SourceFile

#保留行号
-keepattributes SourceFile,LineNumberTable

#可以把你的代码以及所使用到的各种第三方库代码统统移动到同一个包下
-repackageclasses

#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

# 保持测试相关的代码
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

## Support library
-dontwarn android.support.**
-keep public class android.support.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

-keep public class com.i500m.tutor.R$*{
   public static final int *;
}

-keepattributes Signature
-keepattributes InnerClasses

-keep public class com.i500m.libs.android.R$*{
   public static final int *;
}

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
# start  js
-keep class com.i500m.libs.android.view.** { *; }
# end   js
# Application classes that will be serialized/deserialized over Gson
-keep class com.i500m.libs.android.entries.** { *; }
##---------------End: proguard configuration for Gson  ----------
##---------------Begin: proguard configuration for BaseQuickAdapter  ----------
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
##---------------End: proguard configuration for BaseQuickAdapter  ----------
##---------------Begin: proguard configuration for PictureSelector 2.0  ----------
-keep class com.luck.picture.lib.** { *; }

-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

 #rxjava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#rxandroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#*****************eventbus start************

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#*****************eventbus end**************

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
##---------------End: proguard configuration for PictureSelector 2.0  ----------
##---------------Begin: proguard configuration for banner  ----------
# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }
##---------------End: proguard configuration for banner  ----------

##---------------Start: proguard configuration for 高德  ----------
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
##---------------End: proguard configuration for 高德  ----------

##---------------Start: proguard configuration for umeng  ----------
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
##---------------End: proguard configuration for umeng  ----------

##---------------Start: proguard configuration for greendao  ----------
-dontwarn org.greenrobot.greendao.**
-keep class org.greenrobot.greendao.** { *; }
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
    public static void dropTable(org.greenrobot.greendao.database.Database, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database, boolean);
}
-keep class **$Properties
##---------------End: proguard configuration for greendao  ----------

# 第三方库
-dontwarn javax.annotation.**
-keep class javax.annotation.**{ *; }
-dontwarn org.conscrypt.**
-dontwarn com.google.**
-keep class com.google.** { *; }
-dontwarn com.umeng.analytics.**
-keep class com.umeng.analytics.** { *; }
-dontwarn okhttp3.**
-keep class okhttp3.**{ *; }
-dontwarn okio.**
-keep class okio.**{ *; }
-dontwarn com.tencent.**
-keep class com.tencent.** { *; }
-dontwarn retrofit2.**
-keep class retrofit2.**{ *; }
-dontwarn android.databinding.**
-keep class android.databinding.**{ *; }
-dontwarn javax.inject.**
-keep class javax.inject.**{ *; }
-dontwarn io.reactivex.**
-keep class io.reactivex.**{ *; }
-dontwarn com.squareup.**
-keep class com.squareup.** { *; }
-dontwarn com.x91tec.appshelf.**
-keep class com.x91tec.appshelf.** { *; }
-dontwarn com.developer.bsince.log.**
-keep class com.developer.bsince.log.** { *; }
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }
-dontwarn com.r0adkll.slidr.**
-keep class com.r0adkll.slidr.** { *; }
-dontwarn com.scwang.smartrefresh.layout.**
-keep class com.scwang.smartrefresh.layout.** { *; }
-dontwarn com.trello.rxlifecycle2.**
-keep class com.trello.rxlifecycle2.** { *; }
-dontwarn com.yanzhenjie.permission.**
-keep class com.yanzhenjie.permission.** { *; }
-dontwarn org.greenrobot.eventbus.**
-keep class org.greenrobot.eventbus.** { *; }
-dontwarn cn.jzvd.**
-keep class cn.jzvd.** { *; }
-dontwarn net.lucode.hackware.magicindicator.**
-keep class net.lucode.hackware.magicindicator.** { *; }
-dontwarn com.zhy.view.flowlayout.**
-keep class com.zhy.view.flowlayout.** { *; }

