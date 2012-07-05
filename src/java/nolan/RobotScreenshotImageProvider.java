
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

import java.awt.*;

/**
 * Provides images by taking screenshots using Robot.
 * Subclasses should override done() to decide when to stop.
 */
public abstract class RobotScreenshotImageProvider implements ImageProvider {

    private final Rectangle screenSize;
    private final Robot robot;

    protected RobotScreenshotImageProvider(Robot robot) {
        this(robot, fullScreenSize());
    }

    protected RobotScreenshotImageProvider(Robot robot, Rectangle screenSize) {
        this.screenSize = screenSize;
        this.robot = robot;
    }

    private static Rectangle fullScreenSize() {
        return new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }

    @Override
    public int getWidth() {
        return screenSize.width;
    }

    @Override
    public int getHeight() {
        return screenSize.height;
    }

    @Override
    public byte[] next() {
        if(!done()) {
            return Image.toBytes(
                    robot.createScreenCapture(screenSize));
        }
        return null;
    }

    /**
     * @return true if stop providing images
     */
    protected abstract boolean done();

}
