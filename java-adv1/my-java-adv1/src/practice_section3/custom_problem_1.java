package practice_section3;

import static practice_section3.MyLogger.log;

public class custom_problem_1 {
    public static void main(String[] args) {
        String[] fileNames = new String[]{"video.mp3", "music.mp3", "document.pdf"};
        int[] fileSizes = {50, 30, 10};

        for (int i = 0; i < fileNames.length; i++) {
            Thread thread = new Thread(new FileDownloader(fileNames[i], fileSizes[i]));
            thread.start();
        }
    }

    static class FileDownloader implements Runnable {
        String fileName;
        int fileSize; // MB

        public FileDownloader(String fileName, int fileSize) {
            this.fileName = fileName;
            this.fileSize = fileSize;
        }

        @Override
        public void run() {
            log(fileName + " 다운로드 시작" + " (" + fileSize + "MB)");
            int downloadSize = 0;
            int percentCounter = 10;

            try {
                while (downloadSize < fileSize) {
                    Thread.sleep(200);
                    downloadSize += 1;

                    if ((downloadSize * 100) / fileSize >= percentCounter) {
                        log(fileName + " " + percentCounter + "% 완료");
                        percentCounter += 10;
                    }
                }

                log(fileName + " 다운로드 완료!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
