package com.hhzt.iptv.lvb_x.utils;

import android.app.Instrumentation;

public class VirturlKeyPadCtr {
    private static Instrumentation mInstrumentation;

    public static void RC_ConttrollerAction(final int keyValue) {
        /*
		 * Start the key-simulation in a thread so we do not block the GUI.
		 */
        new Thread(new Runnable() {
            public void run() {
				/* Simulate a KeyStroke to the menu-button. */
                simulateKeystroke(keyValue);
            }
        }).start(); /* And start the Thread. */
    }

    /**
     * Wrapper-function taking a KeyCode. A complete KeyStroke is DOWN and UP
     * Action on a key!
     */
    public static void simulateKeystroke(int KeyCode) {
        if (mInstrumentation == null) {
            mInstrumentation = new Instrumentation();
        }
        mInstrumentation.sendKeyDownUpSync(KeyCode);
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 100) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isFastDoubleOnKey(long date) {
        long time = System.currentTimeMillis();
        if (date - time < 500) {
            return true;
        }
        return false;
    }
}
