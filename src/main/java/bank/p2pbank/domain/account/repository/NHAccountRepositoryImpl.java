package bank.p2pbank.domain.account.repository;

import bank.p2pbank.domain.account.entity.QNHAccount;
import bank.p2pbank.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NHAccountRepositoryImpl implements NHAccountRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public boolean existsByUserAndBankcodeAndAccountNumber(User user, String bankcode, String nhAccountNumber) {
        QNHAccount nhAccount = QNHAccount.nHAccount; //정적 메타 클래스(엔티티의 각 필드들이 QueryDSL에서 타입 안전하게 쿼리할 수 있도록 하종으로 생성해주는 클래스)를 사용하는 표준 방식

        Integer fetchOne = queryFactory
                .selectOne() //조건을 만족하는 행이 있다면 1이라는 숫자 하나를 가져오고 없으면 null 반환
                .from(nhAccount)
                .where(nhAccount.user.eq(user)
                        .and(nhAccount.bankCode.eq(bankcode)
                        .and(nhAccount.nhAccountNumber.eq(nhAccountNumber))))
                .fetchFirst(); // 하나만 가져옴

        return fetchOne != null;
    }
}
