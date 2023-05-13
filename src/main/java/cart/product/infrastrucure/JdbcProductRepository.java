package cart.product.infrastrucure;

import cart.product.domain.Product;
import cart.product.domain.ProductRepository;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("product")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, image, price FROM product";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public Product save(Product product) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(product);
        long savedId = jdbcInsert.executeAndReturnKey(param).longValue();
        product.injectAutoId(savedId);
        return product;
    }

    @Override
    public Product update(Product product) {
        String sql = "UPDATE product SET name = :name, image = :image, price = :price WHERE id = :id";
        SqlParameterSource param = new BeanPropertySqlParameterSource(product);
        jdbcTemplate.update(sql, param);
        return product;
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM product WHERE id = :id";
        return jdbcTemplate.update(sql, Map.of("id", id));
    }

    @Override
    public Boolean existById(Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM product WHERE id = :id)";
        return jdbcTemplate.queryForObject(sql, Map.of("id", id), Boolean.class);
    }


    private final RowMapper<Product> productRowMapper = (rs, rowNum) ->
        new Product(rs.getLong("id"),
            rs.getString("name"),
            rs.getString("image"),
            rs.getLong("price"));
}
