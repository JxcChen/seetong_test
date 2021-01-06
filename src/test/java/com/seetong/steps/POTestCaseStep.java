package com.seetong.steps;

import com.seetong.page.main.BasePage;
import com.seetong.utils.PlaceholderUtils;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

public class POTestCaseStep extends BaseTestCaseStep {


    private ArrayList<Executable> assertList = new ArrayList<>();
    
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
                int i = (int) step.get("assert");
                String matcher = asserts.get(i).getMatcher();
                String reason = asserts.get(i).getReason();
                String runExpected = PlaceholderUtils.resolveString(asserts.get(i).getExpected(),assertExpected.get(index));
                String actResult = BasePage.getInstance().getActResult();
                if (matcher.equals("equalTo")) {
                    assertList.add(() ->{
                        assertThat(reason, actResult,equalTo(runExpected));
                    });
                }
                if (matcher.equals("containsString")) {
                    assertList.add(() ->{
                        assertThat(reason,actResult,containsString(runExpected));
                    });
                }
                if (matcher.equals("not_containsString")){
                    assertList.add(() ->{
                        assertThat(reason,actResult,not(containsString(runExpected)));
                    });
                }

            }
        });
        assertAll("全局断言",assertList.stream());
    }

}
