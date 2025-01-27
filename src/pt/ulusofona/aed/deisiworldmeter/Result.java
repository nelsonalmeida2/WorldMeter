package pt.ulusofona.aed.deisiworldmeter;

public class Result {
        boolean success;
        String error;
        String result;

        public Result(boolean success, String error, String result) {
            this.success = success;
            this.error = error;
            this.result = result;
        }

        public Result () {}

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

}
