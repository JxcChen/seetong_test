package com.seetong.page.device;

import com.seetong.page.main.BasePage;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DevicePage extends BasePage {


    public DevicePage(WebDriver driver) {
        super(driver);
    }

    public AddNVRDevicePage addNVRDevicePage = new AddNVRDevicePage(driver);

    private By deviceBtn = new MobileBy.ByAndroidUIAutomator("new UiSelector().text(\"设备\")");

    // 预览界面回退按钮
    private By goBackBtn = By.id("player_back");


    private By addDeviceResult = By.id("help_content");
    // 添加成功后的提示框按钮
    private By iKnowBtn = By.id("i_know_button");


    private By deleteDeviceBtn = By.id("device_del_device");
    private By deleteConfirmBtn = new MobileBy.ByAndroidUIAutomator("new UiSelector().text(\"删除\")");

    private By searchBtn = By.id("device_search");
    private By searchInput = By.id("etSearchDevice");
    private By searchCancelBtn = By.id("search_cancel");


    /**
     * 删除所有设备
     *
     * @return DevicePage
     */
    public DevicePage clearAllDevices() {
        while (true) {
            try {
                List<WebElement> elements = driver.findElements(By.id("listview_item_setting_entry"));
                elements.get(0).click();
                clickElement(deleteDeviceBtn);
                clickElement(deleteConfirmBtn);
            } catch (Exception e) {
                break;
            }
        }
        return this;
    }

    /**
     * 回退到设备测试首页
     */
    public void backToIndex() {
        while (true) {
            try {
                WebDriverWait driverWait = new WebDriverWait(driver, 2);
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(searchBtn));
                break;
            } catch (Exception e) {
                swipe("back");
            }
        }
    }

    /**
     * 正常添加设备
     *
     * @param deviceId       设备ID
     * @param devicePassword 设备密码
     * @return DevicePage
     */
    public DevicePage addDeviceSuccess(String deviceId, String devicePassword) {
        try {
            addNVRDevicePage.addDevice(deviceId, devicePassword);
        } catch (Exception e) {
            backToIndex();
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 正确进行局域网添加设备
     *
     * @param deviceId       设备ID
     * @param devicePassword 设备密码
     * @return DevicePage
     */
    public DevicePage addDeviceThroughLanSuccess(String deviceId, String devicePassword) {
        try {
            addNVRDevicePage.addDeviceThroughLan(deviceId, devicePassword);
            clickElement(iKnowBtn);
            clickElement(goBackBtn);
        } catch (Exception e) {
            backToIndex();
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 获取添加成功设备结果
     *
     * @return 添加设备结果
     */
    public String getAddDeviceSuccessResult() {
        String addResult = "";
        try {
            Thread.sleep(3000);
            addResult = getElementText(addDeviceResult);
            clickElement(iKnowBtn);
            clickElement(goBackBtn);
        } catch (InterruptedException e) {
            backToIndex();
            e.printStackTrace();
        }
        return addResult;
    }

    /**
     * 异常添加设备
     *
     * @param deviceId       设备ID
     * @param devicePassword 设备密码
     * @return FailDevicePage
     */
    public DevicePage addDeviceFail(String deviceId, String devicePassword) {
        try {
            addNVRDevicePage.addDevice(deviceId, devicePassword);
        } catch (Exception e) {
            backToIndex();
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 获取添加设备 ID异常时toast的错误信息
     *
     * @return FailToastTips
     */
    public String getAddDeviceFailToastTips() {
        return addNVRDevicePage.getAddDeviceToastTips();
    }

    /**
     * 获取添加设备 ID异常时弹框错误信息
     *
     * @return FailMessage
     */
    public String getAddDeviceFailMessage() {
        return addNVRDevicePage.getAddDeviceMessage();
    }

    /**
     * 删除设备
     *
     * @param deviceId 设备ID
     * @return DevicePage
     */
    public DevicePage deleteDevice(String deviceId) {
        try {
            clickElement(By.xpath(
                    "//*[@text='" + deviceId + "']" +
                            "/following-sibling::" +
                            "*[@resource-id='com.seetong.app.seetong:id/listview_item_setting_entry']"));
            clickElement(deleteDeviceBtn);
            clickElement(deleteConfirmBtn);
        } catch (Exception e) {
            backToIndex();
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 搜索设备
     *
     * @param deviceId 设备ID
     * @return DevicePage
     */
    public DevicePage searchDevice(String deviceId) {
        try {
            clickElement(searchBtn);
            sendKey(searchInput, deviceId);
        } catch (Exception e) {
            backToIndex();
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 获取搜索结果
     *
     * @param deviceId 设备ID
     * @return 搜索结果
     */
    public String searchAndGetResult(String deviceId) {
        searchDevice(deviceId);
        List elements = driver.findElements(By.xpath("//android.widget.TextView"));
        StringBuilder result = new StringBuilder();
        elements.forEach(element -> {
            result.append(((WebElement) element).getText());
        });
        clickElement(searchCancelBtn);
        return result.toString();
    }

}
