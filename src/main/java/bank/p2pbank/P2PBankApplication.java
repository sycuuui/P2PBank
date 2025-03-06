package bank.p2pbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA Auditing 기능 활성화를 위해
@SpringBootApplication
public class P2PBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(P2PBankApplication.class, args);
		System.out.println("P2PBank Server Start");
	}

}
