package cart.cartitem.infrastrucure;

import cart.cartitem.domain.CartItem;
import cart.cartitem.domain.CartItemRepository;
import cart.cartitem.domain.CartItemWithProduct;
import cart.product.domain.Product;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCartItemRepository implements CartItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcCartItemRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("cart_item")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(CartItem cartItem) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(cartItem);
        return jdbcInsert.executeAndReturnKey(param).longValue();
    }

    @Override
    public Optional<CartItemWithProduct> findWithProductById(Long id) {
        String sql = "SELECT c.id, "
                          + "p.id AS product_id, "
                          + "p.name AS product_name,"
                          + "p.image AS product_image, "
                          + "p.price AS product_price, "
                          + "c.owner_id "
                    + "FROM cart_item c "
                    + "INNER JOIN product p on p.id = c.product_id "
                    + "WHERE c.id = :id";
        return Optional.ofNullable(DataAccessUtils.singleResult(
            jdbcTemplate.query(sql, Map.of("id", id), cartItemWithProductRowMapper)));
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        String sql = "SELECT id, product_id, owner_id FROM cart_item WHERE id = :id";
        return Optional.ofNullable(DataAccessUtils.singleResult(
            jdbcTemplate.query(sql, Map.of("id", id), cartItemRowMapper)));
    }

    @Override
    public List<CartItemWithProduct> findAllWithProductByMemberId(Long memberId) {
        String sql = "SELECT c.id, "
                          + "p.id AS product_id, "
                          + "p.name AS product_name,"
                          + "p.image AS product_image, "
                          + "p.price AS product_price, "
                          + "c.owner_id "
                    + "FROM cart_item c "
                    + "INNER JOIN product p on p.id = c.product_id "
                    + "WHERE c.owner_id = :ownerId";
        return jdbcTemplate.query(sql, Map.of("ownerId", memberId), cartItemWithProductRowMapper);
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM cart_item WHERE id = :id";
        return jdbcTemplate.update(sql, Map.of("id", id));
    }

    private RowMapper<CartItemWithProduct> cartItemWithProductRowMapper = (rs, rowNum) ->
        new CartItemWithProduct(rs.getLong("id"),
            new Product(
                rs.getLong("product_id"),
                rs.getString("product_name"),
                rs.getString("product_image"),
                rs.getLong("product_price")),
            rs.getLong("owner_id"));

    private RowMapper<CartItem> cartItemRowMapper = (rs, rowNum) ->
        new CartItem(rs.getLong("id"),
            rs.getLong("product_id"),
            rs.getLong("owner_id"));
}
