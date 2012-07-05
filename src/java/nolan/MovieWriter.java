
// Copyright (C) 2012 by Pramati Technologies

// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at 
// http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package nolan;

import javax.media.*;
import javax.media.control.TrackControl;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.FileTypeDescriptor;
import javax.media.protocol.PullBufferDataSource;
import java.io.File;

public class MovieWriter implements ControllerListener {

    private boolean finished = false;
    private final Object waitSync = new Object();
    private boolean stateTransitionOK = true;

    public void write(String movieFile, PullBufferDataSource source) throws Exception {

        Processor processor = Manager.createProcessor(source);

        processor.addControllerListener(this);
        processor.configure();

        if (!waitForState(processor, Processor.Configured)) {
            System.err.println("Failed to configure the processor.");
            return;
        }

        processor.setContentDescriptor(new ContentDescriptor(FileTypeDescriptor.QUICKTIME));

        TrackControl trackControl = processor.getTrackControls()[0];
        trackControl.setFormat(trackControl.getSupportedFormats()[0]);

        processor.realize();

        if (!waitForState(processor, Processor.Realized)) {
            System.err.println("Failed to realize the processor.");
            return;
        }

        DataSink dataSink = Manager.createDataSink(
                processor.getDataOutput(),
                new MediaLocator(new File(movieFile).toURI().toURL()));

        dataSink.open();
        processor.start();
        dataSink.start();

        waitForFileDone();

        dataSink.close();
        processor.removeControllerListener(this);
    }

    boolean waitForState(Processor p, int state) {
        synchronized (waitSync) {
            try {
                while (p.getState() < state && stateTransitionOK)
                    waitSync.wait();
            } catch (Exception ignore) {
            }
        }
        return stateTransitionOK;
    }

    void waitForFileDone() {
        while (!finished) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void controllerUpdate(ControllerEvent evt) {
        if (evt instanceof ConfigureCompleteEvent
                || evt instanceof RealizeCompleteEvent
                || evt instanceof PrefetchCompleteEvent) {
            synchronized (waitSync) {
                stateTransitionOK = true;
                waitSync.notifyAll();
            }
        } else if (evt instanceof ResourceUnavailableEvent) {
            synchronized (waitSync) {
                stateTransitionOK = false;
                waitSync.notifyAll();
            }
        } else if (evt instanceof EndOfMediaEvent) {
            evt.getSourceController().stop();
            evt.getSourceController().close();
            finished = true;
        }
    }
}
