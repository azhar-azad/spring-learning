# Working with Data

## Topics Covered
- **Reading and writing data with JDBC**

Query a database without `JdbcTemplate`

```
@Override
public Optional<Ingredient> findById(String id) {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	try {
		connection = dataSource.getConnection();
		statement = connection.prepareStatement(
			"select id, name, type from Ingredient where id=?");
		statement.setString(1, id);
		resultSet = statement.executeQuery();
		Ingredient ingredient = null;
		if(resultSet.next()) {
			ingredient = new Ingredient(
				resultSet.getString("id"),
				resultSet.getString("name"),
				Ingredient.Type.valueOf(resultSet.getString("type")));
		}
		return Optional.of(ingredient);
	} catch (SQLException e) {
		// ??? What should be done here ???
	} finally {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {}
		}
	}
	return Optional.empty();
}
```
Query a database with Spring's JdbcTemplate

```
private JdbcTemplate jdbcTemplate;

public Optional<Ingredient> findById(String id) {
	List<Ingredient> results = jdbcTemplate.query(
		"select id, name, type from Ingredient where id=?",
		this::mapRowToIngredient,
		id);
	return results.size() == 0 ?
		Optional.empty() :
		Optional.of(results.get(0));
}

private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
	return new Ingredient(
		row.getString("id"),
		row.getString("name"),
		Ingredient.Type.valueOf(row.getString("type")));
}
```

- **Working with Spring Data JDBC**

Spring Data will automatically generate implementations for our repository interfaces at run time. But it will do that only for interfaces that extend one of the repository interfaces provided by Spring Data. At the very least, our repository interfaces will need to extend Repository so that Spring Data knows to create the implementation automatically.

The good news about Spring Data—there’s no need to write an implementation! When the application starts, Spring Data automatically generates an implementation on the fly. This means the repositories are ready to use from the get-go. Just inject them into the controllers and you’re done.


- **Persisting data with Spring Data JPA**

*Method name signature explanation*

Problem: Query for all orders delivered to given ZIP code withing a given date range. 
Solution: The method name will be *findOrdersByDeliveryZipAndPlaceAtBetween()*. It will take 3 arguments: 
1. The deliveryZip value
2. Starting date value
3. End date value 

find -> The verb: This method will find data ("get" and "read" are also allowed here).
Orders -> The subject: Optional. If ommitted or any word will also retrieve the entity data for which this repository is written.
By -> Signifies the start of properties to match.
DeliveryZip -> Match .deliveryZip or .delivery.zip property.
And -> SQL AND
PlacedAt -> Match .placedAt or .placed.at property.
Between -> The value must fall between the given values. 

In addition to an implicit Equals operation and the Between operation, Spring Data method signatures can also include any of the following operators:

- IsAfter , After , IsGreaterThan , GreaterThan
- IsGreaterThanEqual , GreaterThanEqual
- IsBefore , Before , IsLessThan , LessThan
- IsLessThanEqual , LessThanEqual
- IsBetween , Between
- IsNull , Null
- IsNotNull , NotNull
- IsIn , In
- IsNotIn , NotIn
- IsStartingWith , StartingWith , StartsWith
- IsEndingWith , EndingWith , EndsWith
- IsContaining , Containing , Contains
- IsLike , Like
- IsNotLike , NotLike
- IsTrue , True
- IsFalse , False
- Is , Equals
- IsNot , Not
- IgnoringCase , IgnoresCase

Finally, you can also place OrderBy at the end of the method name to sort the results by a specified column. For example, to order by the deliveryTo property, use the following code: *findByDeliveryCityOrderByDeliveryTo(String city)*

- All custom query methods require @Query . This is because, unlike JPA, there’s no mapping metadata to help Spring Data JDBC automatically infer the query from the method name.
- All queries specified in @Query must be SQL queries, not JPA queries.

