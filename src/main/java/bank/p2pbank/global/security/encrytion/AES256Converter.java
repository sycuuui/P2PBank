package bank.p2pbank.global.security.encrytion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter
@RequiredArgsConstructor
public class AES256Converter implements AttributeConverter<String, String> {

    private final AESUtil aesUtil;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return aesUtil.encrypt(attribute);
        } catch (Exception e) {
            log.error("AES 암호화 실패", e);
            throw new IllegalStateException("암호화 중 오류 발생");
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return aesUtil.decrypt(dbData);
        } catch (Exception e) {
            log.error("AES 복호화 실패", e);
            throw new IllegalStateException("복호화 중 오류 발생");
        }
    }
}
