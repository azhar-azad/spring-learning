package com.azad.tacocloud.tacos.data;

import com.azad.tacocloud.tacos.Ingredient;
import com.azad.tacocloud.tacos.IngredientRef;
import com.azad.tacocloud.tacos.Taco;
import com.azad.tacocloud.tacos.TacoOrder;
import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        /*
        We created a PreparedStatementCreatorFactory that describes the insert query along with the types of the
        query's input fields.
         */
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("insert into Taco_Order "
        + "(delivery_name, delivery_street, delivery_city, delivery_state, delivery_zip, "
        + "cc_number, cc_expiration, cc_cvv, placed_at) "
        + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
        /*
        Because we'll need to fetch the saved order's ID, we also will need to call setReturnGeneratedKeys(true).
         */
        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());
        /*
        After defining the PreparedStatementCreatorFactory, we use it to create a PreparedStatementCreator, passing in
        the values from TacoOrder object that will be persisted. The last field given to the PreparedStatementCreator
        is the date that the order is created, which we'll also need to set on the TacoOrder object itself so that the
        returned TacoOrder will have that information available.
         */
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
                order.getDeliveryName(), order.getDeliveryStreet(), order.getDeliveryCity(),
                order.getDeliveryState(), order.getDeliveryZip(), order.getCcNumber(),
                order.getCcExpiration(), order.getCcCVV(), order.getPlacedAt()));

        /*
        Per the schema, the id property on the Taco_Order table is an identity, meaning that the database will
        determine the value automatically. But if the database determines the value for us, then we will need to know
        what that value is so that it can be returned on the TacoOrder object returned from the save() method.
        Fortunately, Spring offers a helpful GeneratedKeyHolder type that can help with that.
        */
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        /*
        Now that we have a PreparedStatementCreator in hand, we're ready to actually save the order data by calling the
        update() method on JdbcTemplate, passing in the PreparedStatementCreator and a GeneratedKeyHolder.
         */
        jdbcOperations.update(psc, keyHolder);
        /*
        After the order data has been saved, the GeneratedKeyHolder will contain the value of the id filed as assigned
        by the database, and should be copied into the TacoOrder object's id property.
         */
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        /*
        At this point, the order has been saved, but we need to also save the Taco objects associated with the order. We
        can do that by calling saveTaco() for each Taco in order.
         */
        List<Taco> tacos = order.getTacos();
        int i = 0;
        for (Taco taco: tacos) {
            saveTaco(orderId, i++, taco);
        }

        return order;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("insert into Taco "
        + "(name, created_at, taco_order, taco_order_key) "
        + "values (?, ?, ?, ?)", Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG);
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
                taco.getName(), taco.getCreatedAt(), orderId, orderKey));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        /*
        We make a call to saveIngredientRefs() method to create a row in the Ingredient_Ref table to link the Taco row
        to an Ingredient row.
         */
        saveIngredientRefs(tacoId, taco.getIngredients().stream().map(i -> new IngredientRef(i.getId()))
                .collect(Collectors.toList()));

        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs) {
        int key = 0;
        for (IngredientRef ingredientRef: ingredientRefs) {
            jdbcOperations.update("insert into Ingredient_Ref (ingredient, taco, taco_key) "
            + "values (?, ?, ?)", ingredientRef.getIngredient(), tacoId, key++);
        }
    }
}
