package io.romain.ppmtool.payload;

public class JWTLoginSUcessResponse {

    private boolean success;
    private String token;

    public JWTLoginSUcessResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JWTLoginSUcessResponse{" +
                "success=" + success +
                ", token='" + token + '\'' +
                '}';
    }
}
