package edu.training.network;

public interface OnTaskListener {
    void OnTaskCompleted(String jsonString);
    void OnTaskError(int code, String message, String error);
}
