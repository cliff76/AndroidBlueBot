package com.bluebot.injection;

import com.bluebot.MainActivity;
import com.bluebot.droid.ide.BlueBotIDEActivity;
import com.bluebot.runtime.bluetooth.BluetoothCommandSink;
import com.bluebot.runtime.runner.BlueCodeRunner;
import com.bluebot.ui.CodeRunnerRequestProcessor;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

/**
 * Created by Clifton Craig on 4/12/17.
 * Copyright GE 4/12/17
 */

class BlueBotIDEActivityModule implements InjectionModule{
    @Override
    public void inject(Object object) {
        injectActivity((BlueBotIDEActivity)object);
    }

    private void injectActivity(final BlueBotIDEActivity blueBotIDEActivity) {
        final CodeRunnerRequestProcessor requestProcessor = new CodeRunnerRequestProcessor(new BlueCodeRunner(new BluetoothCommandSink() {
            BluetoothSPP bluetooth = MainActivity.bluetooth;

            @Override
            public void send(String command) {
                bluetooth.send(command, true);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));
        requestProcessor.setRunIndefinitely(true);
        blueBotIDEActivity.setRequestProcessor(requestProcessor);
    }
}
