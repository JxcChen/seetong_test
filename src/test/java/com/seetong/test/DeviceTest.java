package com.seetong.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.seetong.steps.BaseTestCaseStep;
import com.seetong.testcase.testcase_object.TestCaseModel;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * ClassName: PoTestcaseNewTest
 * date: 2021/1/5 8:52
 * @author JJJJ
 * Description:
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeviceTest {

    Logger logger = LoggerFactory.getLogger(DeviceTest.class);
    static ObjectMapper mapper;
    static TestCaseModel model;
    @BeforeAll
    public static void init() {
        try {
            // todo: 获取yaml数据并进行初始化
            mapper = new ObjectMapper(new YAMLFactory()) {
            };
            model = mapper.readValue(DeviceTestBak.class.getResourceAsStream("/testcase/device_test_data.yaml"), TestCaseModel.class);
            // 进行初始化
            TestCaseModel.getTestData(model).get(0).run();
            // 清空所有设备
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeEach
    public void backToTestPage() {
        TestCaseModel.getTestData(model).get(0).run();
    }

    @DisplayName("正常添加设备")
    @Description("添加设备用例")
    @Story("添加设备")
    @ParameterizedTest
    @MethodSource()
    @Order(1)
    void addDeviceSuccessTest(BaseTestCaseStep testCase) {
        testCase.run();
    }

    @DisplayName("通过局域网添加设备")
    @Description("通过局域网添加设备测试用例")
    @Story("通过局域网添加设备")
    @ParameterizedTest
    @MethodSource()
    @Order(2)
    void addDeviceThroughLanSuccessTest(BaseTestCaseStep testCase) {
        testCase.run();
    }

    @DisplayName("添加设备异常并有toast错误信息")
    @Description("添加设备异常用例")
    @Story("添加设备异常")
    @ParameterizedTest
    @MethodSource()
    @Order(3)
    void addDeviceFailWithToastTipsTest(BaseTestCaseStep testCase) {
        testCase.run();
    }

    @DisplayName("添加设备异常并有弹框错误提示")
    @Description("添加设备异常测试用例")
    @Story("添加设备异常")
    @ParameterizedTest
    @MethodSource()
    @Order(4)
    void addDeviceFailWithMessageTest(BaseTestCaseStep testCase) {
        testCase.run();
    }

    @DisplayName("搜索设备")
    @Description("搜索设备测试用例")
    @Story("搜索设备")
    @ParameterizedTest
    @MethodSource()
    @Order(5)
    void searchDeviceTest(BaseTestCaseStep testCase) {
        testCase.run();
    }

    @DisplayName("删除设备")
    @Description("删除设备测试用例")
    @Story("删除设备")
    @ParameterizedTest
    @MethodSource()
    @Order(6)
    void deleteDeviceTest(BaseTestCaseStep testCase) {
        testCase.run();
    }


    public static List<BaseTestCaseStep> addDeviceSuccessTest() {
        return TestCaseModel.getTestData(model);
    }

   public static List<BaseTestCaseStep> addDeviceFailWithToastTipsTest() {
        return TestCaseModel.getTestData(model);
    }

    public static List<BaseTestCaseStep> addDeviceFailWithMessageTest() {
        return TestCaseModel.getTestData(model);
    }

    public static List<BaseTestCaseStep> addDeviceThroughLanSuccessTest() {
        return TestCaseModel.getTestData(model);
    }

    public static List<BaseTestCaseStep> deleteDeviceTest() {
        return TestCaseModel.getTestData(model);
    }

    public static List<BaseTestCaseStep> searchDeviceTest() {
        return TestCaseModel.getTestData(model);
    }

}
