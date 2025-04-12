package bank.p2pbank.global.config;

import bank.p2pbank.global.security.encrytion.AES256Converter;
import bank.p2pbank.global.security.encrytion.AESUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    @Bean
    public AES256Converter aes256Converter(AESUtil aesUtil) {
        return new AES256Converter(aesUtil);
    }
}
