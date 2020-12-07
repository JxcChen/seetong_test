package com.seetong.page;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MainPage extends BasePage {

    public MainPage(){
        this.initDriver();
    }

    /**
     * 初始化驱动
     */
    public void initDriver(){
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platForm","Android");
            capabilities.setCapability("deviceName","mate40");
            capabilities.setCapability("appPackage","com.seetong.app.seetong");
            capabilities.setCapability("appActivity",".ui.WelcomeActivity");
            capabilities.setCapability("noReset",true);
            capabilities.setCapability("dontStopAppOnReset","true");
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备页面
     * @return
     */
    public DevicePage devicePage(){
        return new DevicePage(driver);
    }


    /**
     * 结束退出驱动
     */
    public void quit(){
        driver.closeApp();
    }

}
