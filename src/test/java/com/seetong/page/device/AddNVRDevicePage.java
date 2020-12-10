package com.seetong.page.device;

import com.seetong.page.main.BasePage;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AddNVRDevicePage extends BasePage {

    public AddNVRDevicePage(WebDriver driver) {
        super(driver);
    }

    private By addDeviceBtn = By.id("device_add");
    private MobileBy NVR = new MobileBy.ByAndroidUIAutomator("new UiSelector().text(\"网络硬盘录像机\")");
    private By deviceIdInput = By.id("device_add_id");
    private By devicePasswordInput = By.id("device_add_password");
    private By confirmBtn = new MobileBy.ByAndroidUIAutomator("new UiSelector().text(\"确定添加\")");
    private By toastTips = By.xpath("//*[contains(@text,'不合法')]");
    private By goBackBtn = By.id("add_device_back_rl");
    // 添加失败错误提示框信息
    private By message = By.xpath("//android.widget.TextView");
    private By closeBtn = By.xpath("//*[@text='关闭']");

    //通过局域网添加设备
    private By addDeviceLanBtn = By.id("device_add_add_lan");
    private By addDeviceLanCommitBtn = By.id("lan_device_add");
    private By passwordInput = new MobileBy.ByAndroidUIAutomator("new UiSelector().text(\"输入密码\")");
    private By addDeviceLanConfirmBtn = new MobileBy.ByAndroidUIAutomator("new UiSelector().text(\"确认\")");


    /**
     * 添加设备
     *
     * @param deviceId       设备ID
     * @param devicePassword 设备密码
     * @return
     */
    public AddNVRDevicePage addDevice(String deviceId, String devicePassword) {
        clickElement(addDeviceBtn);
        clickElement(NVR);
        sendKey(deviceIdInput, deviceId);
        sendKey(devicePasswordInput, devicePassword);
        clickElement(confirmBtn);
        return this;
    }

    public AddNVRDevicePage addDeviceThroughLan(String deviceId, String devicePassword) throws Exception {

        clickElement(addDeviceBtn);
        clickElement(NVR);
        clickElement(addDeviceLanBtn);
        Thread.sleep(10000);
        clickElement(By.xpath(
                "//*[@text='" + deviceId + "']" +
                        "/following-sibling::" +
                        "*[@resource-id='com.seetong.app.seetong:id/dev_check']"));
        clickElement(addDeviceLanCommitBtn);
        sendKey(passwordInput, devicePassword);
        clickElement(addDeviceLanConfirmBtn);

        return this;
    }

    /**
     * 获取toast中的错误信息
     *
     * @return toastTipsContent
     */
    public String getAddDeviceToastTips() {
        String toastTipsContent = driver.findElement(toastTips).getText();
        clickElement(goBackBtn);
        return toastTipsContent;
    }

    /**
     * 获取toast中的错误弹框信息
     *
     * @return toastTipsContent
     */
    public String getAddDeviceMessage() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String messageContent = "";
        List<WebElement> elements = driver.findElements(message);
        for (WebElement element : elements
        ) {
            messageContent += element.getText();
        }
        clickElement(closeBtn);
        clickElement(goBackBtn);
        return messageContent;
    }


}
