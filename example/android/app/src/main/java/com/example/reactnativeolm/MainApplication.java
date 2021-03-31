package com.example.reactnativeolm;

import android.app.Application;
import android.content.Context;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactInstanceManager;
import com.facebook.soloader.SoLoader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import com.reactnativeolm.OlmSASPackage;
import com.reactnativeolm.OlmSessionPackage;
import com.reactnativeolm.OlmUtilityPackage;
import com.reactnativeolm.OlmPkSigningPackage;
import com.reactnativeolm.OlmPkEncryptionPackage;
import com.reactnativeolm.OlmPkDecryptionPackage;
import com.reactnativeolm.OlmOutboundGroupSessionPackage;
import com.reactnativeolm.OlmManagerPackage;
import com.reactnativeolm.OlmInboundGroupSessionPackage;
import com.reactnativeolm.OlmAccountPackage;

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost =
      new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
          return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          // Packages that cannot be autolinked yet can be added manually here, for OlmExample:
          // packages.add(new MyReactNativePackage());
          packages.add(new OlmSASPackage());
          packages.add(new OlmSessionPackage());
          packages.add(new OlmUtilityPackage());
          packages.add(new OlmPkSigningPackage());
          packages.add(new OlmPkEncryptionPackage());
          packages.add(new OlmPkDecryptionPackage());
          packages.add(new OlmOutboundGroupSessionPackage());
          packages.add(new OlmManagerPackage());
          packages.add(new OlmInboundGroupSessionPackage());
          packages.add(new OlmAccountPackage());
          return packages;
        }

        @Override
        protected String getJSMainModuleName() {
          return "index";
        }
      };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
    initializeFlipper(this, getReactNativeHost().getReactInstanceManager()); // Remove this line if you don't want Flipper enabled
  }

  /**
   * Loads Flipper in React Native templates.
   *
   * @param context
   */
  private static void initializeFlipper(Context context, ReactInstanceManager reactInstanceManager) {
    if (BuildConfig.DEBUG) {
      try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
        Class<?> aClass = Class.forName("com.reactnativeolmExample.ReactNativeFlipper");
        aClass
            .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
            .invoke(null, context, reactInstanceManager);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}
