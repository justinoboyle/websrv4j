package com.arrayprolc.websrv4j.listener;

public abstract class LogListener {

    public abstract void log(String prefix, String[] message);

    public void log(String prefix, String message) {
        log(new String[] { message });
    }

    public void log(String message) {
        log("[INFO]", message);
    }

    public void log(String[] message) { 
        log("[INFO]", message);
    }

    public void log(Exception e) {
        int i = 0;
        StackTraceElement[] sce = e.getStackTrace();
        String[] s = new String[sce.length];
        for (StackTraceElement st : sce) {
            s[i] = st.toString();
            i++;
        }
        log("ERROR", s);
    }

}
