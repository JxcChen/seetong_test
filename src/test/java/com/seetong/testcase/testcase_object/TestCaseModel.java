package com.seetong.testcase.testcase_object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestCaseModel {
    public HashMap<String,POTestCase> caseModel;

    /**
     * 放回方法对应的步骤
     * @param model 对应yaml中的数据
     * @return 方法对应的步骤
     */
    public static List<BaseTestCase> getTestData(TestCaseModel model){
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

        if (methodName.equals("init")){
            List<BaseTestCase> list = new ArrayList<>();
            list.add(model.caseModel.get(methodName));
            return list;
        }
        if(methodName.equals("quit")){
            List<BaseTestCase> list = new ArrayList<>();
            list.add(model.caseModel.get(methodName));
            return list;
        }
        return model.caseModel.get(methodName).testcaseGenerate();
    }
}
