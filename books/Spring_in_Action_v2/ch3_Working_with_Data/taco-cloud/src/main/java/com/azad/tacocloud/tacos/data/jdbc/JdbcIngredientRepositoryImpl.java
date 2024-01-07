package com.azad.tacocloud.tacos.data.jdbc;

import com.azad.tacocloud.tacos.jdbc.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/***
 * We need to write an implementation of IngredientRepository that uses JdbcTemplate to query the database.
 *
 * @Repository - annotation is one of the handful of stereotype annotations that Spring defines, including @Controller
 * and @Component. By annotating JdbcIngredientRepository with @Repository, we declare that it should be automatically
 * discovered by Spring component scanning and instantiated as a bean in the Spring application context.
 */
@Repository
public class JdbcIngredientRepositoryImpl implements JdbcIngredientRepository {

    private JdbcTemplate jdbcTemplate;

    /*
    When Spring creates the JdbcIngredientRepository bean, it injects it with JdbcTemplate. That's because when
    there's only one constructor, Spring implicitly applies auto wiring of dependencies through that constructor's
    parameters. If there is more than one constructor, or if we just want auto wiring to be explicitly stated, then we
    can annotate the constructor with @Autowired.
     */
    @Autowired
    public JdbcIngredientRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /***
     * The findAll() method, expecting to return a collection of objects, uses JdbcTemplate's query() method. The
     * query() method accepts the SQL for the query as well as a RowMapper implementation. query() also accepts as its
     * final argument(s) a list of any parameters required in the query. But in this case, there aren't any required
     * parameters.
     * @return A list of Ingredient objects found on the Ingredient table.
     */
    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("select id, name, type from Ingredient", this::mapRowToIngredient);
    }

    /***
     * The findById() method will need to include a where clause in its query to compare the value of the id column with
     * the value of the id parameter passed into the method. Therefore, the call to query() includes, as its final
     * parameter, the id parameter. When the query is performed, the ? will be replaced with this value.
     * @param id The ingredient id to fetch
     * @return
     */
    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> results = jdbcTemplate.query("select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient, id);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    /***
     * JdbcTemplate's update() method can be used for any query that writes or updates or saves data in the database.
     * @param ingredient The Ingredient object to save
     * @return
     */
    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    /***
     * The mapRowToIngredient() method is an implementation of Spring's RowMapper for the purpose of mapping each row
     * in the result set to an object.
     * @param row
     * @param rowNum
     * @return
     * @throws SQLException
     */
    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type")));
    }

    /***
     * THIS METHOD WILL NOT BE USED IN THE APPLICATION. IT IS JUST FOR UNDERSTANDING PURPOSE.
     * The RowMapper parameter for both findAll() and findById() is given a method reference to the mapRowToIngredient()
     * method. Java's method reference and lambdas are convenient when working with JdbcTemplate as an alternative to
     * an explicit RowMapper implementation. If for some reason we want or need an explicit RowMapper, then the
     * following implementation of findByIdExplicit() shows how to do that.
     * @param id
     * @return
     */
    private Ingredient findByIdExplicit(String id) {
        return jdbcTemplate.queryForObject("select id, name, type from Ingredient where id=?", new RowMapper<Ingredient>() {
            @Override
            public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Ingredient(
                        rs.getString("id"),
                        rs.getString("name"),
                        Ingredient.Type.valueOf(rs.getString("type")));
            };
        }, id);
    }
}
