package com.seetong.testcase.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.seetong.testcase.testcase_object.BaseTestCase;
import com.seetong.testcase.testcase_object.TestCaseModel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeviceTest {

    static ObjectMapper mapper;
    static TestCaseModel model;

    @BeforeAll
    public static void init() {
        try {
            // todo: 获取yaml数据并进行初始化
            mapper = new ObjectMapper(new YAMLFactory()) {};
            model = mapper.readValue(DeviceTest.class.getResourceAsStream("/device_test_data.yaml"), TestCaseModel.class);
            // 进行初始化
            TestCaseModel.getTestData(model).get(0).run();
            // 清空所有设备
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @ParameterizedTest
    @MethodSource()
    @Order(1)
    void addDeviceSuccessTest(BaseTestCase testCase) {
        testCase.run();
    }

    @ParameterizedTest
    @MethodSource()
    @Order(2)
    void addDeviceFailWithToastTipsTest(BaseTestCase testCase) {
        testCase.run();
    }

    @ParameterizedTest
    @MethodSource()
    @Order(3)
    void addDeviceThroughLanSuccessTest(BaseTestCase testCase) {
        testCase.run();
    }

    @ParameterizedTest
    @MethodSource()
    @Order(3)
    void addDeviceFailWithMessageTest(BaseTestCase testCase) {
        testCase.run();
    }

    @ParameterizedTest
    @MethodSource()
    @Order(4)
    void searchDeviceTest(BaseTestCase testCase) {
        testCase.run();
    }

    @ParameterizedTest
    @MethodSource()
    @Order(5)
    void deleteDeviceTest(BaseTestCase testCase) {
        testCase.run();
    }



    public static List<BaseTestCase> addDeviceSuccessTest() {
        return TestCaseModel.getTestData(model);
    }

    public static List<BaseTestCase> addDeviceFailWithToastTipsTest() {
        return TestCaseModel.getTestData(model);
    }

    public static List<BaseTestCase> addDeviceFailWithMessageTest() {
        return TestCaseModel.getTestData(model);
    }

    public static List<BaseTestCase> addDeviceThroughLanSuccessTest() {
        return TestCaseModel.getTestData(model);
    }

    public static List<BaseTestCase> deleteDeviceTest() {
        return TestCaseModel.getTestData(model);
    }

    public static List<BaseTestCase> searchDeviceTest() {
        return TestCaseModel.getTestData(model);
    }


    @AfterAll
    public static void quit(){
        TestCaseModel.getTestData(model).get(0).run();
    }

}
