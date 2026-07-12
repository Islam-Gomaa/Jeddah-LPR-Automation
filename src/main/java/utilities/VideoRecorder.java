package utilities;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class VideoRecorder {

    private Process process;
    private String outputPath;

    private static final String FFMPEG_PATH =
            "C:\\Users\\i.gomaa\\Downloads\\ffmpeg-8.1.1-essentials_build\\ffmpeg-8.1.1-essentials_build\\bin\\ffmpeg.exe";

    public void startRecording(String testName) {

        try {

            if (process != null && process.isAlive()) {
                System.out.println("Recording already running...");
                return;
            }

            String folderPath = "test-recordings";
            new File(folderPath).mkdirs();

            outputPath = folderPath
                    + File.separator
                    + testName.replaceAll("[^a-zA-Z0-9-_]", "_")
                    + "_"
                    + System.currentTimeMillis()
                    + ".mp4";

            ProcessBuilder builder = new ProcessBuilder(

                    FFMPEG_PATH,

                    "-y",

                    "-f", "gdigrab",
                    "-framerate", "20",
                    "-i", "desktop",

                    // FIX FOR ODD SCREEN WIDTHS
                    "-vf", "scale=trunc(iw/2)*2:trunc(ih/2)*2",

                    "-c:v", "libx264",
                    "-preset", "ultrafast",
                    "-pix_fmt", "yuv420p",

                    outputPath
            );

            process = builder.start();

            // اقرأ stderr
            new Thread(() -> {
                try (BufferedReader br =
                             new BufferedReader(
                                     new InputStreamReader(
                                             process.getErrorStream()))) {

                    String line;

                    while ((line = br.readLine()) != null) {
                        System.out.println("FFMPEG >> " + line);
                    }

                } catch (Exception ignored) {
                }
            }).start();

            Thread.sleep(3000);

            System.out.println("\n====================================");
            System.out.println("Video Recording Started");
            System.out.println("Process Alive : " + process.isAlive());
            System.out.println("Output File   : " + outputPath);
            System.out.println("====================================\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {

        try {

            if (process == null) {
                return;
            }

            if (!process.isAlive()) {

                System.out.println("Recording process already stopped.");
                return;
            }

            try {

                OutputStream os = process.getOutputStream();

                os.write("q\n".getBytes());

                os.flush();

                os.close();

            } catch (Exception ignored) {
            }

            boolean finished =
                    process.waitFor(15, TimeUnit.SECONDS);

            if (!finished) {

                process.destroy();

                if (!process.waitFor(5, TimeUnit.SECONDS)) {
                    process.destroyForcibly();
                }
            }

            File video = new File(outputPath);

            System.out.println("\n====================================");
            System.out.println("Video Recording Stopped");
            System.out.println("Saved File : " + outputPath);

            if (video.exists()) {
                System.out.println("File Size  : "
                        + (video.length() / 1024)
                        + " KB");
            }

            System.out.println("====================================\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}