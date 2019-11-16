package ar.edu.uade.integracion.shop.service;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FtpClient {
    private String server;
    private int port;
    private String user;
    private String password;
    private FTPSClient ftp;

    public FtpClient(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    void open() throws IOException {
        ftp = new FTPSClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
        ftp.enterLocalPassiveMode();
        ftp.login(user, password);
    }

    void close() throws IOException {
        ftp.disconnect();
    }

    public void getFile(String fileName, ByteArrayOutputStream destination) throws IOException {
        ftp.retrieveFile(fileName, destination);
    }

    public List<String> listFiles(String path) throws IOException {
        FTPFile[] files = ftp.listFiles(path);
        return Arrays.stream(files)
                .map(FTPFile::getName)
                .collect(Collectors.toList());
    }

}
