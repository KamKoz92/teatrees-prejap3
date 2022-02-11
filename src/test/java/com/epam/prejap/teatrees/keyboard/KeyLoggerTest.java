package com.epam.prejap.teatrees.keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class KeyLoggerTest implements KeySubscriber {
    private int keyPressCount = 0;
    private int rKeyPressCount = 0;

    @Test
    public void testKeyPressed() {
        KeyLoggerMock k = new KeyLoggerMock();
        SoftAssert sa = new SoftAssert();
        k.subscribeForKeys(this, Key.VC_ALL);

        k.keyPressed(Key.VC_R);
        sa.assertEquals(keyPressCount, 1);
        sa.assertEquals(rKeyPressCount, 1);

        k.keyPressed(Key.VC_SPACE);
        sa.assertEquals(keyPressCount, 2);
        sa.assertEquals(rKeyPressCount, 1);

        k.unsubscribeForKeys(this, Key.VC_ALL);

        k.keyPressed(Key.VC_R);
        sa.assertEquals(keyPressCount, 2);
        sa.assertEquals(rKeyPressCount, 1);

        k.keyPressed(Key.VC_SPACE);
        sa.assertEquals(keyPressCount, 2);
        sa.assertEquals(rKeyPressCount, 1);

        sa.assertAll();
    }

    @Override
    public void accept(Key key) {
        if (key.equals(Key.getKey(Key.VC_R))) {
            rKeyPressCount++;
        }
        keyPressCount++;
    }
}

class KeyLoggerMock implements KeyLogger {

    private Map<Key, List<KeySubscriber>> subscribers;

    KeyLoggerMock() {
        subscribers = new HashMap<Key, List<KeySubscriber>>();
        subscribers.put(Key.getKey(Key.VC_ALL), new ArrayList<KeySubscriber>());
    }

    public void keyPressed(int keyCode) {
        Key key = Key.getKey(keyCode);
        if (subscribers.containsKey(key)) {
            subscribers.get(key).forEach(sub -> sub.accept(key));
        }
        subscribers.get(Key.getKey(Key.VC_ALL)).forEach(sub -> sub.accept(key));
    }

    @Override
    public void subscribeForKeys(KeySubscriber event, int... keyCodes) {
        for (int code : keyCodes) {
            Key key = Key.getKey(code);
            if (!subscribers.containsKey(key)) {
                subscribers.put(key, new ArrayList<KeySubscriber>(Arrays.asList(event)));
            } else {
                subscribers.get(key).add(event);
            }
        }
    }

    @Override
    public void unsubscribeForKeys(KeySubscriber event, int... keyCodes) {
        for (int code : keyCodes) {
            Key key = Key.getKey(code);
            if (subscribers.containsKey(key) &&
                    subscribers.get(key).contains(event)) {
                subscribers.get(key).remove(event);
            }
        }
    }

}