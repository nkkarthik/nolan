
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

import javax.imageio.ImageIO;
import javax.media.*;
import javax.media.control.TrackControl;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.FileTypeDescriptor;
import javax.media.protocol.PullBufferDataSource;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Image  {

    public static byte[] toBytes(RenderedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            byte[] bytes = baos.toByteArray();
            baos.close();
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
