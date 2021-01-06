package com.seetong.steps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseTestCaseStep {

    public List<HashMap<String, Object>> data;
    public List<HashMap<String, String>> assertExpected;
    public List<AssertModel> asserts;
    public List<HashMap<String, Object>> steps;
    public int index = 0;



    /**
     * 将数据根据参数个数进行裂变
     * @return 裂变后的 case集合
     */
    public List<BaseTestCaseStep> testcaseGenerateBak() {
        List<BaseTestCaseStep> testCaseList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            POTestCaseStepBak poTestCase = new POTestCaseStepBak();
            poTestCase.data = data;
            poTestCase.steps = steps;
            poTestCase.index = i;
            testCaseList.add(poTestCase);
        }
        return testCaseList;
    }


    /**
     * 将数据根据参数个数进行裂变
     * @return 裂变后的 case集合
     */
    public List<BaseTestCaseStep> testcaseGenerate() {
        List<BaseTestCaseStep> testCaseList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            POTestCaseStep poTestCase = new POTestCaseStep();
            poTestCase.data = data;
            poTestCase.steps = steps;
            poTestCase.index = i;
            poTestCase.asserts = asserts;
            poTestCase.assertExpected = assertExpected;
            testCaseList.add(poTestCase);
        }
        return testCaseList;
    }


    /**
     * 将yaml里的数据进行参数化替换
     * @param step 执行的步骤
     * @param methodName 需要进行参数化的方法名
     * @return 进行参数化替换后的数据
     */
    public Object getValue(HashMap<String, Object> step, String methodName) {
        Object value = step.get(methodName);
        ArrayList<String> dataList = new ArrayList<>();
        // 如果是字符串则说明可能存在 需要替换的参数
        // 如果是列表则说明可能有多个参数需要替换
        if (value instanceof String) {
            String strValue = (String) value;
            // 将包含param的数据进行参数化替换
            // 将包含assert 进行断言的参数化替换
            if (strValue.contains("param")) {
                dataList.add(data.get(index).get(strValue).toString());
            }
            return dataList;
        } else if (value instanceof List) {
            // 如果为列表说明有多个参数
            List listValue = (ArrayList) value;
            if (listValue.size() > 0 && listValue.get(0).toString().contains("param")) {
                String s = listValue.get(0).toString();
                // 获取到具体内容  在根据内容获取到对应的param
                ArrayList<Object> list = new ArrayList<>();
                ((ArrayList<String>) data.get(index).get(s)).forEach((list)::add);
                return list;
            }
            return listValue;
        }
        return value;
    }

    public void run() {
    }

    public List<HashMap<String, Object>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, Object>> data) {
        this.data = data;
    }

    public List<AssertModel> getAsserts() {
        return asserts;
    }

    public void setAsserts(List<AssertModel> asserts) {
        this.asserts = asserts;
    }

    public List<HashMap<String, String>> getAssertExpected() {
        return assertExpected;
    }

    public void setAssertExpected(List<HashMap<String, String>> assertExpected) {
        this.assertExpected = assertExpected;
    }

    public List<HashMap<String, Object>> getSteps() {
        return steps;
    }

    public void setSteps(List<HashMap<String, Object>> steps) {
        this.steps = steps;
    }

}
