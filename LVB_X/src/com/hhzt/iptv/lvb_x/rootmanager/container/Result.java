
package com.hhzt.iptv.lvb_x.rootmanager.container;

import com.hhzt.iptv.lvb_x.rootmanager.utils.RootUtils;

/**
 * This class is used to store a root operation result which contains the result
 * of execution and details information.
 * 
 * @author Chris Jiang
 */
public class Result {

    /* members */
    private String message;
    private int statusCode;

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Boolean getResult() {
        RootUtils.Log("Status Code is " + statusCode);
        if (statusCode == 0) {
            return true;
        } else if (statusCode <= 100) {
            return true;
        } else {
            return false;
        }
    }

    private Result() {

    }

    public static ResultBuilder newBuilder() {
        return new ResultBuilder();
    }

    public static class ResultBuilder {
        private ResultEnum inEnum = null;

        public ResultBuilder setCommandSuccess() {
            inEnum = ResultEnum.RUNCOMMAND_SUCCESS;
            return this;
        }

        public ResultBuilder setCommandFailedTimeout() {
            inEnum = ResultEnum.RUNCOMMAND_FAILED_TIMEOUT;
            return this;
        }

        public ResultBuilder setCommandFailedDenied() {
            inEnum = ResultEnum.RUNCOMMAND_FAILED_DENIED;
            return this;
        }

        public ResultBuilder setCommandFailedInterrupted() {
            inEnum = ResultEnum.RUNCOMMAND_FAILED_INTERRUPTED;
            return this;
        }

        public ResultBuilder setCommandFailed() {
            inEnum = ResultEnum.RUNCOMMAND_FAILED;
            return this;
        }

        public ResultBuilder setInstallSuccess() {
            inEnum = ResultEnum.INSTALL_SUCCESS;
            return this;
        }

        public ResultBuilder setInsallFailedNoSpace() {
            inEnum = ResultEnum.INSTALL_FAILED_NOSPACE;
            return this;
        }

        public ResultBuilder setInstallFailedWrongContainer() {
            inEnum = ResultEnum.INSTALL_FAILED_WRONGCONTAINER;
            return this;
        }

        public ResultBuilder setInstallFailedWrongCer() {
            inEnum = ResultEnum.INSTALL_FAILED_WRONGCER;
            return this;
        }

        public ResultBuilder setInstallFailed() {
            inEnum = ResultEnum.INSTALL_FIALED;
            return this;
        }

        public ResultBuilder setUninstallSuccess() {
            inEnum = ResultEnum.UNINSTALL_SUCCESS;
            return this;
        }

        public ResultBuilder setUninstallFailed() {
            inEnum = ResultEnum.UNINSTALL_SUCCESS;
            return this;
        }

        public ResultBuilder setFailed() {
            inEnum = ResultEnum.FAILED;
            return this;
        }

        public ResultBuilder setCustomMessage(String customMessage) {
            inEnum = ResultEnum.CUSTOM;
            inEnum.setCustomMessage(customMessage);
            return this;
        }

        public Result build() {
            if (inEnum == null) {
                throw new IllegalStateException(
                        "Get a empty or null error message during command execution, can not generate result object");
            }

            Result re = new Result();
            re.message = inEnum.getMessage();
            re.statusCode = inEnum.getStatusCode();
            return re;
        }
    }

    public enum ResultEnum {

        RUNCOMMAND_SUCCESS(90, "Command Executed Successfully"), RUNCOMMAND_FAILED_TIMEOUT(401,
                "Run Command Timeout"), RUNCOMMAND_FAILED_DENIED(402,
                "Run Command Permission Denied"), RUNCOMMAND_FAILED_INTERRUPTED(403,
                "Run Command Interrupted"), RUNCOMMAND_FAILED(409, "Run Command Failed"),

        INSTALL_SUCCESS(80, "Application installed Successfully"), INSTALL_FAILED_NOSPACE(404,
                "Install Failed because of no enough space"), INSTALL_FAILED_WRONGCONTAINER(405,
                "Install Failed Wrong container"), INSTALL_FAILED_WRONGCER(406,
                "Install Failed Wrong Cer or version"), INSTALL_FIALED(407, "Install Failed"),

        UNINSTALL_SUCCESS(70, "Application uninstall Successfully"), UNINSTALL_FAILED(408,
                "Uninstall App Failed"),

        FAILED(409, "Illegal Parameters or State"), CUSTOM(0, "");

        private int statusCode;
        private String message;

        private ResultEnum(int sc, String msg) {
            statusCode = sc;
            message = msg;
        }

        public void setCustomMessage(String customMessage) {
            message = customMessage;
        }

        public String getMessage() {
            return message;
        }

        public int getStatusCode() {
            return statusCode;
        }

    }
}
