package com.seetong.page;

import com.seetong.common.Constant;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;

public class BasePage {


    public static HashMap<String,BasePage> pages = new HashMap<>();
    public static String actResult ;
    public AndroidDriver driver;
    public BasePage(AndroidDriver driver){
        this.driver = driver;
    }
    public BasePage(){}
    public static BasePage instance = null;


    /**
     * 单例模式获取BasePage实例
     * @return BasePage页面实例
     */
    public static BasePage getInstance() {
        if (instance == null){
            instance = new BasePage();
        }
        return instance;
    }



    /**
     * 通过反射进行页面初始化
     * @param objectName 对象名
     * @param className 全类名
     */
    public void initPo(String objectName, String className) {
        try {
            BasePage page = (BasePage) Class.forName(className).newInstance();
            // todo: 将页面存下来
            pages.put(objectName,page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取保存的页面
     * @param objectName 页面对象名
     * @return BasePage
     */
    public BasePage getPage(String objectName) {
        return pages.get(objectName);
    }

    /**
     * 获取测试结果 用于断言
     * @return 测试结果
     */
    public String getActResult(){
        return actResult;
    }

    /**
     * 利用反射执行页面对应的方法 并获取返回值
     * @param methodName 方法名
     * @param objectName 调用该方法的对象名
     * @param params 参数
     */
    public void runStep(String methodName, String objectName, ArrayList<String> params) {
        try {
            Method runMethod = Arrays.stream(this.getClass().getMethods()).filter(method -> method.getName().equals(methodName)).findFirst().get();
            Object result = runMethod.invoke(this, params.toArray());
            // 判断返回值是页面还是字符串 是页面则存入pages 是字符串则存入actResults
            if (result instanceof BasePage){
                if (objectName.equals("mainPage")){
                    pages.put(methodName, (BasePage) result);
                }else{
                    pages.put(objectName, (BasePage) result);
                }
            }else if(result instanceof String){
                actResult = result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 等待元素可见
     * @param locator 目标元素定位器
     * @return 目标元素
     */
    public WebElement waitElementVisible(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Constant.WAIT_TIME);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * 等待元素可被点击
     * @param locator 目标元素定位器
     * @return 目标元素
     */
    public WebElement waitElementClickable(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Constant.WAIT_TIME);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * 点击元素
     * @param locator 目标元素定位器
     */
    public void clickElement(By locator){
        waitElementClickable(locator).click();
    }

    /**
     * 对元素输入内容
     * @param locator 目标元素定位器
     * @param key 输入的内容
     */
    public void sendKey(By locator, String key){
        WebElement webElement = waitElementVisible(locator);
        webElement.sendKeys(key);
    }



    public String getElementText(By locator){ return waitElementVisible(locator).getText(); }

    /**
     * 向下滚动
     */
    public void swipeDown(){
        // 获取页面尺寸
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        // 1.创建touchAction对象
        TouchAction touchAction = new TouchAction(driver);
        // 2.创建Duration对象 单位为毫秒
        Duration duration = Duration.ofMillis(600);

        // 3.添加滑动操作
        touchAction
                .press(PointOption.point(width/2,height/2)) // 按住开始坐标
                .waitAction(WaitOptions.waitOptions(duration)) // 设置执行时长
                .moveTo(PointOption.point(width/2,height/5)) //移动到达的位置
                .release() // 放开鼠标
                .perform(); // 执行操作
    }


}
