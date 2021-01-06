package com.seetong.steps;

/**
 * ClassName: AssertModel
 * date: 2021/1/5 8:48
 *
 * @author JJJJ
 * Description:
 */
public class AssertModel {
    private String reason;
    private String matcher;
    private String expected;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMatcher() {
        return matcher;
    }

    public void setMatcher(String matcher) {
        this.matcher = matcher;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }
}
