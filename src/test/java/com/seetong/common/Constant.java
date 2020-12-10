package com.seetong.common;

import org.openqa.selenium.remote.DesiredCapabilities;

public class Constant {

    // 显示等待时长
    public static final int WAIT_TIME = 10;
    // 隐式等待时长
    public static final int IMPLICITLY_WAIT_TIME = 15;
    // appium server地址
    public static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    // cap配置文件地址
    public static final String CAPABILITIES_FILE_PATH = "/capabilities.properties";
    // device测试相关数据
    public static final String DEVICE_TEST_DATA_FILE_PATH = "/device_test_data.yaml";

}
