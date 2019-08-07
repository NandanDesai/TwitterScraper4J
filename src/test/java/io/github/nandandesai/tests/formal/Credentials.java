package io.github.nandandesai.tests.formal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Credentials {
    private String apiKey;
    private String apiSecretKey;
    private String accessToken;
    private String accessTokenSecret;

    public Credentials() throws IOException, URISyntaxException {
        URL imageSampleUrl = ClassLoader.getSystemClassLoader().getResource("credentials");

        File inputFile = new File(imageSampleUrl.toURI());
        byte[] bytesArray = new byte[(int) inputFile.length()];

        FileInputStream fileInputStream = new FileInputStream(inputFile);
        fileInputStream.read(bytesArray);
        fileInputStream.close();
        String content = new String(bytesArray);

        String[] lines = content.split("\n");

        for (String line : lines) {
            String[] keyValue = line.split("=");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            if (key.equals("API_KEY")) {
                apiKey=value;
            } else if (key.equals("API_SECRET_KEY")) {
                apiSecretKey=value;
            } else if (key.equals("ACCESS_TOKEN")) {
                accessToken=value;
            } else if (key.equals("ACCESS_TOKEN_SECRET")) {
                accessTokenSecret=value;
            } else {
                throw new IllegalArgumentException("invalid key in \"credentials\" file");
            }

        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecretKey() {
        return apiSecretKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }
}
