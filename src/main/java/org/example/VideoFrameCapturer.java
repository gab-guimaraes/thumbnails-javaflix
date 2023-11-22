package org.example;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoFrameCapturer {
    public static void main(String[] args) throws IOException {
        try {
            FFmpegFrameGrabber g = new FFmpegFrameGrabber("world_video.mp4");
            g.start();
            double frameRate = g.getFrameRate(); //tax frame per second
            double videoLengthInSeconds = g.getLengthInTime() / 1000000.0;
            int totalFrames = (int) (videoLengthInSeconds / 10);
            System.out.println(totalFrames);
            Frame capturedFrame;
            Java2DFrameConverter converter = new Java2DFrameConverter();
            for (int i = 0; i < totalFrames; i++) {
                g.setFrameNumber((int) (i * 10 * g.getFrameRate()));
                capturedFrame = g.grabKeyFrame();
                BufferedImage bi = converter.convert(capturedFrame);
                ImageIO.write(bi, "png", new File("video-frame-" + System.currentTimeMillis() + ".png"));
            }
            g.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}