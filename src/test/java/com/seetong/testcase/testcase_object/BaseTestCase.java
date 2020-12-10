package com.seetong.testcase.testcase_object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseTestCase {

    public List<HashMap<String, Object>> data;
    public List<HashMap<String, Object>> steps;
    public int index = 0;


    /**
     * 将数据根据参数个数进行裂变
     *
     * @return
     */
    public List<BaseTestCase> testcaseGenerate() {
        List<BaseTestCase> testCaseList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            POTestCase poTestCase = new POTestCase();
            poTestCase.data = data;
            poTestCase.steps = steps;
            poTestCase.index = i;
            testCaseList.add(poTestCase);
        }
        return testCaseList;
    }


    public Object getValue(HashMap<String, Object> step, String methodName) {
        Object value = step.get(methodName);
        ArrayList<String> dataList = new ArrayList<>();
        // 如果是字符串则说明可能存在 需要替换的参数
        // 如果是数组则说明有多个参数
        if (value instanceof String) {
            String strValue = (String) value;
            if (strValue.contains("param")) {
                dataList.add(data.get(index).get(strValue).toString());
            } else if (strValue.contains("assert")) {
                dataList.add(strValue);
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

}
