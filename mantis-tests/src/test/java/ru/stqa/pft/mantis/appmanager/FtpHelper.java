package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {

    private final ApplicationManager app;
    private final FTPClient ftp;

    public FtpHelper(ApplicationManager app) {
        this.app = app;
        ftp = new FTPClient();
    }

    //file - локальный файл, target - имя удаленного файла, backup - имя резервной копии
    public void upload(File file, String target, String backup) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        //на всякий случай удаляем резервную копию, если она есть
        ftp.deleteFile(backup);
        //переименовываем удаленный файл, делаем резервную копию
        ftp.rename(target, backup);
        ftp.enterLocalPassiveMode();
        ftp.storeFile(target, new FileInputStream(file)); //FileInputStream - побайтовое чтение, для бинарников
        ftp.disconnect();
    }

    public void restore(String backup, String target) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        //удаляется загруженный upload файл
        ftp.deleteFile(target);
        //из резервной копии восстанавливается оригинальный файл
        ftp.rename(backup, target);
        ftp.disconnect();
    }
}
