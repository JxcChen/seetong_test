package com.seetong.steps;

import com.seetong.page.main.BasePage;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class POTestCaseStepBak extends BaseTestCaseStep {




    public void run() {
        steps.forEach(step -> {
            // 获取到每个key 用作判断依据
            String key = step.keySet().iterator().next();
            if (key.equals("init")) {
                // 获取值
                ArrayList<String> initValue = (ArrayList<String>) getValue(step, "init");
                // todo: 调用BasePage方法初始化页面
                BasePage.getInstance().initPo(initValue.get(0), initValue.get(1));
            }
            if (key.contains(".")) {
                // todo: 执行方法
                ArrayList<String> value = (ArrayList<String>) getValue(step, key);
                // 进行切分获取对象名和方法名
                String[] objectMethod = key.split("\\.");
                String objectName = objectMethod[0];
                String methodName = objectMethod[1];
                BasePage page = BasePage.getInstance().getPage(objectName);
                page.runStep(methodName, objectName, value);
            }
            if (key.equals("assert")) {
                // todo: 进行断言
                ArrayList<String> value = (ArrayList<String>) getValue(step, key);
                if (value.get(0).contains("assertEquals")) {
                    assertEquals(value.get(1), BasePage.getInstance().getActResult());
                }
                if (value.get(0).contains("assertTrue")) {
                    assertTrue(BasePage.getInstance().getActResult().contains(value.get(1)));
                }
                if (value.get(0).contains("assertFalse")) {
                    assertFalse(BasePage.getInstance().getActResult().contains(value.get(1)));
                }
            }
        });
    }

}
