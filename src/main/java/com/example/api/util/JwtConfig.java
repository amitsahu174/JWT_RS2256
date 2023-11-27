package com.example.api.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    private String privateKeystr = "MIIEpQIBAAKCAQEA1KcaIZxfJ3ExTPPsx5OfaOwVISmSltIXrdFPNJSHosJFdYFYkIYugYZPmsdVXYk2ZTk2q2iBfN+9GZBg4n/MZYBBbha3ll1Sr7oSymIh6Q8673w1LC18gDXID/zUE1Y6H5LlyDRBTsFrOW6+OwNxbP73zthRXzc6kj8k6Zw63BbsT+p/aIaM9+a0DOWyPQ4F2GLfOky7EBL/ifwuzAqypirz16VedUqVt/MnzN/OZ5JtnZyrMFXu0IQZ/+gHsCN7LtMCwy44G8+v3Z6HWcksqDOy2xvoB/fRGwzEpmiIkS3yxWA2sUvWZEzKnKRrkAyS2JmsHJydV/GdX0q49b4tKwIDAQABAoIBAQCUGekGHYzbwGawjHWMrZBpF3rDxNtV9O4O3WaHXSZ1wU10v+e5h+odxTftuQQIB8b9inI7wKXmHUDq6/W96Xml+0QWcvXbYBV1U2dA0kWBtly6a8vwf6Fk/Y/fKptc+EG0ua8U1ufg/K47qpgMK6K0YYKe6WMKqEE/l4CSvn7zxjEDoXONiF/LxYV+vIVQQ07jfBQElsE+nxuYmFXT3houDR8hjzM5eHgdrl0ObKFBkc1OBHyKwWA+XBzcCXhLJdSL8cNjEmSR7dXMvXUxEi0Kv9LEpXHtckIdD/Uox5TEr6rjyARUYsPdOUgNve1Hch+SH39mniyDmFb2BTuhXeWBAoGBAP6fF5+tUJmseeWud74RehiyTc/tCw781L0al43mf6d5kmK1UAu3MvZVFqujiRuJAuIT+XIwx+U4T0jEBxd37+0kkHPfrAVAe9N4o6WD7Ms8VyyC/E3sVEc3TT2xHAkDH6d0tA61QdJfUCQBIsIbVhPkYZLTsPdaE/oxqGhviQdBAoGBANXN1zyM7vKJYKDHj0wpVLK3zxOd5Q5N/Ls3LVALoe1URrHuJF2dOJwmKpUpLYKGxyUwtNvolmT/BKutSSFThVlRsmUK81Wt/WR0LDNEDFiN6ELP8o9/L/p+oK72FHPdYizleRlh+NFgz/9+pkxTgcBM02Kbel+Y+2k/xYoYO+VrAoGBAKoUmVInElzkcjRzOzcyM23FxOZsP+PODUkYNz4pRwIW2iqnVIXO5PLY2aDjR1WmSs+7affWD+DUoVlI4DfT/OdackeElEKZ1VzoC+Am5IjJkK2B/YkLoSxFC4p3APQgKwfPk+rzNrmQw1aZj587ZV86QdczItXHTOrmOskwH8dBAoGBAL5vmBVg2rzBTIYo+iQ2dDCztAifDRUOtdvzq7Kuic6g0E9P9KhFj9TV0fwGL3khn2Qq3A08QCk5VOmtcYPzpsUGFcYWWOOqFBdTusL3C6Ac1hIcssrJ2OxfubPdkB4bxISE6LaJI10rxFiocGtvvhSQ2X/tSLS5yMXYfZUhPMh9AoGAKyT02j2TRxTq0cok2Xni0Kcfixlt77D1hzZyeSjFweWf+LOtvJH9FJ4Al4Ru2gpdV+U67Akgr2ZW0ZZSvMiTuAQ+gWXPu18tDIpU27MZsYNuegrFrN9tCjf0ApKedWhDidzkEGxpbkoYa4h1EIdXxoDjLz6jQcotqSDydRaqtkg=";
    // private StringBuffer privateKeystr = new StringBuffer(
    // "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCKP58Z8bCqTD8uPB2TaQZ8gZWvED1Rco0SN8pVSlf4gK5uaS8D9zstyZz0XdPSoMKE5jTb1Q0leDnHCJmhahjtLBbCyA6xUTJIt6RIhzj1qeXX/etK134Z53VehYnca0JTpD8QLwxvJzDotEYt7vw8QqFt9592VfgHhjAzO+nrarZ5ie0+0TH15eY3DXRB1e/lBOkiteZxG/BGzZngIEXV6qWseFYa2bl3QEodKoLoEtKQTAvfoBZBLdwARDCzMf26otBQ+BdI3l7tpb7uBt7zNVl+sXPP7U/cOZIJJ/MeePIuBIr4oIpNngFmjhN1oqhi0L8BoZTn4adO9f9uYq4dAgMBAAECggEAOlGITit0eQJdDKXzXAHL6K3JdOYG19rYw7yhL8WCwqOV8aGRYbzjS/dmrHcAKjQCmol59tozB4Fl7h4koMDe7v3QRC6cdBRqf95oAOh6ELew66KDpTiuS0Mmcc8nTsI4YoxAxhTyy9jeeuvBRxMveEQrUPZKYGL3lET5BEPb86CUZys2FBHQ+XRedmxtyzskPfAxNDGe7c6s5eXs2o6b1U9bE1oRw5rdZlAeKMdrEsJJksQg+TQnPjBSnEqwN9aYiAsVmRWDmSjBjqv0/PFDU9JTN0OPBfxy9a3Bx49wOlJM0ntmWZuWygvV/8jReBEQ6zEnEcBDQ8b3lVbdcHJNQQKBgQD+/uBMBx+KS65Ymc+JjGL+Bs/IuvS1bpDdJEEqwanW0DwANmh+nlx76gQvNwRo4zlXuHHiT4Aewq0T1NEkpXs2PAWonYMqCvrN/TKciJAlML4ME5gvO5y7rpbNxiuyCIKKj6GhlMqHP78Pxs4He3+r8n5E341gI14HFmEcpT3RJQKBgQCKywYbO6h/GDsllLIp+cwRNk1uvZ+kPuvyElrGfcCVZWZ9SPlUbJykDFdObnV6p2d8r3aQodaDTCIQX3ppy9TQp+9scfXRkR0oAtNw1o/tvB3vOuvfgI8X1bJnBT9VyxA1+m2SSCF+xSy1U656dVglwRFu0+AxZYrMiUS5h6RDmQKBgQCpoUn6qZ5stG3V2PQ7z10nGcbuBJi3fUb3JaAIXm6qTTos9gTLfnONpx6Dfjj7wXSrCzMPVs2nKOBUWfWLSF5PtNwa0DWqKUWw9GfPw/UIGIXEqOBLQ3+/RqRhXRxCQfD9VhEy2TVlhZ2HVDxIs/o5m4YuMPzuYHizhEqz8KTP1QKBgH6pk5QM2NPMKEY+sm2OU0at+PKESQmahrcvOvqETtnIZUUW8Lu0NyubO54PPlVGKzJl6t1JZLwEId4xVETyyO/3IpAkXEL5nNMj6RTEKyu5jtQBQLuC1pPY6OwNpORF4jBwr8hc3XyUsx10FfoZ1uz1ilRLNgG+enH5H+f1OtqZAoGBANnfIj1VZKssQTpm+PRLx2vxL0grR2pjLCOhS1pOWzOv4Idf+dimZzLeQsnXjWwVmcg/W39aYuUXqa/N0Y8UgVsApCsCgFyV4om9yy9tH4JlSDiRHd7fROKoVtLoRan46KbDLl8zTlpno7Pzm99+tO2H9kHu1LrOt7VPNXo1YVvW");
    private String publicKeystr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1KcaIZxfJ3ExTPPsx5OfaOwVISmSltIXrdFPNJSHosJFdYFYkIYugYZPmsdVXYk2ZTk2q2iBfN+9GZBg4n/MZYBBbha3ll1Sr7oSymIh6Q8673w1LC18gDXID/zUE1Y6H5LlyDRBTsFrOW6+OwNxbP73zthRXzc6kj8k6Zw63BbsT+p/aIaM9+a0DOWyPQ4F2GLfOky7EBL/ifwuzAqypirz16VedUqVt/MnzN/OZ5JtnZyrMFXu0IQZ/+gHsCN7LtMCwy44G8+v3Z6HWcksqDOy2xvoB/fRGwzEpmiIkS3yxWA2sUvWZEzKnKRrkAyS2JmsHJydV/GdX0q49b4tKwIDAQAB";

    @Bean
    public PrivateKey privateKey() throws Exception {

        return GetPrivateKeyData(privateKeystr); // Change the file name accordingly
    }

    @Bean
    public PublicKey publicKey() throws Exception {
        return GetPublicKeyData(publicKeystr); // Change the file name accordingly
    }

    public PublicKey GetPublicKeyData(String publicKeyContent) {

        try {
            Security.addProvider(new BouncyCastleProvider());
            // Security.addProvider(new BouncyCastleProvider());
            byte[] keyBytes = java.util.Base64.getDecoder().decode(publicKeyContent);

            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePublic(spec);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    public PrivateKey GetPrivateKeyData(String privateKey1) {

        try {

            Security.addProvider(new BouncyCastleProvider());
            // String privateKeyContent = keyBuilder.toString();
            // byte[] keyBytes = DatatypeConverter.parseBase64Binary(privateKey1);
            byte[] keyBytes = java.util.Base64.getDecoder().decode(privateKey1);

            // byte[] keyBytes =
            // java.util.Base64.getDecoder().decode(privateKeyContent.toString());

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
}