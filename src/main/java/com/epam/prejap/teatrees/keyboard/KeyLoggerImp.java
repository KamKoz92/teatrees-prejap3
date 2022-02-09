package com.epam.prejap.teatrees.keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class KeyLoggerImp implements KeyLogger, NativeKeyListener {

    Map<Integer, List<ConsumerEvent>> subscribers;

    public KeyLoggerImp() {
        subscribers = new HashMap<Integer, List<ConsumerEvent>>();
        registerHook();
        addListener();
    }

    private void addListener() {

        GlobalScreen.addNativeKeyListener(this);
    }

    private void registerHook() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribeForKey(int keyCode, ConsumerEvent event) {
        if (!subscribers.containsKey(keyCode)) {
            subscribers.put(keyCode, new ArrayList<ConsumerEvent>(Arrays.asList(event)));
        } else {
            subscribers.get(keyCode).add(event);
        }

    }

    @Override
    public void unsubscribeForKey(int keyCode, ConsumerEvent event) {
        if (subscribers.containsKey(keyCode)) {
            if (subscribers.get(keyCode).contains(event)) {
                subscribers.get(keyCode).remove(event);
            }
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        if (subscribers.containsKey(event.getKeyCode())) {
            subscribers.get(event.getKeyCode()).forEach(sub -> sub.accept(event.getKeyCode()));
        }
    }
}
