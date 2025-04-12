package bank.p2pbank.global.security.encrytion;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AESUtil {

    @Value("${aes.secret-key}")
    private String secretKey; // 32자리 (256bit) key

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding"; // 알고리즘/모드/패딩 지정
    private static final String AES = "AES";
    private SecretKeySpec keySpec; //문자열 키(secretKey)를 Java가 사용할 수 있도록 변환한 객체

    @PostConstruct //bean이 DI까지 끝난 후 자동으로 호출하여 실행
    public void init() {
        byte[] keyBytes = secretKey.getBytes();
        this.keySpec = new SecretKeySpec(keyBytes, AES); //문자열 키를 암호화용 객체(AES 키)로 변환
    } //암호화에 사용할 키를 준비하는 작업

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM); // 사용할 알고리즘 지정

        byte[] ivBytes = new byte[16];
        new SecureRandom().nextBytes(ivBytes);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes); //초기화 벡터 생성

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec); // 암호화 모드와 어떤 키,iv를 선택할 것인지 적용
        byte[] encrypted = cipher.doFinal(plainText.getBytes()); // 암호화 수행

        // IV + 암호문을 합쳐서 base64로 인코딩
        // 암호화한 결과에 iv를 앞에 붙여서 저장(->복호화시 iv를 꺼내 써야하므로)
        byte[] encryptedIvAndText = new byte[ivBytes.length + encrypted.length];
        System.arraycopy(ivBytes, 0, encryptedIvAndText, 0, ivBytes.length);
        System.arraycopy(encrypted, 0, encryptedIvAndText, ivBytes.length, encrypted.length);

        return Base64.getEncoder().encodeToString(encryptedIvAndText); //바이너리를 문자열로 변환
    }

    public String decrypt(String cipherText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(cipherText); //Base64로 저장된 암호화를 byte배열로 복원

        byte[] iv = new byte[16];
        byte[] encrypted = new byte[decoded.length - 16];

        //저장된 암호문에 iv와 암호화된 내용을 분리
        System.arraycopy(decoded, 0, iv, 0, 16); //앞부분 16byte는 iv
        System.arraycopy(decoded, 16, encrypted, 0, encrypted.length); //나머지는 암호문

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv)); // 복호화 모드로 수

        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }
}