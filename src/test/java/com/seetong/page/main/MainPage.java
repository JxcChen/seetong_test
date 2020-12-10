package com.seetong.page.main;

import com.seetong.common.Constant;
import com.seetong.page.device.DevicePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.qameta.allure.util.PropertiesUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainPage extends BasePage {

    public MainPage() {
        this.initDriver();
    }

    /**
     * 初始化驱动
     */
    public void initDriver() {

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            Properties properties = new Properties();
            // 获取DesiredCapabilities信息
            InputStream resource = PropertiesUtils.class.getResourceAsStream(Constant.CAPABILITIES_FILE_PATH);
            properties.load(resource);
            Set<Object> keySet = properties.keySet();
            // 设置DesiredCapabilities配置信息
            for (Object key : keySet
            ) {
                String strKey = key.toString();
                capabilities.setCapability(strKey, properties.getProperty(strKey).toString());
            }
            String platformName = properties.get("platformName").toString();
            // 初始化driver
            switch(platformName){
                case "Android":
                    driver = new AndroidDriver(new URL(Constant.APPIUM_SERVER_URL), capabilities);
                case "IOS":
                    driver = new IOSDriver(new URL(Constant.APPIUM_SERVER_URL), capabilities);
            }
            driver.manage().timeouts().implicitlyWait(Constant.IMPLICITLY_WAIT_TIME, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备页面
     *
     * @return
     */
    public DevicePage devicePage() {
        return new DevicePage(driver);
    }


    /**
     * 结束退出驱动
     */
    public void quit() {
        driver.quit();
    }

}
