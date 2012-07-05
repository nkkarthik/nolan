
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

import javax.media.Buffer;
import javax.media.Format;
import javax.media.Time;
import javax.media.format.VideoFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PullBufferDataSource;
import javax.media.protocol.PullBufferStream;
import java.io.IOException;

public class MovieDataSource extends PullBufferDataSource
        implements PullBufferStream {

    private final VideoFormat format;
    private final ImageProvider images;

    public MovieDataSource(ImageProvider images) {
        this.images = images;

        float frameRate = 5;
        format = new VideoFormat(VideoFormat.JPEG,
                                 new java.awt.Dimension(
                                         images.getWidth(),
                                         images.getHeight()),
                                 Format.NOT_SPECIFIED,
                                 Format.byteArray,
                                 frameRate);
    }

    @Override
    public PullBufferStream[] getStreams() {
        return new PullBufferStream[] {this};
    }

    @Override
    public void read(Buffer buffer) throws IOException {
        byte[] img = images.next();
        if(img != null) {
            buffer.setData(img);
            buffer.setOffset(0);
            buffer.setLength(img.length);
            buffer.setFormat(getFormat());
            buffer.setFlags(buffer.getFlags() | Buffer.FLAG_KEY_FRAME);
        } else {
            buffer.setEOM(true);
            buffer.setOffset(0);
            buffer.setLength(0);
        }
    }

    @Override
    public String getContentType() {
        return ContentDescriptor.RAW;
    }

    @Override
    public void connect() throws IOException {
    }

    @Override
    public void disconnect() {
    }

    @Override
    public void start() throws IOException {
    }

    @Override
    public void stop() throws IOException {
    }

    @Override
    public Object getControl(String s) {
        return null;
    }

    @Override
    public Object[] getControls() {
        return new Object[0];
    }

    @Override
    public Time getDuration() {
        return DURATION_UNKNOWN;
    }

    @Override
    public boolean willReadBlock() {
        return false;
    }

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public ContentDescriptor getContentDescriptor() {
        return new ContentDescriptor(getContentType());
    }

    @Override
    public long getContentLength() {
        return 0;
    }

    @Override
    public boolean endOfStream() {
        return false;
    }
}
