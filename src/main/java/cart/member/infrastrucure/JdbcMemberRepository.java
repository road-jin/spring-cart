package cart.member.infrastrucure;

import cart.member.domain.Member;
import cart.member.domain.MemberRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT id, email, password FROM member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT id, email, password FROM member WHERE email = :email";
        return Optional.ofNullable(DataAccessUtils.singleResult(
            jdbcTemplate.query(sql, Map.of("email", email), memberRowMapper)));
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) ->
        new Member(rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"));
}
