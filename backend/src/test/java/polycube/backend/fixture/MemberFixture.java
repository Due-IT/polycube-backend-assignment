package polycube.backend.fixture;

import polycube.backend.model.entity.Member;
import polycube.backend.model.type.Grade;

public class MemberFixture {
    public static Member createMember(Grade grade) {
        return createMember(1L, grade.name() + "회원", grade);
    }

    private static Member createMember(Long id, String name, Grade grade) {
        return new Member(id, name, grade);
    }
}
