package com.seetong.testcase.testcase_object;

import com.seetong.steps.BaseTestCaseStep;
import com.seetong.steps.POTestCaseStepBak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 映射yaml文件的类
 */
public class TestCaseModelBak {
    public HashMap<String, POTestCaseStepBak> caseModel;

    /**
     * 获取测试数据
     * @param model 对应yaml中的数据
     * @return 方法对应的测试步骤步骤
     */
    public static List<BaseTestCaseStep> getTestData(TestCaseModelBak model) {
        // 获取当前调用该方法的具体方法名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        // 进行初始化
        if (methodName.equals("init")) {
            List<BaseTestCaseStep> list = new ArrayList<>();
            list.add(model.caseModel.get(methodName));
            return list;
        }
        // 每次测试前回退到测试首页
        if (methodName.equals("backToTestPage")) {
            List<BaseTestCaseStep> list = new ArrayList<>();
            list.add(model.caseModel.get(methodName));
            return list;
        }
        // 测试结束后关闭驱动方法
        if (methodName.equals("quit")) {
            List<BaseTestCaseStep> list = new ArrayList<>();
            list.add(model.caseModel.get(methodName));
            return list;
        }

        return model.caseModel.get(methodName).testcaseGenerateBak();
    }
}
