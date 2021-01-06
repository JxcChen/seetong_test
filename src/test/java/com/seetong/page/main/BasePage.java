package com.seetong.page.main;

import com.seetong.common.Constant;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;

public class BasePage {

    public Logger logger = Logger.getLogger(BasePage.class);
    public static HashMap<String, BasePage> pages = new HashMap<>();
    public static String actResult;
    public WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage() {
    }

    public static BasePage instance = null;


    /**
     * 单例模式获取BasePage实例
     *
     * @return BasePage页面实例
     */
    public static BasePage getInstance() {
        if (instance == null) {
            instance = new BasePage();
        }
        return instance;
    }


    /**
     * 通过反射进行页面初始化
     *
     * @param objectName 对象名
     * @param className  全类名
     */
    public void initPo(String objectName, String className) {
        try {
            logger.info("进行页面初始化");
            BasePage page = (BasePage) Class.forName(className).newInstance();
            // todo: 将页面存下来
            pages.put(objectName, page);
        } catch (Exception e) {
            logger.error("页面初始化失败");
            e.printStackTrace();
        }
    }

    /**
     * 获取保存的页面
     *
     * @param objectName 页面对象名
     * @return BasePage
     */
    public BasePage getPage(String objectName) {
        BasePage basePage = null;
        try {
            logger.info("获取"+objectName+"页面");
            basePage = pages.get(objectName);
        } catch (Exception e) {
            logger.error("获取"+objectName+"页面失败");
            e.printStackTrace();
        }
        return basePage;
    }

    /**
     * 获取测试结果 用于断言
     *
     * @return 测试结果
     */
    public String getActResult() {
        return actResult;
    }

    /**
     * 利用反射执行页面对应的方法 并获取返回值
     *
     * @param methodName 方法名
     * @param objectName 调用该方法的对象名
     * @param params     参数
     */
    public void runStep(String methodName, String objectName, ArrayList<String> params) {
        try {
            logger.info("执行"+objectName+"的"+methodName+"方法");
            Method runMethod = Arrays.stream(this.getClass().getMethods()).filter(method -> method.getName().equals(methodName)).findFirst().get();
            Object result = runMethod.invoke(this, params.toArray());
            // 判断返回值是页面还是字符串 是页面则存入pages 是字符串则存入actResults
            if (result instanceof BasePage) {
                if (objectName.equals("mainPage")) {
                    pages.put(methodName, (BasePage) result);
                } else {
                    pages.put(objectName, (BasePage) result);
                }
            } else if (result instanceof String) {
                actResult = result.toString();
            }
        } catch (Exception e) {
            logger.error("执行"+objectName+"的"+methodName+"方法失败");
            e.printStackTrace();
        }

    }


    /**
     * 等待元素可见
     *
     * @param locator 目标元素定位器
     * @return 目标元素
     */
    public WebElement waitElementVisible(By locator) {
        try {
            logger.info("等待"+locator+"元素可见");
            WebDriverWait wait = new WebDriverWait(driver, Constant.WAIT_TIME);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("定位"+locator+"元素超时");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 等待元素可被点击
     *
     * @param locator 目标元素定位器
     * @return 目标元素
     */
    public WebElement waitElementClickable(By locator) {
        try {
            logger.info("等待"+locator+"元素可可被点击");
            WebDriverWait wait = new WebDriverWait(driver, Constant.WAIT_TIME);
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("定位"+locator+"元素超时");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 点击元素
     *
     * @param locator 目标元素定位器
     */
    public void clickElement(By locator) {
        logger.info("点击"+locator+"元素");
        waitElementClickable(locator).click();
    }

    /**
     * 对元素输入内容
     *
     * @param locator 目标元素定位器
     * @param key     输入的内容
     */
    public void sendKey(By locator, String key) {
        logger.info("对"+locator+"元素输入："+key);
        WebElement webElement = waitElementVisible(locator);
        webElement.sendKeys(key);
    }


    public String getElementText(By locator) {
        logger.info("获取"+locator+"文本信息");
        return waitElementVisible(locator).getText();
    }

    /**
     * 滑动屏幕操作
     *
     * @param direction 滑动方向
     */
    public void swipe(String direction) {
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
        Duration duration = Duration.ofMillis(600);
        if (direction.equals("down")) {
            swipeDown(width, height, touchAction, duration);
        } else if (direction.equals("up")) {
            swipeUp(width, height, touchAction, duration);
        } else if (direction.equals("right")) {
            swipeRight(width, height, touchAction, duration);
        } else if (direction.equals("left")) {
            swipeLeft(width, height, touchAction, duration);
        } else if (direction.equals("back")) {
            swipeBack(width, height, touchAction, duration);
        } else {
            System.out.println("方向有误");
        }
    }

    /**
     * 向下滚动
     */
    public void swipeDown(int width, int height, TouchAction touchAction, Duration duration) {
        logger.info("向下滑动");
        touchAction
                .press(PointOption.point(width / 2, height / 2)) // 按住开始坐标
                .waitAction(WaitOptions.waitOptions(duration)) // 设置执行时长
                .moveTo(PointOption.point(width / 2, height / 6)) //移动到达的位置
                .release() // 放开鼠标
                .perform(); // 执行操作
    }

    /**
     * 向上滚动
     */
    public void swipeUp(int width, int height, TouchAction touchAction, Duration duration) {
        logger.info("向上滑动");
        touchAction
                .press(PointOption.point(width / 2, height / 4))
                .waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(width / 2, height / 4 * 3))
                .release()
                .perform();
    }

    /**
     * 向右滚动
     */
    public void swipeRight(int width, int height, TouchAction touchAction, Duration duration) {
        logger.info("向右滑动");
        touchAction
                .press(PointOption.point(width / 2, height / 2))
                .waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(width / 6, height / 2))
                .release()
                .perform();
    }


    /**
     * 向左滚动
     */
    public void swipeLeft(int width, int height, TouchAction touchAction, Duration duration) {
        logger.info("向左滑动");
        touchAction
                .press(PointOption.point(width / 4, height / 2))
                .waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(width / 4 * 3, height / 2))
                .release()
                .perform();
    }

    /**
     * 向左滚动
     */
    public void swipeBack(int width, int height, TouchAction touchAction, Duration duration) {
        logger.info("滑动回退");
        touchAction
                .press(PointOption.point(1, height / 2))
                .waitAction(WaitOptions.waitOptions(duration))
                .moveTo(PointOption.point(width / 2, height / 2))
                .release()
                .perform();
    }

    /**
     * 滑动到元素可见
     *
     * @param TextSelector 文本定位符
     */
    public void swipeToTargetElement(String TextSelector) {
        logger.info("滑动到"+TextSelector+"元素位置");
        ((AndroidDriver)driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                        ".scrollIntoView(new UiSelector().textMatches(\"" + TextSelector + "\").instance(0))")
                .click();
    }

    /**
     * 回退到测试首页
     */
    public void backToIndex() {
        logger.info("返回到测试首页");
    }

}
