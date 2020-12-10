package com.seetong.testcase.testcase_object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestCaseModel {
    public HashMap<String, POTestCase> caseModel;

    /**
     * 放回方法对应的步骤
     *
     * @param model 对应yaml中的数据
     * @return 方法对应的步骤
     */
    public static List<BaseTestCase> getTestData(TestCaseModel model) {
        // 获取当前调用该方法的具体方法名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        // 进行初始化
        if (methodName.equals("init")) {
            List<BaseTestCase> list = new ArrayList<>();
            list.add(model.caseModel.get(methodName));
            return list;
        }
        // 每次测试前回退到测试首页
        if (methodName.equals("backToTestPage")) {
            List<BaseTestCase> list = new ArrayList<>();
            list.add(model.caseModel.get(methodName));
            return list;
        }
        // 测试结束后关闭驱动方法
        if (methodName.equals("quit")) {
            List<BaseTestCase> list = new ArrayList<>();
            list.add(model.caseModel.get(methodName));
            return list;
        }

        return model.caseModel.get(methodName).testcaseGenerate();
    }
}
