package ar.edu.uade.integracion.shop.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
@Ignore
public class FtpTest {
    FtpClient ftpClient;

    @Before
    public void setup() throws IOException {
        ftpClient = new FtpClient("f24-preview.runhosting.com", 21, "3203234", "logistica123");
        ftpClient.open();
    }

    @Test
    public void list_files() throws IOException {
        ftpClient.listFiles("/").forEach(f -> System.out.println(f));
    }

    @Test
    public void getFile() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ftpClient.getFile("/data.csv", out);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    @After
    public void tearDown() throws IOException {
        ftpClient.close();
    }


}
