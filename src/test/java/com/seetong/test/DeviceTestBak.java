package com.seetong.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.seetong.common.Constant;
import com.seetong.steps.BaseTestCaseStep;
import com.seetong.testcase.testcase_object.TestCaseModelBak;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeviceTestBak {
    static ObjectMapper mapper;
    static TestCaseModelBak model;

    @BeforeAll
    public static void init() {
        try {
            // todo: 获取yaml数据并进行初始化
            mapper = new ObjectMapper(new YAMLFactory()) {
            };
            model = mapper.readValue(DeviceTestBak.class.getResourceAsStream(Constant.DEVICE_TEST_DATA_FILE_PATH), TestCaseModelBak.class);
            // 进行初始化
            TestCaseModelBak.getTestData(model).get(0).run();
            // 清空所有设备
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void quit() {
        TestCaseModelBak.getTestData(model).get(0).run();
    }

    @BeforeEach
    public void backToTestPage() {
        TestCaseModelBak.getTestData(model).get(0).run();
    }


    @ParameterizedTest
    @MethodSource()
    @Order(1)
    void addDeviceSuccessTest(BaseTestCaseStep testCase) {
        testCase.run();
    }

    @ParameterizedTest
    @MethodSource()
    @Order(2)
    void addDeviceThroughLanSuccessTest(BaseTestCaseStep testCase) {
        testCase.run();
    }

    @ParameterizedTest
    @MethodSource()
    @Order(3)
    void addDeviceFailWithToastTipsTest(BaseTestCaseStep testCase) {
        testCase.run();
    }

    @ParameterizedTest
    @MethodSource()
    @Order(4)
    void addDeviceFailWithMessageTest(BaseTestCaseStep testCase) {
        testCase.run();
    }

    @ParameterizedTest
    @MethodSource()
    @Order(5)
    void searchDeviceTest(BaseTestCaseStep testCase) {
        testCase.run();
    }

    @ParameterizedTest
    @MethodSource()
    @Order(6)
    void deleteDeviceTest(BaseTestCaseStep testCase) {
        testCase.run();
    }


    public static List<BaseTestCaseStep> addDeviceSuccessTest() {
        return TestCaseModelBak.getTestData(model);
    }

    public static List<BaseTestCaseStep> addDeviceFailWithToastTipsTest() {
        return TestCaseModelBak.getTestData(model);
    }

    public static List<BaseTestCaseStep> addDeviceFailWithMessageTest() {
        return TestCaseModelBak.getTestData(model);
    }

    public static List<BaseTestCaseStep> addDeviceThroughLanSuccessTest() {
        return TestCaseModelBak.getTestData(model);
    }

    public static List<BaseTestCaseStep> deleteDeviceTest() {
        return TestCaseModelBak.getTestData(model);
    }

    public static List<BaseTestCaseStep> searchDeviceTest() {
        return TestCaseModelBak.getTestData(model);
    }


}
